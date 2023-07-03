package com.jaya.app.jayamixing.repository_impl

import com.jaya.app.core.common.Resource
import com.jaya.app.core.domain.model.VerifyOtpModel
import com.jaya.app.core.domain.repositories.OtpRepository
import com.jaya.app.jayamixing.module.MyApiList
import javax.inject.Inject

class OtpRepositoryImpl @Inject constructor(
    private val myApiList: MyApiList
):OtpRepository {
    override suspend fun verifyOtp(): Resource<VerifyOtpModel> {
        return try{
            Resource.Success(myApiList.verifyOtp())
        }catch (ex: Exception){
           Resource.Error(ex.message)
        }
    }
}