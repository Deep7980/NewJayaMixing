package com.jaya.app.core.domain.model

data class CuttingManTypesModel(
    val status:Boolean,
    val message:String,
    val cuttingManList: List<CuttingManTypes>,
)
data class CuttingManTypes(
    val id:String,
    val name: String
)
