package com.jaya.app.core.domain.repositories

import com.jaya.app.core.common.Resource
import com.jaya.app.core.domain.model.ProductSubmitModel
import com.jaya.app.core.domain.model.UserDetailsModel

interface DashboardRepository {

    suspend fun getUserDetails(): Resource<UserDetailsModel>

    suspend fun getProductDetails(): Resource<ProductSubmitModel>
}