package com.jaya.app.core.domain.repositories

import com.jaya.app.core.common.Resource
import com.jaya.app.core.domain.model.GetOtpModel

interface LoginRepository {

    suspend fun getOtp(): Resource<GetOtpModel>
}