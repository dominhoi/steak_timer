package com.domi.steaktimer

import android.os.CountDownTimer

class SteakTimer(
	steakMinute: Int,
	restingMinute: Int,
	val steakTimerCallback: TimerCallback
) {
	private val steakMillis = ((1000 * 60) * steakMinute).toLong()
	private val restingMillis = ((1000 * 60) * restingMinute).toLong()
	private val countDownInterval = 1000.toLong()

	private val steakTimer = object : CountDownTimer(steakMillis, countDownInterval) {
		override fun onFinish() {
			steakTimerCallback.onSteakFinish()

			restingTimer.start()
		}

		override fun onTick(millisUntilFinished: Long) {
			val millis = steakMillis - millisUntilFinished
			val secs = millis / 1000
			val minute = (secs % 3600 / 60).toInt()
			val seconds = (secs % 60).toInt()

			steakTimerCallback.onTick(minute, seconds)

			if (needToRotate(seconds, minute)) {
				steakTimerCallback.onRotate(isFirstRotated(seconds, minute))
			}
		}
	}

	private val restingTimer = object : CountDownTimer(restingMillis, countDownInterval) {
		override fun onFinish() {
			steakTimerCallback.onRestingFinish()
		}

		override fun onTick(millisUntilFinished: Long) {
			val millis = restingMillis - millisUntilFinished
			val secs = millis / 1000
			val minute = (secs % 3600 / 60).toInt()
			val seconds = (secs % 60).toInt()

			steakTimerCallback.onTick(minute, seconds)
		}
	}

	fun start() {
		steakTimer.start()
	}

	fun cancel() {
		try {
			steakTimer.cancel()
			restingTimer.cancel()
		} catch (ignore: Exception) {
		}
	}

	private fun needToRotate(seconds: Int, minute: Int) = seconds == 30 || seconds == 0 && minute > 0

	private fun isFirstRotated(seconds: Int, minute: Int) = seconds == 30 && minute == 0

	interface TimerCallback {
		fun onSteakFinish()
		fun onRestingFinish()
		fun onTick(minute: Int, seconds: Int)
		fun onRotate(isFirst: Boolean)
	}

}