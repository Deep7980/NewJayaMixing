package com.jaya.app.jayamixing.utility

import androidx.annotation.StringRes

data class DialogData(
    @StringRes val title: Int,
    val message: String = "",
    @StringRes val positive: Int,
    @StringRes val negative: Int? = null,
    @StringRes val neutral: Int? = null,
    val data: Any? = null,
)
