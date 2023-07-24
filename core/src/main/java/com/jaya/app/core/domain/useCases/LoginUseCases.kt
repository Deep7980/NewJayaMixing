package com.jaya.app.core.domain.useCases

import com.jaya.app.core.common.DataEntry
import com.jaya.app.core.common.Destination
import com.jaya.app.core.common.EmitType
import com.jaya.app.core.common.Resource
import com.jaya.app.core.common.handleFailedResponse
import com.jaya.app.core.domain.repositories.LoginRepository
import com.jaya.app.core.helpers.AppStore
import io.ktor.client.utils.EmptyContent.status
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCases @Inject constructor(
    private val loginRepository: LoginRepository,
    private val appStore: AppStore
){

//    fun getOtp() = flow {
//        emit(DataEntry(EmitType.Loading, true))
//        when (val response = loginRepository.getOtp()) {
//            is Resource.Success -> {
//                emit(DataEntry(EmitType.Loading, false))
//                response.data?.apply {
//                    when (status) {
//                        true -> {
//                            if (isUser) {
//                                emit(DataEntry(type = EmitType.BackendSuccess, value ="$message \n  OTP=$otp"))
//                                emit(DataEntry(type = EmitType.Navigate, value = Destination.Otp))
//                            } else {
//                                emit(DataEntry(type = EmitType.BackendError, value =message))
//                            }
//                        }
//
//                        else -> {
//                            emit(DataEntry(type = EmitType.BackendError, value = message))
//                        }
//                    }
//                }
//            }
//
//            is Resource.Error -> {
//                handleFailedResponse(
//                    response = response,
//                    message = response.message,
//                    emitType = EmitType.NetworkError
//                )
//            }
//
//            else -> {
//
//            }
//        }
//    }

    fun login(email:String,password:String) = flow {
        emit(DataEntry(EmitType.Loading, true))
        when (val response = loginRepository.login(email,password)) {
            is Resource.Success -> {
                emit(DataEntry(EmitType.Loading, false))
                response.data?.apply {
                    when (status) {
                        true -> {
                            if (isUser) {
                                appStore.setUserCredentials(userCreds)
                                appStore.setUserId(userId)
                                emit(DataEntry(type = EmitType.BackendSuccess, value ="$message \n  USERID=$userId"))
                                emit(DataEntry(type = EmitType.Navigate, value = Destination.Dashboard))
                            } else {
                                emit(DataEntry(type = EmitType.BackendError, value =message))
                            }
                        }

                        else -> {
                            emit(DataEntry(type = EmitType.BackendError, value = message))
                        }
                    }
                }
            }

            is Resource.Error -> {
                handleFailedResponse(
                    response = response,
                    emitType = EmitType.NetworkError
                )
            }

            else -> {

            }
        }
    }
}