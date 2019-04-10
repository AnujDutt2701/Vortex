package com.alacritystudios.vortex.ui.fragment.browse

import android.util.Log
import androidx.lifecycle.ViewModelProvider

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.AndroidSupportInjection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

import com.alacritystudios.vortex.R

import javax.inject.Inject

class BrowseFragment : Fragment() {

    @Inject
    internal lateinit var mViewModelFactory: ViewModelProvider.Factory

    private var mViewModel: BrowseViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setToolbarText()
        return inflater.inflate(R.layout.browse_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(BrowseViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    private fun setToolbarText() {
        (activity as AppCompatActivity).supportActionBar?.title = "Browse"

    }

    companion object {

        fun newInstance(): BrowseFragment {
            return BrowseFragment()
        }
    }

}
