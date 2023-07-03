package com.jaya.app.jayamixing.repository_impl

import com.jaya.app.core.common.Resource
import com.jaya.app.core.domain.model.GetOtpModel
import com.jaya.app.core.domain.repositories.LoginRepository
import com.jaya.app.jayamixing.module.MyApiList
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val myApiList: MyApiList
):LoginRepository {
    override suspend fun getOtp(): Resource<GetOtpModel> {
        return try {
            Resource.Success(myApiList.getOtp())
        } catch (ex: Exception) {
            Resource.Error(message = ex.message)
        }
    }
}