package com.jaya.app.jayamixing.presentation.viewmodels

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.jaya.app.core.utils.AppNavigator
import com.jaya.app.jayamixing.ui.theme.Primary
import com.jaya.app.jayamixing.ui.theme.SplashGreen
import com.jaya.app.jayamixing.utility.AppConnectivity
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class BaseViewModel @Inject constructor(
    val appNavigator: AppNavigator,
    val connectivity: AppConnectivity,
):ViewModel() {
    var videoUriList= mutableStateListOf<Uri>()
    var videoMultipartList= mutableStateListOf<MultipartBody.Part>()
    var videoShootTime= mutableStateListOf<String>()
    var storedLoginEmail= mutableStateOf("")
    var storedLoginPassword = mutableStateOf("")
    var versionCode= mutableStateOf(0)
    var statusBarColor= mutableStateOf(Primary)
    var getStartedSelectedPlant = mutableStateOf("")
    var userName :String=""
    var selectedFloor = mutableStateOf("")
    var selectedPlant = mutableStateOf("Select Plant")
}