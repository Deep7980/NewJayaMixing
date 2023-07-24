package com.jaya.app.core.common

import kotlinx.coroutines.flow.FlowCollector

suspend inline fun <reified R> FlowCollector<DataEntry>.handleFailedResponse(
    response: Resource<R>,
    emitType: EmitType = EmitType.NETWORK_ERROR
) {
    emit(DataEntry(emitType, response.message))
//    when (message != null) {
//        true -> {
//            emit(DataEntry(emitType, message))
//        }
//        else -> {
//            emit(DataEntry(emitType, response.message))
//        }
//    }
}