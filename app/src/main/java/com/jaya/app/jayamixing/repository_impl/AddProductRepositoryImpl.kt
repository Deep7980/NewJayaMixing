package com.jaya.app.jayamixing.repository_impl

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
import com.jaya.app.core.domain.repositories.AddProductRepository
import com.jaya.app.jayamixing.module.MyApiList
import okhttp3.MultipartBody
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

    override suspend fun getMixingLabourTypes(userId: String,query:String): List<MixingLabourList> {
        if (query.isEmpty() || query.isBlank()) return emptyList()

        val results = myApiList.getMixingLabourDetails() ?: return emptyList()

        return results.filter {
            if (query.length in 2..3) {
                query.trim()
                    .contains(Regex("(^[a-zA-Z]*+)|([0-9]{2}$)", option = RegexOption.COMMENTS))
            } else {
                it.name.lowercase().replace(Regex("[^a-zA-Z\\d:]"), "")
                    .contains(Regex(query.lowercase().trim()))
            }
        }.toList()
    }

    override suspend fun getCuttingLabourTypes(userId: String,query: String): List<CuttingLabourList> {
        if (query.isEmpty() || query.isBlank()) return emptyList()

        val results = myApiList.getCuttingLabourDetails() ?: return emptyList()

        return results.filter {
            if (query.length in 2..3) {
                query.trim()
                    .contains(Regex("(^[a-zA-Z]*+)|([0-9]{2}$)", option = RegexOption.COMMENTS))
            } else {
                it.name.lowercase().replace(Regex("[^a-zA-Z\\d:]"), "")
                    .contains(Regex(query.lowercase().trim()))
            }
        }.toList()
    }

//    override suspend fun getMixingAndCuttingLabourDetails(query: String): List<MixingAndCuttingLabourModel> {
//        if (query.isEmpty() || query.isBlank()) return emptyList()
//
//        val results = myApiList.getMixingAndCuttingLabourListData()
//
//        return results.filter {
//            if (query.length in 2..3) {
//                query.trim()
//                    .contains(Regex("(^[a-zA-Z]*+)|([0-9]{2}$)", option = RegexOption.COMMENTS))
//            } else {
//                it.mixingLabourList.toString().replace(Regex("[^a-zA-Z\\d:]"), "")
//                    .contains(Regex(query.lowercase().trim()))
//            }
//        }.toList()
//    }


    override suspend fun getSupervisorPrefilledData(shift: String,plant: String,userId:String): Resource<SupervisorPrefilledDataResponse> {
       return try{
           Resource.Success(myApiList.getSupervisorPrefilledData())
       }catch (ex:Exception){
           Resource.Error(message = ex.message)
       }
    }



    override suspend fun submitPackingSupervisorDetails(
        user_id: String,
        shift: String,
        plant: String,
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
    ): Resource<AddDetailsModel> {
        return try {
            Resource.Success(myApiList.submitPackingSupervisorDetails(user_id,shift,plant,floorManager_name,products_name,mixing_man, cutting_man, oven_man, packingSupervisor_name, mixingLabourList, cuttingLabourList, leftDoughValue, brokenAddedValue, productDesc, productImage))
        } catch (ex: Exception) {
            Resource.Error(message = ex.message)
        }
    }
}