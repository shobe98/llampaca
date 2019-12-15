package com.ait.alpaca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_failure.*
import kotlinx.android.synthetic.main.activity_menu.*

class FailureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_failure)

        Glide.with(this).asGif().load(R.drawable.clouds).into(ivClouds)


        btnBack.setOnClickListener {
            startActivity(Intent(this@FailureActivity, ChallengeActivity::class.java))
            finish()
        }
    }
}
