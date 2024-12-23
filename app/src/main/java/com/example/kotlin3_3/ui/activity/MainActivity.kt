package com.example.kotlin3_3.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.kotlin3_3.R
import com.example.kotlin3_3.data.preference.PreferencesHelper
import com.example.kotlin3_3.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val preferencesHelper: PreferencesHelper by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavigation(savedInstanceState)
        onNewIntent(intent)
    }

    override fun onResume() {
        super.onResume()
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }

    private fun setupNavigation(savedInstanceState: Bundle?) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)

        when {
            !preferencesHelper.isShownOnBoard -> {
                navGraph.setStartDestination(R.id.registrationFlowFragment)
            }

            else -> {
                navGraph.setStartDestination(R.id.onBoardFragment)
                if (!preferencesHelper.isRegisterShow) {
                    navGraph.setStartDestination(R.id.registrationFlowFragment)
                } else {
                    navGraph.setStartDestination(R.id.noteFlowFragment)
                }
            }
        }
        navController.graph = navGraph
    }
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        intent.let {
            if (it.action == Intent.ACTION_SEND && it.type == "text/plain") {
                val receivedText = it.getStringExtra(Intent.EXTRA_TEXT)
                if (!receivedText.isNullOrEmpty()) {
                    val navHostFragment =
                        supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                    val currentFragment =
                        navHostFragment?.childFragmentManager?.fragments?.firstOrNull()
                }
            }
        }
    }


}