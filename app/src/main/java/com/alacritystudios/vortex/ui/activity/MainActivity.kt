package com.alacritystudios.vortex.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.alacritystudios.vortex.R
import com.alacritystudios.vortex.databinding.ActivityMainBinding
import com.alacritystudios.vortex.util.ThemeUtil
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    internal lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    internal lateinit var themeUtil: ThemeUtil
    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        setThemeForActivity()
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main, null)
        initialiseUI()
    }

    private fun setToolbarForActivity() {
        setSupportActionBar(activityMainBinding.toolbar)
    }

    private fun setThemeForActivity() {
        setTheme(themeUtil.setThemeFromPreferences())
    }

    private fun initialiseUI(): Unit {
        setToolbarForActivity()
        val navController = Navigation.findNavController(this, R.id.fragment)
        NavigationUI.setupWithNavController(activityMainBinding.bnvMainActivity, navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (isInternalFragment(destination.id)) {
                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                with(activityMainBinding.bnvMainActivity) {
                    if (visibility == View.VISIBLE && alpha == 1f) {
                        animate()
                            .alpha(0f)
                            .withEndAction { visibility = View.GONE }
                            .duration = 500
                    }
                }
            } else {
                supportActionBar!!.setDisplayHomeAsUpEnabled(false)
                with(activityMainBinding.bnvMainActivity) {
                    visibility = View.VISIBLE
                    animate()
                        .alpha(1f)
                        .duration = 500
                }
            }
        }
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentDispatchingAndroidInjector
    }

    fun isInternalFragment(id: Int): Boolean {
        if (id == R.id.gameFragment) {
            return true
        } else {
            return false
        }
    }
}
