package com.jaya.app.core.common

enum class MetarFields(val value: String) {
    BaseUrl("base_url");

    operator fun invoke() = value
}