package com.domi.steaktimer

import android.app.Application
import android.content.Context

class SteakTimerApp : Application() {

	override fun onCreate() {
		super.onCreate()

		context = applicationContext
	}


	companion object {
		var context: Context? = null
	}
}
