package com.ait.alpaca

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_challenge.*
import kotlinx.android.synthetic.main.activity_instructions.*

class InstructionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instructions)

        btnQuitInstructions.setOnClickListener {
            finish()

        }
    }
}
