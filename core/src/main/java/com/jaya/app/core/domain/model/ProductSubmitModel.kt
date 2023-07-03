package com.jaya.app.core.domain.model

data class ProductSubmitModel(
    val status:Boolean,
    val message:String,
    val prod_response: List<ProdDetails>
)

data class ProdDetails(
    val productId:String,
    val timeStamp:String,
    val plantStatus:String,
    val productName:String,
    val shiftStatus:String,
    val superVisorName:String,
    val packingSupervisor:String
)