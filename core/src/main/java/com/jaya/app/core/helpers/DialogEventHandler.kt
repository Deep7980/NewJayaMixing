package com.jaya.app.core.helpers

interface DialogEventHandler {

    fun onConfirm(data: Any?)
    fun onDismiss(data: Any?)
}