package com.jaya.app.jayamixing.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaya.app.core.common.Destination
import com.jaya.app.core.common.DialogData
import com.jaya.app.core.common.EmitType
import com.jaya.app.core.domain.useCases.DashboardUseCases
import com.jaya.app.core.utils.AppNavigator
import com.jaya.app.jayamixing.extensions.MyDialog
import com.jaya.app.jayamixing.extensions.castValueToRequiredTypes
import com.jaya.app.jayamixing.helpers_impl.SavableMutableState
import com.jaya.app.jayamixing.utility.UiData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val dashBoardUseCases: DashboardUseCases,
    private val appNavigator: AppNavigator,
    savedStateHandle: SavedStateHandle
):ViewModel() {


    val dataLoading = SavableMutableState(UiData.StateApiLoading,savedStateHandle,false)
    var userName = mutableStateOf("")
    var userId = mutableStateOf("")
    var emailId = mutableStateOf("")
    var designation = mutableStateOf("")
    var packagingShift = mutableStateOf("")
    var packagingPlant = mutableStateOf("")
    val dashboardBack = mutableStateOf<MyDialog?>(null)
    init {
        //getUserDetails()
        //getPackagingList()
    }

    fun onBackDialog() {
        dashboardBack.value = MyDialog(
            data = DialogData(
                title = "Jaya Mixing Supervisor App",
                message = "Are you sure you want to exit ?",
                positive = "Yes",
                negative = "No",
            )
        )
        handleDialogEvents()
    }

    private fun handleDialogEvents() {
        dashboardBack.value?.onConfirm = {

        }
        dashboardBack.value?.onDismiss = {
            dashboardBack.value?.setState(MyDialog.Companion.State.DISABLE)
        }
    }

    fun onLogoutFromDashboard() {
        viewModelScope.launch {
            dashBoardUseCases.logOutFromDashboard().collect {
                when (it.type) {
                    EmitType.Navigate -> {
                        it.value?.apply {

                            castValueToRequiredTypes<Destination.NoArgumentsDestination>()?.let { destination ->
                                appNavigator.tryNavigateTo(
                                    destination(),
                                    popUpToRoute = Destination.Login(),
                                    isSingleTop = true,
                                    inclusive = true
                                )
                            }

                        }
                    }

                    else -> {}
                }
            }
        }
    }

//    fun onDashBoardPageToAddProduct() {
//        appNavigator.tryNavigateTo(
//            route = Destination.AddProduct(),
//            // popUpToRoute = Destination.Dashboard(),
//            isSingleTop = true,
//            inclusive = true
//        )
//    }


}