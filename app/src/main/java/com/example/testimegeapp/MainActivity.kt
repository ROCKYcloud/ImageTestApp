package com.example.testimegeapp

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.testimegeapp.databinding.ActivityMainBinding
import com.example.testimegeapp.utils.NavigationDispatcher
import com.example.testimegeapp.utils.setLightNavigationBars
import com.example.testimegeapp.utils.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applySystemWindowInsetsToPadding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var currentNavController: LiveData<NavController>? = null
    private val destinationChangedListener =
        NavController.OnDestinationChangedListener { _, destination, _ ->
            // TODO show/hide bottom navigation per destination
            when (destination.id) {
                R.id.imageFragment,
                R.id.listFragment -> {
                    toggleNavigation(false); binding.activityRoot.setLightNavigationBars(false)
                }
                else -> {
                    toggleNavigation(true); binding.activityRoot.setLightNavigationBars(false)
                }
            }
        }
    @Inject
    lateinit var navigationDispatcher: NavigationDispatcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lockIntoPortrait()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        applyEdgeToEdge()
        if (savedInstanceState == null) initNavigation(restore = false)
        lifecycleScope.launchWhenResumed { observeNavigationCommands() }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        initNavigation(restore = true)
    }

    private fun initNavigation(restore: Boolean) {
        binding.bnv.setupWithNavController(
                navGraphIds = listOf(
                        R.navigation.nav_graph,
                ),
                fragmentManager = supportFragmentManager,
                containerId = R.id.fragmentHost,
                intent = intent
        ).also { controller ->
            if (!restore) startDestination().let { id -> controller.value?.navigate(id) }
            controller.observe(this) { navController ->
                with(navController) {
                    removeOnDestinationChangedListener(destinationChangedListener)
                    addOnDestinationChangedListener(destinationChangedListener)
                }
            }
            currentNavController = controller
        }
    }

    private fun startDestination(): Int {
        return  R.id.listFragment
    }

    private fun lockIntoPortrait() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    private fun applyEdgeToEdge() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    private suspend fun observeNavigationCommands() {
        for (command in navigationDispatcher.navigationEmitter) {
            command.invoke(Navigation.findNavController(this@MainActivity, R.id.fragmentHost))
        }
    }

    private fun toggleNavigation(on: Boolean) = binding.bnv.also { bnv ->
        when {
            on && !bnv.isVisible -> {
                bnv.isEnabled = true
                bnv.isVisible = true
            }
            !on && !bnv.isInvisible -> {
                bnv.isEnabled = false
                bnv.isInvisible = true
            }
        }
    }
}
