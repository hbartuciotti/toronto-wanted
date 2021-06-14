package com.bartuciotti.torontowanted

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bartuciotti.torontowanted.util.*
import com.bartuciotti.torontowanted.util.Constants.DEEPLINK_EXTRA
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_toolbar.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),
    NetworkStateReceiver.ConnectivityReceiverListener {


    @Inject
    lateinit var deepLinkHelper: DeepLinkHelper
    private lateinit var navController: NavController
    private var networkStateReceiver: NetworkStateReceiver? = NetworkStateReceiver(this)


    /** Life Cycle */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        setNavigation()
        setActionBar()
        registerReceivers()
        checkForDeeplink() // Redirects user to details page if there is deeplink
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceivers()
    }


    /** Listeners */
    private var wasDisconnected = false // Check if user ever lost connection
    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        if (isConnected) {
            if (wasDisconnected) {
                SnackbarHelper.show(snackbarContainer, "Reconnected!", Snackbar.LENGTH_SHORT)
                (application as ApplicationClass).syncDatabase()
            }
        } else {
            SnackbarHelper.show(snackbarContainer, "You Are Offline!", Snackbar.LENGTH_INDEFINITE)
            wasDisconnected = true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        //Back Button
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


    /** Util */
    private fun setNavigation() {
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()
        navView.setupWithNavController(navController)
    }

    private fun setActionBar() {
        setSupportActionBar(personalizedToolbar) //set custom toolbar
        supportActionBar?.setDisplayShowTitleEnabled(false) //remove default toolbar title
        val topLevelPages = AppBarConfiguration(
            setOf(R.id.fragmentInvestigations, R.id.fragmentNews, R.id.fragmentAbout)
        )
        //topLevelPages allows navigation to set back buttons to children of these pages
        setupActionBarWithNavController(navController, topLevelPages)
    }

    private fun checkForDeeplink() {
        val deepLink = intent.getParcelableExtra<DeepLink>(DEEPLINK_EXTRA)
        if (deepLink != null)
            lifecycleScope.launch { deepLinkHelper.openDeepLink(deepLink, navController) }
    }


    /** Broadcast Receivers */
    private fun registerReceivers() {
        application.registerReceiver(
            networkStateReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    private fun unregisterReceivers() {
        try {
            application.unregisterReceiver(networkStateReceiver)
        } catch (exception: IllegalArgumentException) {
            Log.e(TAG, "Unable to unregister receivers" + exception.localizedMessage)
        }
    }

    private val TAG = MainActivity::class.simpleName
}