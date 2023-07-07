package com.jaya.app.core.domain.repositories

import com.jaya.app.core.common.Resource
import com.jaya.app.core.domain.model.GetLoginModel

interface LoginRepository {

    suspend fun login(): Resource<GetLoginModel>
}