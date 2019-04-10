package com.alacritystudios.vortex.util;

import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.android.material.snackbar.Snackbar;
import timber.log.Timber;

public class UiUtil {

    public static void setLoadingBehaviour(SwipeRefreshLayout swipeRefreshLayout, RecyclerView recyclerView,
                                           ConstraintLayout constraintLayout, HttpResponseUtil httpResponseUtil) {
        Timber.d("setLoadingBehaviour");
        if (httpResponseUtil.getInitialLoad() == HttpResponseUtil.NetworkState.LOADING
                || httpResponseUtil.getNetworkLoad() == HttpResponseUtil.NetworkState.LOADING) {
            swipeRefreshLayout.setRefreshing(true);
        } else {
            swipeRefreshLayout.setRefreshing(false);
        }
        if (httpResponseUtil.getInitialLoad() == HttpResponseUtil.NetworkState.LOADING) {
            recyclerView.setVisibility(View.VISIBLE);
            constraintLayout.setVisibility(View.GONE);
            Timber.d("Initial loading.");
        } else if (httpResponseUtil.getInitialLoad() == HttpResponseUtil.NetworkState.LOADED &&
                httpResponseUtil.getNetworkLoad() == HttpResponseUtil.NetworkState.ERROR &&
                httpResponseUtil.getResponseState() == HttpResponseUtil.ResponseState.RESPONSE_UNSUCCESSFUL) {
            Timber.d("Pagination failed.");
            Snackbar.make(constraintLayout, "Pagination  failed.", 2000).show();
        } else if (httpResponseUtil.getInitialLoad() == HttpResponseUtil.NetworkState.ERROR &&
                httpResponseUtil.getNetworkLoad() == HttpResponseUtil.NetworkState.ERROR &&
                httpResponseUtil.getResponseState() == HttpResponseUtil.ResponseState.RESPONSE_UNSUCCESSFUL) {
            recyclerView.setVisibility(View.GONE);
            constraintLayout.setVisibility(View.VISIBLE);
            Timber.d("Initial load failed.");
        } else if (httpResponseUtil.getInitialLoad() == HttpResponseUtil.NetworkState.LOADED &&
                httpResponseUtil.getNetworkLoad() == HttpResponseUtil.NetworkState.LOADED &&
                httpResponseUtil.getResponseState() == HttpResponseUtil.ResponseState.RESPONSE_SUCCESSFUL) {
            recyclerView.setVisibility(View.VISIBLE);
            constraintLayout.setVisibility(View.GONE);
            Timber.d("Success.");
        }
    }
}
