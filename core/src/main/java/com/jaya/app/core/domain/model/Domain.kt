package com.jaya.app.core.domain.model

data class Domain(
    val base: String,
    val changeImmediate: String
){
    companion object {
        const val CHANGE = "YES"
        const val DONT_CHANGE = "NO"
    }
}
