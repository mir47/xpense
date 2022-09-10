package com.xpense.android.presentation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.xpense.android.R
import com.xpense.android.databinding.ActivityMainBinding
import com.xpense.android.presentation.legacy.txn_list.LegacyTxnListFragmentDirections
import com.xpense.android.util.SMS_EXTRA

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfig: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupActionBarWithNavController(this, navController, binding.drawerLayout)

        // prevent nav gesture if not on start destination
        navController.addOnDestinationChangedListener { controller, destination, _ ->
            if (destination.id == controller.graph.startDestinationId) {
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }

        appBarConfig = AppBarConfiguration(navController.graph, binding.drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)

        createNotificationChannel(
            getString(R.string.sms_notification_channel_id),
            getString(R.string.sms_notification_channel_name),
            getString(R.string.sms_notification_channel_description)
        )
        createNotificationChannel(
            getString(R.string.fcm_notification_channel_id),
            getString(R.string.fcm_notification_channel_name),
            getString(R.string.fcm_notification_channel_description)
        )

        // check if launched from notification from SMS detection
        intent?.extras?.getString(SMS_EXTRA)?.let {
            navController.navigate(
                LegacyTxnListFragmentDirections
                    .actionLegacyTxnListFragmentToTxnAddEditFragment()
                    .setSms(it)
            )
        }
    }

    override fun onSupportNavigateUp() =
        NavigationUI.navigateUp(findNavController(R.id.nav_host_fragment), appBarConfig)

    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer.isOpen) drawer.close()
        else super.onBackPressed()
    }

    private fun createNotificationChannel(
        channelId: String,
        channelName: String,
        channelDescription: String
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                setShowBadge(false)
                enableLights(true)
                lightColor = Color.RED
                enableVibration(true)
                description = channelDescription
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
