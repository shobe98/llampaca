package com.ait.alpaca

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ait.alpaca.data.Alpaca
import com.ait.alpaca.utils.ProgressUtils
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_success.*

class SuccessActivity : AppCompatActivity() {


    // TODO(maxine): based on this number you can tell which word was used for the challenge and make the "hooray" message more customized
    var numSolved = ProgressUtils.getNumberOfAlpacas().toInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)

        Glide.with(this).asGif().load(R.drawable.clouds).into(ivCloudsSuccess)

        if (ProgressUtils.isFinished()) {
            ivAlpsSuccess.visibility = View.VISIBLE
        }

        btnQuitSuccess.setOnClickListener {
            finish()

        }



        btnPresent.setOnClickListener {

            val intent = Intent(this@SuccessActivity, AlpacaPopup::class.java)
            intent.putExtra("name", Alpaca.alpacaMap[numSolved - 1].second)
            intent.putExtra("desc", Alpaca.alpacaMap[numSolved - 1].third)
            intent.putExtra("image", Alpaca.alpacaMap[numSolved - 1].first)

            startActivity(intent)
            finish()

        }

    }


}
