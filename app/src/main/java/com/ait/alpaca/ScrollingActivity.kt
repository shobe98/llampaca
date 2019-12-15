package com.ait.alpaca

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.ait.alpaca.data.Alpaca
import com.ait.alpaca.utils.ProgressUtils
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_scrolling.*


class ScrollingActivity : AppCompatActivity() {

    var numAlpacas = 8


    lateinit var alpacaAdapter: AlpacaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)

        Glide.with(this).asGif().load(R.drawable.clouds).into(ivCloudsScroll)

        if (ProgressUtils.isFinished()) {
            ivAlpsAlbum.visibility = View.VISIBLE
        }

        btnQuitAlbum.setOnClickListener {
            finish()
        }

        val numberOfColumns = 2
        recyclerAlpaca.setLayoutManager(GridLayoutManager(this, numberOfColumns))


        alpacaAdapter = AlpacaAdapter(this)
        recyclerAlpaca.adapter = alpacaAdapter




    }

    override fun onStart() {
        super.onStart()

        numAlpacas = ProgressUtils.getNumberOfAlpacas().toInt()

        for (i in (alpacaAdapter.size() + 1)..numAlpacas) {
            alpacaAdapter.addTodo(Alpaca(i))
        }
    }



    override fun onRestart() {
        super.onRestart()
        //TODO{ANDREI}: get current number
    }




}
