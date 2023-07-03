package com.jaya.app.jayamixing.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.jaya.app.core.domain.useCases.OtpUseCases
import com.jaya.app.core.helpers.AppStore
import com.jaya.app.core.utils.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class OtpViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val otpUseCases: OtpUseCases,
    private val pref: AppStore,
    savedStateHandle: SavedStateHandle
):ViewModel() {

    var isEmailReadOnly= mutableStateOf(true)
    var otpNumber= mutableStateOf("")
    var errorMessage = mutableStateOf("")
    var successMessage = mutableStateOf("")
    var color = mutableStateOf(Color.Gray)
    var loadingButton = mutableStateOf(true)
    var loadingg = mutableStateOf(false)
    var resendButtonTxt = mutableStateOf("Resend OTP.")

//    fun verifyOtp(){
//        otpUseCases.verifyOtp()
//            .flowOn(Dispatchers.IO)
//            .onEach {
//                when(it.type)
//            }
//    }
}