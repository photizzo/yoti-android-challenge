package com.yoti.android.cryptocurrencychallenge.presentation.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.text.SimpleDateFormat
import java.util.*


inline fun <T> Flow<T>.handleError(crossinline action: (value: String) -> Unit): Flow<T> =
catch { e -> action(e.localizedMessage) }

fun String?.handleNullableString() = this ?: ""

fun Long.formatDisplayDate(): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy");
    return formatter.format(Date(this))
}

