package com.ait.alpaca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ait.alpaca.utils.ProgressUtils
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity(), ProgressUtils.AlpacaHandler {
    override fun handleAlpacasFirstInnit() {
        btnChallenge.isEnabled = true
        btnAlbum.isEnabled = true


        if (ProgressUtils.isFinished()) {
            ivAlpsMenu.visibility = View.VISIBLE
        }

        // This function gets called as soon as connection was established with database.

        // TODO(maxine, andrei): Come up with an indicator for the user to wait - otherwise they will keep pressing the button and the app will seem broken
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        ProgressUtils.initializeSingleton(this) // technically if you are too fast this won't work

        Glide.with(this).asGif().load(R.drawable.clouds).into(ivClouds)

        btnAlbum.setOnClickListener {
            startActivity(Intent(this@MenuActivity, ScrollingActivity::class.java))

        }

        btnInstructions.setOnClickListener {
            startActivity(Intent(this@MenuActivity, InstructionsActivity::class.java))
        }

        btnChallenge.setOnClickListener {
            if (!ProgressUtils.isFinished()) {
                startActivity(Intent(this@MenuActivity, ChallengeActivity::class.java))
            } else {
                // TODO(maxine): what do when game over
                Toast.makeText(
                    this@MenuActivity,
                    "Sorry bruh, you finished the game!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }


        if(ProgressUtils.isInitialized()) {
            btnChallenge.isEnabled = true
            btnAlbum.isEnabled = true
        }

    }

    override fun onResume() {
        if (ProgressUtils.isFinished()) {
            ivAlpsMenu.visibility = View.VISIBLE

            btnChallenge.text = "Restart"

            btnChallenge.setOnClickListener {
                ProgressUtils.resetProgression()

                var intent = Intent(this@MenuActivity, SplashActivity::class.java)

                intent.putExtra("BYPASS_LOGIN", true)

                startActivity(intent)
                finish()
            }
        }
        super.onResume()
    }
}
