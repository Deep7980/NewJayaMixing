package com.jaya.app.core.domain.useCases

import com.jaya.app.core.common.DataEntry
import com.jaya.app.core.common.Destination
import com.jaya.app.core.common.EmitType
import com.jaya.app.core.common.Resource
import com.jaya.app.core.common.handleFailedResponse
import com.jaya.app.core.domain.repositories.SplashRepository
import com.jaya.app.core.helpers.AppStore
import com.jaya.app.core.helpers.Info
import com.jaya.app.core.utils.AppNavigator
import com.jaya.app.core.utils.AppPreference
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SplashUseCases @Inject constructor(
    private val splashRepository: SplashRepository,
    private val appInfo: Info,
    private val appStore: AppStore,
    private val appNavigator: AppNavigator
) {


    fun appMaintenanceApi(currentAppVersion: Int) = flow {
        emit(DataEntry(EmitType.Loading, true))
        when (val response = splashRepository.appMaintenance(currentAppVersion)) {
            is Resource.Success -> {
                emit(DataEntry(EmitType.Loading, false))
                response.data?.apply {
                    when (status) {
                        true -> {
                            appStore.setBaseUrl(domain.base, domain.changeImmediate)
                            if (version.isUpdateAvailable) {
                                emit(DataEntry(EmitType.VERSION_UPDATE, version))
                            } else {
                                emit(DataEntry(EmitType.CALL_NEXT_API, null))
                            }
                        }

                        false -> handleFailedResponse(response)
                    }
                }
            }

            is Resource.Loading -> {

            }

            is Resource.Error -> {
                throw IllegalArgumentException(response.message)
            }
        }

    }


//    fun getBaseUrl() = flow{
//        when (val response = splashRepository.baseUrl()) {
//            is Resource.Success -> {
//                response.data?.apply {
//                    when (status) {
//                        true -> {
//                            emit(DataEntry(type = EmitType.BaseUrl, value = base_url))
//                        }
//                        else -> {
//                            emit(DataEntry(type = EmitType.BackendError, value = message))
//                        }
//                    }
//                }
//            }
//            is Resource.Error -> {
//                handleFailedResponse(
//                    response = response,
//                    message = response.message,
//                    emitType = EmitType.NetworkError
//                )
//            }
//            else -> {
//
//            }
//        }
//    }
//
//
//    fun checkAppVersion() = flow {
//        val currentVersion = appInfo.getCurrentVersion()
//        when (val response = splashRepository.appVersion(currentVersion)) {
//            is Resource.Success -> {
//                response.data?.apply {
//                    when (status) {
//                        true -> {
//                            if (currentVersion < appVersion.versionCode.toInt()) {
//                                emit(DataEntry(type = EmitType.AppVersion, value = appVersion))
//                            } else {
//                                if(appStore.isLoggedIn()) {
//                                    emit(DataEntry(type = EmitType.Navigate,value = Destination.Dashboard))
//                                } else {
//                                    emit(DataEntry(type = EmitType.Navigate,value = Destination.Login))
//                                }
//                            }
//                        }
//                        else -> {
//                            emit(DataEntry(type = EmitType.BackendError, value = message))
//                        }
//                    }
//                }
//            }
//            is Resource.Error -> {
//                handleFailedResponse(
//                    response = response,
//                    message = response.message,
//                    emitType = EmitType.NetworkError
//                )
//            }
//            else -> {
//
//            }
//        }
//    }


    fun navigateToAppropiateScreen() = flow {
        if(appStore.isLoggedIn()) {
            emit(DataEntry(type = EmitType.Navigate, value = Destination.Dashboard))
        } else {
            emit(DataEntry(type = EmitType.Navigate, value = Destination.Login))
        }
    }

    fun decideNavigation() = flow {

        if (!appStore.login()) {
            emit(
                DataEntry(
                    EmitType.NAVIGATE, appNavigator.navigateTo(
                        Destination.Login(),
                        Destination.Splash(),
                        inclusive = true,
                        isSingleTop = true

                    )
                )
            )
        } else {
            emit(
                DataEntry(
                    EmitType.NAVIGATE, appNavigator.navigateTo(
                        Destination.Dashboard(),
                        Destination.Splash(),
                        inclusive = true,
                        isSingleTop = true

                    )
                )
            )
        }

    }
}