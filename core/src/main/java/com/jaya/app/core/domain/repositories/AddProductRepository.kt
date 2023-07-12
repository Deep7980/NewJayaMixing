package com.jaya.app.core.domain.repositories

import com.jaya.app.core.common.Resource
import com.jaya.app.core.domain.model.AddDetailsModel
import com.jaya.app.core.domain.model.CuttingLabourList
import com.jaya.app.core.domain.model.CuttingManTypesModel
import com.jaya.app.core.domain.model.FloorManagerTypesModel
import com.jaya.app.core.domain.model.MixingLabourList
import com.jaya.app.core.domain.model.MixingManTypesModel
import com.jaya.app.core.domain.model.OvenManTypesModel
import com.jaya.app.core.domain.model.PackingSupervisorTypesModel
import com.jaya.app.core.domain.model.ProductTypesModel

import com.jaya.app.core.domain.model.SupervisorPrefilledDataResponse

import okhttp3.MultipartBody


interface AddProductRepository {
    suspend fun getFloorManagerTypes(): Resource<FloorManagerTypesModel>

    suspend fun getProductTypes(): Resource<ProductTypesModel>

    suspend fun getMixingManTypes(): Resource<MixingManTypesModel>

    suspend fun getCuttingManTypes(): Resource<CuttingManTypesModel>

    suspend fun getOvenManTypes(): Resource<OvenManTypesModel>

    suspend fun getPackingSupervisorTypes(): Resource<PackingSupervisorTypesModel>

    suspend fun getMixingLabourTypes(userId: String,query:String): List<MixingLabourList>

    suspend fun getCuttingLabourTypes(userId: String,query: String): List<CuttingLabourList>

//    suspend fun getMixingAndCuttingLabourDetails(query:String): List<MixingAndCuttingLabourModel>

    suspend fun getSupervisorPrefilledData(shift: String,plant: String,userId:String): Resource<SupervisorPrefilledDataResponse>

    suspend fun submitPackingSupervisorDetails(
        user_id: String,
        shift:String,
        plant:String,
        floorManager_name: String,
        products_name: String,
        mixing_man: String,
        cutting_man: String,
        oven_man: String,
        packingSupervisor_name: String,
        mixingLabourList: List<String>,
        cuttingLabourList: List<String>,
        leftDoughValue: String,
        brokenAddedValue: String,
        productDesc: String,
        productImage: List<MultipartBody.Part>
    ): Resource<AddDetailsModel>
}