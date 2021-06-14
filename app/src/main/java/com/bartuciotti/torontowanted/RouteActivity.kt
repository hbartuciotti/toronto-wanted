package com.bartuciotti.torontowanted

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bartuciotti.torontowanted.util.Constants.DEEPLINK_EXTRA
import com.bartuciotti.torontowanted.util.DeepLinkHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Displays splash screen, checks for deeplink (Opening app from notification) and redirects to MainActivity
 * This activity has a different Theme than the rest of the Application (Check Manifest > AppTheme.RouteActivity)
 */
@AndroidEntryPoint
class RouteActivity : AppCompatActivity() {


    @Inject lateinit var deepLinkHelper: DeepLinkHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainActivity = Intent(this, MainActivity::class.java)

        if (deepLinkHelper.hasDeepLink(intent))
            mainActivity.putExtra(DEEPLINK_EXTRA, deepLinkHelper.getDeepLink(intent))

        startActivity(mainActivity)
        finish()
    }
}