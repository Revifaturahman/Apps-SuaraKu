package com.revifaturahman.suaraku.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.revifaturahman.suaraku.databinding.ActivitySplashScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private val handler = Handler(Looper.getMainLooper())
    private var progress = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        simulateLoading()
    }

    private fun simulateLoading() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                progress += 5
                binding.loadingBar.progress = progress

                if (progress < 100) {
                    handler.postDelayed(this, 300)
                } else {
                    handler.removeCallbacks(this)

                    val intent = Intent(this@SplashScreen, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish() // penting
                }
            }
        }, 50)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }

}