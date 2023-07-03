package com.jaya.app.jayamixing.repository_impl

import com.jaya.app.core.common.Resource
import com.jaya.app.core.domain.model.ProductSubmitModel
import com.jaya.app.core.domain.model.UserDetailsModel
import com.jaya.app.core.domain.repositories.DashboardRepository
import com.jaya.app.jayamixing.module.MyApiList
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(
    private val myApiList: MyApiList
):DashboardRepository {
    override suspend fun getUserDetails(): Resource<UserDetailsModel> {
        return try {
            Resource.Success(myApiList.getUserDetails())
        } catch (ex: Exception) {
            Resource.Error(message = ex.message)
        }
    }

    override suspend fun getProductDetails(): Resource<ProductSubmitModel> {
        return try {
            Resource.Success(myApiList.getProductDetails())
        } catch (ex: Exception) {
            Resource.Error(message = ex.message)
        }
    }
}