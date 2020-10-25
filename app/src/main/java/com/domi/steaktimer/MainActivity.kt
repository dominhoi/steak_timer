package com.domi.steaktimer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
	private lateinit var speakModule: SpeakModule
	private lateinit var viewModel: MainViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		initSpeakModule()
		initViewModel()
		initViews()
	}

	private fun initViews() {
		startButton.setOnClickListener {
			viewModel.startTimer()
		}

		cancelButton.setOnClickListener {
			viewModel.cancel()
		}
	}

	private fun initSpeakModule() {
		speakModule = SpeakModule(this)
	}

	private fun initViewModel() {
		viewModel = defaultViewModelProviderFactory.create(MainViewModel::class.java)
		viewModel.tickLiveData.observe(this, Observer {
			timerResult.text = it.toString()
		})

		viewModel.rotateLiveData.observe(this, Observer { first ->
			speakModule.speakRotate(first)
		})

		viewModel.finishLiveData.observe(this, Observer {
			timerResult.text = it
			speakModule.speakFinish()
		})

		viewModel.restingFinishLiveData.observe(this, Observer {
			timerResult.text = it
			speakModule.speakRestingFinish()
		})
	}
}