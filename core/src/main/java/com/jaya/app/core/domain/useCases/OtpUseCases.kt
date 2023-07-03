package com.jaya.app.core.domain.useCases

import android.provider.ContactsContract.Data
import com.jaya.app.core.common.DataEntry
import com.jaya.app.core.common.Destination
import com.jaya.app.core.common.EmitType
import com.jaya.app.core.common.Resource
import com.jaya.app.core.common.handleFailedResponse
import com.jaya.app.core.domain.repositories.LoginRepository
import com.jaya.app.core.domain.repositories.OtpRepository
import com.jaya.app.core.helpers.AppStore
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OtpUseCases @Inject constructor(
    private val otpRepository: OtpRepository,
    private val loginRepository: LoginRepository,
    private val appStore: AppStore
) {

    fun verifyOtp()= flow {
        emit(DataEntry(EmitType.Loading,true))
        when(val response = otpRepository.verifyOtp()){
            is Resource.Success ->{
                emit(DataEntry(EmitType.Loading,false))
                response.data?.apply {
                    when(status){
                        true ->{
                            if(isMatched){
                                appStore.login(userId)
                                emit(DataEntry(EmitType.BackendSuccess,message))
                                emit(DataEntry(EmitType.Navigate,Destination.Dashboard))
                            }else{
                                emit(DataEntry(EmitType.BackendError,message))
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
                    message = response.message,
                    emitType = EmitType.NetworkError
                )
            }

            else -> {

            }

        }
    }

    fun resendOtp() = flow {
        emit(DataEntry(EmitType.Loading, true))
        when (val response = loginRepository.getOtp()) {
            is Resource.Success -> {
                emit(DataEntry(EmitType.Loading, false))
                response.data?.apply {
                    when (status) {
                        true -> {
                            if (isUser) {
                                emit(DataEntry(type = EmitType.BackendSuccess, value ="OTP=$otp"))
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
                    message = response.message,
                    emitType = EmitType.NetworkError
                )
            }

            else -> {

            }
        }
    }
}