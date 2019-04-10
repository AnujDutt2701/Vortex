package com.alacritystudios.vortex.ui.fragment.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.alacritystudios.vortex.R
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class FollowingFragment : Fragment() {

    @Inject
    internal lateinit var mViewModelFactory: ViewModelProvider.Factory

    private var mViewModel: FollowingViewModel? = null

    fun newInstance(): FollowingFragment {
        return FollowingFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.following_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(FollowingViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

}
