package com.jaya.app.jayamixing.repository_impl

import com.jaya.app.core.common.Resource
import com.jaya.app.core.domain.model.GetLoginModel
import com.jaya.app.core.domain.repositories.LoginRepository
import com.jaya.app.jayamixing.module.MyApiList
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val myApiList: MyApiList
):LoginRepository {
    override suspend fun login(email:String,password:String): Resource<GetLoginModel> {
        return try {
            Resource.Success(myApiList.getLogin(email,password))
        } catch (ex: Exception) {
            Resource.Error(message = ex.message)
        }
    }
}