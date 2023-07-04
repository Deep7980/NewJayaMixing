package com.jaya.app.core.domain.model

data class FloorManagerTypesModel(
    val message: String,
    val floor_type_list: List<FloorManagerType>,
    val status: Boolean
)

data class FloorManagerType(
    val floor_type: String,
    val floor_type_id: String
)
