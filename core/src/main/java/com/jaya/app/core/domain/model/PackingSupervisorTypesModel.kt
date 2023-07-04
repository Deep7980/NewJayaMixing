package com.jaya.app.core.domain.model

data class PackingSupervisorTypesModel(
    val message: String,
    val packingSupervisorList: List<PackingSupervisorTypes>,
    val status: Boolean
)
data class PackingSupervisorTypes(
    val id:String,
    val name: String
)
