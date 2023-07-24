package com.jaya.app.jayamixing.utility

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.window.DialogProperties
import com.jaya.app.core.common.DialogData

class DialogHandler(private val data: DialogData = null) {

    val currentData get() = data
    private val _enable = mutableStateOf(false)
    fun currentState() = _enable.value

    val properties = DialogProperties()

    init {
        if (data != null) {
            setState(State.ENABLE)
        }
    }

    private var _consumeEvents: DialogEventHandler? = null
    val events: DialogEventHandler? get() = _consumeEvents

    fun consumeEvents(handler: DialogEventHandler) {
        if (_consumeEvents == null) _consumeEvents = handler
    }

    companion object {

        enum class State {
            ENABLE,
            DISABLE
        }
    }

    fun setState(state: State) {
        when (state) {
            State.ENABLE -> {
                _enable.value = true
            }
            State.DISABLE -> {
                _enable.value = false
            }
        }
    }

}