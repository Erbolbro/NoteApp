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
//        if (savedInstanceState == null) {
//            if (!preferencesHelper.isShownOnBoard) {
//                navController.graph =
//                    navController.navInflater.inflate(R.navigation.registration_graph).apply {
//                        setStartDestination(R.id.onBoardFragment)
//                    }
//            } else {
//                if (!preferencesHelper.isShownOnBoard) {
//                    navController.graph =
//                        navController.navInflater.inflate(R.navigation.registration_graph).apply {
//                            setStartDestination(R.id.signUpFragment)
//                        }
//                    navController.graph
//                }
//            }
//        } else {
//            savedInstanceState.classLoader
//        }
//        if (!preferencesHelper.isShownOnBoard) {
//            navController.graph =
//                navController.navInflater.inflate(R.navigation.registration_graph).apply {
//                    setStartDestination(R.id.onBoardFragment)
//                }
//
//        } else {
//            if (!preferencesHelper.isRegisterShow) {
//                navController.graph =
//                    navController.navInflater.inflate(R.navigation.registration_graph).apply {
//                        setStartDestination(R.id.signUpFragment)
//                    }
//            } else {
//                navController.graph = navController.navInflater.inflate(R.navigation.note_graph)
//            }
//        }
//    }
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

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_note)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}