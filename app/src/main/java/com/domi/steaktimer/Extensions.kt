package com.domi.steaktimer

fun Int.resToString(): String = SteakTimerApp.context?.resources?.getString(this) ?: ""