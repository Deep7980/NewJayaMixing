package com.jaya.app.core.domain.repositories

import com.jaya.app.core.common.Resource
import com.jaya.app.core.domain.model.VerifyOtpModel

interface OtpRepository {

    suspend fun verifyOtp():Resource<VerifyOtpModel>
}