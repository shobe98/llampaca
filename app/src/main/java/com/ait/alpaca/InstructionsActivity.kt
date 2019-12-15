package com.ait.alpaca

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ait.alpaca.utils.ProgressUtils
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_instructions.*

class InstructionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instructions)

        Glide.with(this).asGif().load(R.drawable.clouds).into(ivCloudsInstructions)

        if (ProgressUtils.isFinished()) {
            ivAlpsInstructions.visibility = View.VISIBLE
        }


        btnQuitInstructions.setOnClickListener {
            finish()

        }
    }
}
