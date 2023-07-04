package com.jaya.app.core.domain.model

data class MixingManTypesModel(
    val message: String,
    val mixingManList: List<MixingMantype>,
    val status: Boolean
)

data class MixingMantype(
    val id:String,
    val name:String
)