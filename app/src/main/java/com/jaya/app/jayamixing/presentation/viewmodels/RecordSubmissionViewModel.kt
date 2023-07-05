package com.jaya.app.jayamixing.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.jaya.app.core.common.Destination
import com.jaya.app.core.utils.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecordSubmissionViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
):ViewModel() {

    fun onBackToAddProductPage() {
        appNavigator.tryNavigateTo(
            route = Destination.AddProduct(),
            //popUpToRoute = Destination.AddProduct(),
            isSingleTop = true,
            inclusive = true
        )
    }

    fun onBackToDashboardPage(){
        appNavigator.tryNavigateTo(
            route = Destination.Dashboard(),
            popUpToRoute = Destination.Dashboard(),
            isSingleTop = true,
            inclusive = true
        )
    }
}