package com.ait.alpaca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        btnAlbum.setOnClickListener {
            startActivity(Intent(this@MenuActivity, ScrollingActivity::class.java))

        }

        btnChallenge.setOnClickListener {
            startActivity(Intent(this@MenuActivity, ChallengeActivity::class.java))

        }
    }
}
