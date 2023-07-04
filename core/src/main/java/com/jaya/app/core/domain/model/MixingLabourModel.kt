package com.jaya.app.core.domain.model

data class MixingLabourModel(
    val status:Boolean,
    val message:String,
    val mixing_labour_list:List<MixingLabourList>
)
data class MixingLabourList(
    val id:String,
    val name:String
)
