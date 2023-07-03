package com.jaya.app.jayamixing.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaya.app.core.common.Destination
import com.jaya.app.core.common.EmitType
import com.jaya.app.core.domain.useCases.LoginUseCases
import com.jaya.app.core.helpers.AppStore
import com.jaya.app.core.utils.AppNavigator
import com.jaya.app.jayamixing.extensions.castValueToRequiredTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.Timer
import javax.inject.Inject
import kotlin.concurrent.schedule

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val loginUseCases: LoginUseCases,
    private val pref: AppStore,
    savedStateHandle: SavedStateHandle
):ViewModel() {

    var emailText = mutableStateOf("")
    var password = mutableStateOf("")
    var errorMessage = mutableStateOf("")
    var successMessage = mutableStateOf("")
    var color = mutableStateOf(Color.Gray)
    var loadingButton = mutableStateOf(true)
    var loadingg = mutableStateOf(false)



    fun getOtp() {
        // viewModelScope.launch {
        loginUseCases.getOtp()
            .flowOn(Dispatchers.IO)
            .onEach {
                when (it.type) {

                    EmitType.BackendSuccess -> {
                        it.value?.castValueToRequiredTypes<String>()?.let {
                            successMessage.value = it
                            Log.d("getOtp", "getOtp: ${successMessage.value}++++")
                            //_successMessage.update { it }

                        }

                    }

                    EmitType.BackendError -> {
                        it.value?.apply {
                            castValueToRequiredTypes<String>()?.let {
                                errorMessage.value = it
                            }
                        }
                    }
                    EmitType.Loading -> {
                        it.value?.castValueToRequiredTypes<Boolean>()?.let {
                            loadingButton.value = it
                        }
                    }

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
            }.launchIn(viewModelScope)
        // }
    }





}