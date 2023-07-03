package com.jaya.app.core.domain.model

data class UserDetailsModel(
    val message: String,
    val status: Boolean,
    val user_data: UserData
)

data class UserData(
    val address: String,
    val designation: String,
    val district_id: String,
    val email: String,
    val joining_date: String,
    val mobile: String,
    val name: String,
    val pincode: String,
    val state_id: String,
    val user_id: String
)