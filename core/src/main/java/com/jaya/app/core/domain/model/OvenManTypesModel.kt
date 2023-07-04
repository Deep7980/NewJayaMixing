package com.jaya.app.core.domain.model

data class OvenManTypesModel(
    val message: String,
    val ovenManList: List<OvenManType>,
    val status: Boolean
)
data class OvenManType(
    val id:String,
    val name:String
)
