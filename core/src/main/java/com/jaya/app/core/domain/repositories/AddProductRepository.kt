package com.jaya.app.core.domain.repositories

import com.jaya.app.core.common.Resource
import com.jaya.app.core.domain.model.CuttingManTypesModel
import com.jaya.app.core.domain.model.FloorManagerTypesModel
import com.jaya.app.core.domain.model.MixingLabourList
import com.jaya.app.core.domain.model.MixingLabourModel
import com.jaya.app.core.domain.model.MixingManTypesModel
import com.jaya.app.core.domain.model.OvenManTypesModel
import com.jaya.app.core.domain.model.PackingSupervisorTypesModel
import com.jaya.app.core.domain.model.ProductTypesModel

interface AddProductRepository {
    suspend fun getFloorManagerTypes(): Resource<FloorManagerTypesModel>

    suspend fun getProductTypes(): Resource<ProductTypesModel>

    suspend fun getMixingManTypes(): Resource<MixingManTypesModel>

    suspend fun getCuttingManTypes(): Resource<CuttingManTypesModel>

    suspend fun getOvenManTypes(): Resource<OvenManTypesModel>

    suspend fun getPackingSupervisorTypes(): Resource<PackingSupervisorTypesModel>

    suspend fun getMixingLabourTypes(query:String): List<MixingLabourList>
}