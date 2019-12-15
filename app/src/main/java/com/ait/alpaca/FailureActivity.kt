package com.ait.alpaca

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ait.alpaca.utils.ProgressUtils
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_failure.*

class FailureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_failure)

        Glide.with(this).asGif().load(R.drawable.clouds).into(ivCloudsFailure)

        if (ProgressUtils.isFinished()) {
            ivAlpsFailure.visibility = View.VISIBLE
        }


        btnBack.setOnClickListener {
            startActivity(Intent(this@FailureActivity, ChallengeActivity::class.java))
            finish()
        }

        btnQuitFailure.setOnClickListener {
            finish()
        }
    }
}
