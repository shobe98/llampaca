package com.ait.alpaca

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_alpaca_popup.*
import kotlinx.android.synthetic.main.activity_menu.*

class AlpacaPopup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alpaca_popup)



        Glide.with(this).asGif().load(R.drawable.clouds).into(ivCloudsPopup)
        Glide.with(this).asGif().load(R.drawable.popup).into(ivPopupGrass)


        tvPopupName.text = intent.getStringExtra("name")
        tvPopupDesc.text = intent.getStringExtra("desc")
        ivPopupPic.setImageResource(intent.getIntExtra("image", -1))



    }
}
