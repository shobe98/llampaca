package com.ait.alpaca

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_challenge.*
import kotlinx.android.synthetic.main.activity_challenge.ivClouds
import kotlinx.android.synthetic.main.activity_instructions.*
import kotlinx.android.synthetic.main.activity_menu.*

class InstructionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instructions)

        Glide.with(this).asGif().load(R.drawable.clouds).into(ivCloudsInstructions)




        btnQuitInstructions.setOnClickListener {
            finish()

        }
    }
}
