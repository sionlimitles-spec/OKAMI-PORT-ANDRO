package com.okamirecomp.launcher

import android.os.Bundle
import android.view.WindowManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

/**
 * Okami Recomp - Manager shell (UI mockup only).
 *
 * This activity does NOT emulate, decrypt or run any PS2/Xbox game code.
 * It only renders the manager/launcher interface designed for the project.
 * The actual static-recompilation engine (PS2Recomp-based) is a separate,
 * much larger piece of work that has to be built and researched on its own.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        val webView = WebView(this)
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.webViewClient = WebViewClient()
        webView.loadUrl("file:///android_asset/index.html")

        setContentView(webView)
    }
}
