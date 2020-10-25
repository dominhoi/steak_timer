package com.domi.steaktimer

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.*

class SpeakModule(context: Context) {
	private lateinit var tts: TextToSpeech

	init {
		tts = TextToSpeech(context, TextToSpeech.OnInitListener {
			if (it != TextToSpeech.ERROR) {
				tts.language = Locale.KOREAN
			}
		})
		tts.setSpeechRate(1f)
	}

	fun speakRotate(isFirst: Boolean) {
		tts.speak(rotateMessage(isFirst), TextToSpeech.QUEUE_FLUSH, null, "steakToRotate")
	}

	fun speakFinish() = tts.speak(R.string.resting.resToString(), TextToSpeech.QUEUE_FLUSH, null, "resting")

	fun speakRestingFinish() = tts.speak(R.string.resting_finish.resToString(), TextToSpeech.QUEUE_FLUSH, null, "restingFinish")

	private fun rotateMessage(isFirst: Boolean) = if (isFirst) {
		R.string.medium_fire_after_rotate.resToString()
	} else {
		R.string.rotate_message.resToString()
	}

}