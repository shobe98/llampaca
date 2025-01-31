package com.ait.alpaca

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Glide.with(this).asGif().load(R.drawable.splash).into(ivSplash)


        if(intent.getBooleanExtra("BYPASS_LOGIN", false)) {
            Handler().postDelayed({
                startActivity(Intent(this@SplashActivity, MenuActivity::class.java))
                finish()
            }, 4000)
        }
        else {
            Handler().postDelayed({
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                finish()
            }, 4000)
        }
    }
}

