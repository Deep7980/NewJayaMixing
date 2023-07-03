package com.jaya.app.core.common

import kotlinx.coroutines.flow.FlowCollector

suspend inline fun <reified R> FlowCollector<DataEntry>.handleFailedResponse(
    response: Resource<R>,
    message: String?,
    emitType: EmitType
) {
    when (message != null) {
        true -> {
            emit(DataEntry(emitType, message))
        }
        else -> {
            emit(DataEntry(emitType, response.message))
        }
    }
}