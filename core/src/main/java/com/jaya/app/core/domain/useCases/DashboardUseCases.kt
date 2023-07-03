package com.jaya.app.core.domain.useCases

import androidx.lifecycle.SavedStateHandle
import com.jaya.app.core.common.DataEntry
import com.jaya.app.core.common.Destination
import com.jaya.app.core.common.EmitType
import com.jaya.app.core.common.Resource
import com.jaya.app.core.common.handleFailedResponse
import com.jaya.app.core.domain.repositories.DashboardRepository
import com.jaya.app.core.helpers.AppStore
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DashboardUseCases @Inject constructor(
    private val appStore: AppStore,
    private val dashboardRepository: DashboardRepository
) {
    fun logOutFromDashboard() = flow {
        appStore.logout()
        emit(DataEntry(type = EmitType.Navigate, Destination.Login))
    }


    fun getProductDetails() = flow{
        emit(DataEntry(EmitType.Loading,true))
        when(val response = dashboardRepository.getProductDetails()) {
            is Resource.Success ->{
                emit(DataEntry(EmitType.Loading,false))
                response.data?.apply {
                    when(status){
                        true ->{
                            emit(DataEntry(EmitType.PROD_DATA,prod_response))
                        }
                        else ->{
                            emit(DataEntry(EmitType.BackendError,message))
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
    fun getUserDetails() = flow {
        emit(DataEntry(EmitType.Loading, true))
        when (val response = dashboardRepository.getUserDetails()) {//appStore.userId()
            //when (val response =
            is Resource.Success -> {
                emit(DataEntry(EmitType.Loading, false))
                response.data?.apply {
                    when (status) {
                        true -> {
                            emit(DataEntry(type = EmitType.USER_DATA, value = user_data))
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