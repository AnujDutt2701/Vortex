package com.alacritystudios.vortex.ui.fragment.home

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

import com.alacritystudios.vortex.R
import com.alacritystudios.vortex.ui.fragment.home.HomeFragment
import com.alacritystudios.vortex.ui.fragment.home.HomeViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class HomeFragment : Fragment() {

    @Inject
    internal lateinit var mViewModelFactory: ViewModelProvider.Factory

    private var mViewModel: HomeViewModel? = null

    fun newInstance(): HomeFragment {
        return HomeFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setToolbarText()
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    private fun setToolbarText() {
        (activity as AppCompatActivity).supportActionBar?.title = getText(R.string.nav_home_fragment)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

}
