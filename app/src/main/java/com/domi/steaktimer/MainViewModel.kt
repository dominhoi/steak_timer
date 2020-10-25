package com.domi.steaktimer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

private const val STEAK_MINUTE = 4
private const val RESTING_MINUTE = 2

class MainViewModel : ViewModel() {
	val tickLiveData = MutableLiveData<String>()
	val rotateLiveData = MutableLiveData<Boolean>()
	val finishLiveData = MutableLiveData<String>()
	val restingFinishLiveData = MutableLiveData<String>()
	private val steakTimer = SteakTimer(STEAK_MINUTE, RESTING_MINUTE, object : SteakTimer.TimerCallback {
		override fun onSteakFinish() {
			finishLiveData.value = "0${STEAK_MINUTE}:00"
		}

		override fun onRestingFinish() {
			restingFinishLiveData.value = "0${RESTING_MINUTE}:00"
		}

		override fun onTick(minute: Int, seconds: Int) {
			tickLiveData.value = String.format("%02d:%02d", minute, seconds)
		}

		override fun onRotate(isFirst: Boolean) {
			rotateLiveData.value = isFirst
		}
	})

	fun startTimer() {
		steakTimer.start()
	}

	fun cancel() {
		steakTimer.cancel()
		tickLiveData.value = "00:00"
	}
}