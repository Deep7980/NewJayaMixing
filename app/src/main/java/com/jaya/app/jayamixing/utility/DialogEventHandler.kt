package com.jaya.app.jayamixing.utility

interface DialogEventHandler {

    fun onConfirm(data: Any?)
    fun onDismiss(data: Any?)


    enum class DialogEvent{
        UploadInvoice
    }
}