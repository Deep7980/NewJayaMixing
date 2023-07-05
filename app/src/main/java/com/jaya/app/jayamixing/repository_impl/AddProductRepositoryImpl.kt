package com.jaya.app.jayamixing.repository_impl

import com.jaya.app.core.common.Resource
import com.jaya.app.core.domain.model.CuttingManTypesModel
import com.jaya.app.core.domain.model.FloorManagerTypesModel
import com.jaya.app.core.domain.model.MixingLabourModel
import com.jaya.app.core.domain.model.MixingManTypesModel
import com.jaya.app.core.domain.model.OvenManTypesModel
import com.jaya.app.core.domain.model.PackingSupervisorTypesModel
import com.jaya.app.core.domain.model.ProductTypesModel
import com.jaya.app.core.domain.repositories.AddProductRepository
import com.jaya.app.jayamixing.module.MyApiList
import javax.inject.Inject

class AddProductRepositoryImpl @Inject constructor(
    private val myApiList: MyApiList
):AddProductRepository {
    override suspend fun getFloorManagerTypes(): Resource<FloorManagerTypesModel> {
        return try {
            Resource.Success(myApiList.getFloorTypes())
        } catch (ex: Exception) {
            Resource.Error(message = ex.message)
        }
    }

    override suspend fun getProductTypes(): Resource<ProductTypesModel> {
        return try {
            Resource.Success(myApiList.getProductTypes())
        } catch (ex: Exception) {
            Resource.Error(message = ex.message)
        }
    }

    override suspend fun getMixingManTypes(): Resource<MixingManTypesModel> {
        return try {
            Resource.Success(myApiList.getMixingManTypes())
        } catch (ex: Exception) {
            Resource.Error(message = ex.message)
        }
    }

    override suspend fun getCuttingManTypes(): Resource<CuttingManTypesModel> {
        return try {
            Resource.Success(myApiList.getCuttingManTypes())
        } catch (ex: Exception) {
            Resource.Error(message = ex.message)
        }
    }

    override suspend fun getOvenManTypes(): Resource<OvenManTypesModel> {
        return try {
            Resource.Success(myApiList.getOvenManTypes())
        } catch (ex: Exception) {
            Resource.Error(message = ex.message)
        }
    }

    override suspend fun getPackingSupervisorTypes(): Resource<PackingSupervisorTypesModel> {
        return try {
            Resource.Success(myApiList.getPackingSupervisorTypes())
        } catch (ex: Exception) {
            Resource.Error(message = ex.message)
        }
    }

    override suspend fun getMixingLabourTypes(): Resource<MixingLabourModel> {
        return try {
            Resource.Success(myApiList.getMixingLabourDetails())
        } catch (ex: Exception) {
            Resource.Error(message = ex.message)
        }
    }
}