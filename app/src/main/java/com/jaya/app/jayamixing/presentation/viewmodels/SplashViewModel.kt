package com.jaya.app.jayamixing.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaya.app.core.common.Destination
import com.jaya.app.core.common.DialogData
import com.jaya.app.core.common.EmitType
import com.jaya.app.core.domain.model.AppVersion
import com.jaya.app.core.domain.useCases.SplashUseCases
import com.jaya.app.core.helpers.AppStore
import com.jaya.app.core.utils.AppNavigator
import com.jaya.app.jayamixing.R
import com.jaya.app.jayamixing.extensions.MyDialog
import com.jaya.app.jayamixing.extensions.castValueToRequiredTypes
import com.jaya.app.jayamixing.utility.AppConnectivity
import com.jaya.app.jayamixing.utility.DialogEventHandler
import com.jaya.app.jayamixing.utility.DialogHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val splashUseCases: SplashUseCases,
    private val connectivity: AppConnectivity,
    private val pref: AppStore,
    savedStateHandle: SavedStateHandle
):ViewModel(),DialogEventHandler {

    val splashAnimation = mutableStateOf(false)

    val connectivityStatus = connectivity.connectivityStatusFlow

    val versionUpdateDialog = mutableStateOf<MyDialog?>(null)
    //val versionUpdateLink = SavableMutableState<String?>(UiData.AppStoreLink,savedStateHandle,null)
    val versionUpdateLink = mutableStateOf("")

    val updateApp = mutableStateOf<DialogHandler?>(null)

    val appPlayStoreLink = mutableStateOf<String?>(null)

    init {
        viewModelScope.launch {
            splashAnimation()
            appMaintenance()
            //getBaseUrl()
//            Timer().schedule(2000){
//               getBaseUrl()
//            }
        }

    }

    private suspend fun splashAnimation() {
        splashAnimation.value = true
        delay(500L)
        splashAnimation.value = false
    }

//    private fun getBaseUrl() {
//        splashUseCases.getBaseUrl()
//            .flowOn(Dispatchers.IO)
//            .onEach {
//                when (it.type) {
//                    EmitType.BaseUrl -> {
//                        it.value?.apply {
//                            castValueToRequiredTypes<String>()?.let {
//                                pref.storeBaseUrl(it)
//                                checkAppVersion()
//                            }
//                        }
//                    }
//
//                    EmitType.NetworkError -> {
//                        it.value?.apply {
//                            castValueToRequiredTypes<String>()?.let {
//
//                            }
//                        }
//                    }
//
//                    else -> {}
//                }
//            }.launchIn(viewModelScope)
//    }//getBaseUrl


//    private suspend fun checkAppVersion() {
//        splashUseCases.checkAppVersion()
//            .flowOn(Dispatchers.IO)
//            .collect {
//                when (it.type) {
//                    EmitType.BackendError -> {
//                        it.value?.apply {
//                            castValueToRequiredTypes<String>()?.let {
//
//                            }
//                        }
//                    }
//
//                    EmitType.AppVersion -> {
//                        it.value?.apply {
//                            castValueToRequiredTypes<AppVersion>()?.let { appVersion ->
//                                versionUpdateDialog.value = MyDialog(
//                                    data = DialogData(
//                                        title = appVersion.versionTitle,
//                                        message = appVersion.versionMessage,
//                                        positive = "Yes",
//                                        negative = "No",
//                                        data = appVersion
//                                    )
//                                )
//                                handleDialogEvents()
//                            }
//                        }
//                    }
//
//                    EmitType.Navigate -> {
//                        it.value?.apply {
//
//                            castValueToRequiredTypes<Destination.NoArgumentsDestination>()?.let { destination ->
//                                appNavigator.tryNavigateTo(
//                                    destination(),
//                                    popUpToRoute = Destination.Splash(),
//                                    isSingleTop = true,
//                                    inclusive = true
//                                )
//                            }
//
//                        }
//
//
//                    }
//
//                    else -> {}
//                }
//            }
//    }//checkAppVersion


    private fun handleDialogEvents() {
        versionUpdateDialog.value?.onConfirm = {
            it?.castValueToRequiredTypes<AppVersion>()?.apply {
                versionUpdateLink.value = versionUpdateLink.toString()
            }
        }
        versionUpdateDialog.value?.onDismiss = {
            versionUpdateDialog.value?.setState(MyDialog.Companion.State.DISABLE)
            splashUseCases.navigateToAppropiateScreen().onEach {
                when (it.type) {
                    EmitType.Navigate -> {
                        it.value?.apply {
                            castValueToRequiredTypes<Destination.NoArgumentsDestination>()?.let {
                                appNavigator.navigateTo(
                                    it(),
                                    popUpToRoute = Destination.Splash(),
                                    inclusive = true
                                )
                            }
                        }
                    }
                    else -> {}
                }
            }.launchIn(viewModelScope)
        }
    }//handleDialogEvents

    private fun appMaintenance() {
        splashUseCases
            .appMaintenanceApi(
                currentAppVersion = 1
            )
//            .retryWithExponentialBackoff(
//                initialDelay = 500.milliseconds,
//                factor = 2.0,
//                maxAttempt = 5
//            )
//            .catch {
//                emit(DataEntry(EntryType.NETWORK_ERROR, it.message))
//            }
            .onEach {
                when (it.type) {
                    EmitType.VERSION_UPDATE -> {
                        it.value?.castValueToRequiredTypes<AppVersion>()?.let { version ->
                            updateApp.value = DialogHandler(
                                data = DialogData(
                                    title = "Update App",
                                    message = version.description,
                                    positive = "UPDATE",
                                    negative = "SKIP",
                                    data = version
                                )
                            )
                            updateApp.value?.consumeEvents(this)
                        }
                    }
                    EmitType.CALL_NEXT_API->{
                        navigateFurther()
                    }

                    else -> {
//                        it.handleErrors()?.let {
//
//                        }
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun navigateFurther() {
        splashUseCases.decideNavigation().onEach {
            when (it.type) {
                EmitType.NAVIGATE -> {
//                    it.value?.castValueToRequiredTypes<Navigation>()?.apply {
//                        appNavigator.tryNavigateTo(
//                                    destination(),
//                                    popUpToRoute = Destination.Splash(),
//                                    isSingleTop = true,
//                                    inclusive = true
//                                )
//                    }

                    castValueToRequiredTypes<Destination.NoArgumentsDestination>()?.let { destination ->
                        appNavigator.tryNavigateTo(
                            destination(),
                            popUpToRoute = Destination.Splash(),
                            isSingleTop = true,
                            inclusive = true
                        )
                    }
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    override fun onConfirm(data: Any?) {
        if (data is AppVersion) {
            appPlayStoreLink.value = data.link
        }
        updateApp.value?.setState(DialogHandler.Companion.State.DISABLE)
    }

    override fun onDismiss(data: Any?) {
        updateApp.value?.setState(DialogHandler.Companion.State.DISABLE)
        navigateFurther()
    }

}