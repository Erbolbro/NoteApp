package com.example.kotlin3_3.ui.fragments.noteapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.kotlin3_3.R
import com.example.kotlin3_3.databinding.FragmentNoteFlowBinding
import com.google.firebase.auth.FirebaseAuth

class NoteFlowFragment : Fragment() {
    private var _binding: FragmentNoteFlowBinding? = null
    private val binding get() = _binding!!
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private val fireBaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteFlowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userProfile()
        setupNavigation()
        setupDrawer()
    }

    private fun userProfile() {
        val user = fireBaseAuth.currentUser
        if (user != null) {
            val email = user.email
        }
    }


    private fun setupNavigation() {
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.nav_host_fragment_note) as NavHostFragment
        val navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.settingsFragment,
                R.id.profileFragment,
                R.id.shareFragment,
                R.id.devFragment,
                R.id.exitFragment
            ),
            drawerLayout
        )
        binding.navView.setupWithNavController(navController)
        binding.appBarMain.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun setupDrawer() {
        val toolbar: Toolbar = binding.appBarMain.toolbar
        actionBarDrawerToggle = ActionBarDrawerToggle(
            requireActivity(),
            binding.drawerLayout,
            toolbar,
            R.string.nav_open,
            R.string.nav_close
        )
        if (toolbar != null) {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        } else {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
