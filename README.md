# Recompilation pipeline (the "like other people did it" path)

This project follows the same pattern as other PS2 static-recompilation ports
(Gitaroo Man Native, BT3-Recomp, Ace Combat 5 Static Recompilation): all of them
are built on top of **[PS2Recomp](https://github.com/ran-j/PS2Recomp)**, not a
custom-written recompiler. We are doing the same here instead of reinventing it.

Useful discovery: PS2Recomp already ships its own `android/` Gradle project
(raylib-based runner, NDK/CMake build). That means the Android target in this
repo should eventually become a thin wrapper around *that* project (plus our
manager UI), rather than something built from scratch.

## The pipeline, step by step

1. **Get the source ELF.** Extract `SLUS_213.79` (or the EU/JP equivalent) from
   your own legally-owned Okami disc image. Nothing from the disc is ever
   committed to this repo - only the generated code and config files are.
2. **Analyze it.**
   - Preferred (retail/stripped binaries, which Okami is): open the ELF in
     [Ghidra](https://ghidra-sre.org/), run
     `ps2xAnalyzer/tools/ghidra/ExportPS2Functions.java`, export the function
     map as TOML + CSV.
   - Fallback (quick local experiment, only works well if the ELF still has
     debug symbols - Okami's retail build almost certainly does not):
     `./ps2_analyzer okami.elf config.toml`
3. **Recompile.** `./ps2_recomp config.toml` turns the ELF into generated C++
   under `ps2xRecomp`'s output folder - one function at a time, almost
   literally (`addiu $r4,$r4,0x20` becomes `ctx->r4 = ADD32(ctx->r4, 0x20);`).
4. **Link against the runtime.** The generated code is compiled together with
   `ps2xRuntime` (memory model, syscalls, basic GS/VU/file stubs) and
   `ps2xIOP` (runs the original IRX modules with HLE fallbacks).
5. **The hard, manual part: game overrides.** PS2Recomp's generic runtime does
   not know how Okami's custom cel-shading / sumi-e ink renderer works. That
   has to be hand-written as a *game override* module
   (`PS2_REGISTER_GAME_OVERRIDE(...)` in `ps2xRuntime/include/game_overrides.h`)
   that replaces specific EE functions by address for this exact build of
   Okami. This is the step with no shortcut and no guaranteed timeline - it is
   genuine reverse-engineering work, done incrementally as crashes/graphical
   bugs are diagnosed.
6. **Android build.** Point PS2Recomp's `android/app` project at the generated
   sources + our game-override module, set `ps2xBootElf`, build with
   `./gradlew assembleRelease`.

## What's blocking step 2 right now

We need **your own legally-dumped Okami ELF** to run the analyzer on and see
real numbers (how many functions, how much is auto-translatable vs. needs
manual stubs/overrides). Nobody here can source that file from the internet -
it has to come from a disc you own.

Once that's available we can run the analyzer, commit the generated
`config.toml` + function map (not the game data itself), and start triaging
the first batch of stubs.
