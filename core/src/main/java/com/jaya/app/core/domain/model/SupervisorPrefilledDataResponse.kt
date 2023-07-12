package com.jaya.app.core.domain.model

data class SupervisorPrefilledDataResponse(
    val status:Boolean,
    val message:String,
    val supervisorPrefilledDataResponse: SupervisorPrefilledDataModel
)
data class SupervisorPrefilledDataModel(
    val floorManagerList:List<FloorManagerType>,
    val productsList:List<ProductType>,
    val superVisorName:String,
    val mixingManList:List<MixingMantype>,
    val cuttingManList:List<CuttingManTypes>,
    val ovenManList:List<OvenManType>,
    val packingSupervisorList:List<PackingSupervisorTypes>
)
