package com.ait.alpaca

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ait.alpaca.utils.ProgressUtils
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_alpaca_popup.*

class AlpacaPopup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alpaca_popup)




        Glide.with(this).asGif().load(R.drawable.clouds).into(ivCloudsPopup)
        Glide.with(this).asGif().load(R.drawable.popup).into(ivPopupGrass)

        if (ProgressUtils.isFinished()) {
            ivAlpsPopup.visibility = View.VISIBLE

        }

        btnQuitPopup.setOnClickListener {
            finish()
        }

        tvPopupName.text = getString(intent.getIntExtra("name", -1))
        tvPopupDesc.text = getString(intent.getIntExtra("desc", -1))
        ivPopupPic.setImageResource(intent.getIntExtra("image", -1))



    }
}
