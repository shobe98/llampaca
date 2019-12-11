package com.ait.alpaca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ait.alpaca.utils.ProgressUtils
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.activity_splash.*

class MenuActivity : AppCompatActivity(), ProgressUtils.AlpacaHandler {
    override fun handleAlpacasFirstInnit() {
        btnChallenge.isEnabled = true
        btnAlbum.isEnabled = true

        // This function gets called as soon as connection was established with database.

        // TODO(maxine, andrei): Come up with an indicator for the user to wait - otherwise they will keep pressing the button and the app will seem broken
    }

    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        ProgressUtils.initializeSingleton(this) // technically if you are too fast this won't work

        Glide.with(this).asGif().load(R.drawable.clouds).into(ivClouds)

        btnAlbum.setOnClickListener {
            startActivity(Intent(this@MenuActivity, ScrollingActivity::class.java))

        }

        btnChallenge.setOnClickListener {
            startActivity(Intent(this@MenuActivity, ChallengeActivity::class.java))

        }


    }
}
