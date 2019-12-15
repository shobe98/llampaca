package com.ait.alpaca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ait.alpaca.data.Alpaca
import com.ait.alpaca.utils.ProgressUtils
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.activity_success.*

class SuccessActivity : AppCompatActivity() {


    // TODO(maxine): based on this number you can tell which word was used for the challenge and make the "hooray" message more customized
    var numSolved = ProgressUtils.getNumberOfAlpacas().toInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)

        Glide.with(this).asGif().load(R.drawable.clouds).into(ivCloudsSuccess)



        btnPresent.setOnClickListener {

            val intent = Intent(this@SuccessActivity, AlpacaPopup::class.java)
            intent.putExtra("name", Alpaca.alpacaMap.getValue(numSolved).second)
            intent.putExtra("desc", Alpaca.alpacaMap.getValue(numSolved).third)
            intent.putExtra("image", Alpaca.alpacaMap.getValue(numSolved).first)

            //intent.putExtra("name", alpacaMap.getValue(numSolved).second)
            //intent.putExtra("desc", alpacaMap.getValue(numSolved).third)
            //intent.putExtra("image", alpacaMap.getValue(numSolved).first)
            startActivity(intent)
            finish()

        }

    }


}
