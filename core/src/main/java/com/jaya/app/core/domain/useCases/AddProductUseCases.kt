package com.jaya.app.core.domain.useCases

import android.util.Log
import com.jaya.app.core.common.DataEntry
import com.jaya.app.core.common.EmitType
import com.jaya.app.core.common.Resource
import com.jaya.app.core.common.handleFailedResponse
import com.jaya.app.core.domain.repositories.AddProductRepository
import com.jaya.app.core.helpers.AppStore
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddProductUseCases @Inject constructor(
    private val appStore: AppStore,
    private val addProductRepository: AddProductRepository
) {

    fun getSupervisorPrefilledData(userId:String) = flow{
        emit(DataEntry(EmitType.Loading, true))
        when (val response = addProductRepository.getSupervisorPrefilledData(userId)) {//appStore.userId()
            //when (val response =
            is Resource.Success -> {
                emit(DataEntry(EmitType.Loading, false))
                response.data?.apply {
                    Log.d("responseData", "getSupervisorPrefilledData: ${response.data.message}")
                    when (status) {
                        true -> {
//                            emit(DataEntry(type = EmitType.ALL_DETAILS, value = supervisorPrefilledDataResponse))
                            emit(
                                DataEntry(
                                    type = EmitType.FLOOR_LIST,
                                    supervisorPrefilledDataResponse.floorManagerList
                                )
                            )
                            emit(
                                DataEntry(
                                    type = EmitType.PRODUCT_TYPES,
                                    supervisorPrefilledDataResponse.productsList
                                )
                            )

                            emit(
                                DataEntry(
                                    type = EmitType.SUPERVISOR_NAME,
                                    supervisorPrefilledDataResponse.superVisorName
                                )
                            )

                            emit(
                                DataEntry(
                                    type = EmitType.MIXING_MAN_LIST,
                                    supervisorPrefilledDataResponse.mixingManList
                                )
                            )

                            emit(
                                DataEntry(
                                    type = EmitType.CUTTING_MAN_LIST,
                                    supervisorPrefilledDataResponse.cuttingManList
                                )
                            )

                            emit(
                                DataEntry(
                                    type = EmitType.OVEN_MAN_LIST,
                                    supervisorPrefilledDataResponse.ovenManList
                                )
                            )

                            emit(
                                DataEntry(
                                    type = EmitType.PACKING_SUPERVISOR_LIST,
                                    supervisorPrefilledDataResponse.packingSupervisorList
                                )
                            )


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

    fun getProductTypes() = flow {
        emit(DataEntry(EmitType.Loading, true))
        when (val response = addProductRepository.getProductTypes()) {//appStore.userId()
            //when (val response =
            is Resource.Success -> {
                emit(DataEntry(EmitType.Loading, false))
                response.data?.apply {
                    when (status) {
                        true -> {
                            emit(DataEntry(type = EmitType.PRODUCT_TYPES, value = product_type_list))
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

    fun getFloorTypes() = flow{
        emit(DataEntry(EmitType.Loading,true))
        when(val response = addProductRepository.getFloorManagerTypes()){
            is Resource.Success ->{
                emit(DataEntry(EmitType.Loading,false))
                response.data?.apply {
                    when(status){
                        true ->{
                            emit(DataEntry(EmitType.FLOOR_LIST,floor_type_list))
                        }
                        false ->{
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

    fun getMixingManTypes() = flow{
        emit(DataEntry(EmitType.Loading,true))
        when(val response = addProductRepository.getMixingManTypes()){
            is Resource.Success ->{
                emit(DataEntry(EmitType.Loading,false))
                response.data?.apply {
                    when(status){
                        true ->{
                            emit(DataEntry(EmitType.MIXING_MAN_LIST,mixingManList))
                        }
                        false ->{
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

    fun getCuttingManTypes() = flow{
        emit(DataEntry(EmitType.Loading,true))
        when(val response = addProductRepository.getCuttingManTypes()){
            is Resource.Success ->{
                emit(DataEntry(EmitType.Loading,false))
                response.data?.apply {
                    when(status){
                        true ->{
                            emit(DataEntry(EmitType.CUTTING_MAN_LIST,cuttingManList))
                        }
                        false ->{
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

    fun getOvenManTypes() = flow{
        emit(DataEntry(EmitType.Loading,true))
        when(val response = addProductRepository.getOvenManTypes()){
            is Resource.Success ->{
                emit(DataEntry(EmitType.Loading,false))
                response.data?.apply {
                    when(status){
                        true ->{
                            emit(DataEntry(EmitType.OVEN_MAN_LIST,ovenManList))
                        }
                        false ->{
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

    fun getPackingSupervisorTypes() = flow{
        emit(DataEntry(EmitType.Loading,true))
        when(val response = addProductRepository.getPackingSupervisorTypes()){
            is Resource.Success ->{
                emit(DataEntry(EmitType.Loading,false))
                response.data?.apply {
                    when(status){
                        true ->{
                            emit(DataEntry(EmitType.PACKING_SUPERVISOR_LIST,packingSupervisorList))
                        }
                        false ->{
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

    fun getMixingLabourTypes(query:String) = flow{
        emit(DataEntry(EmitType.Loading,false))
        val searchMixingLabours = addProductRepository.getMixingLabourTypes(query)
        emit(DataEntry(EmitType.Loading,true))
        if(searchMixingLabours.isNotEmpty()){
            emit(DataEntry(EmitType.MIXING_LABOUR_LIST,searchMixingLabours))
        }else{
            emit(DataEntry(EmitType.NO_MIXING_LABOUR,null))
        }
    }

    fun getCuttingLabourTypes(query:String) = flow{
        emit(DataEntry(EmitType.Loading,false))
        val searchCuttingLabours = addProductRepository.getCuttingLabourTypes(query)
        emit(DataEntry(EmitType.Loading,true))
        if(searchCuttingLabours.isNotEmpty()){
            emit(DataEntry(EmitType.CUTTING_LABOUR_LIST,searchCuttingLabours))
        }else{
            emit(DataEntry(EmitType.NO_CUTTING_LABOUR,null))
        }
    }



}