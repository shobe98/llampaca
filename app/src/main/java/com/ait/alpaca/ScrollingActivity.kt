package com.ait.alpaca

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ait.alpaca.data.Alpaca
import kotlinx.android.synthetic.main.activity_challenge.*
import kotlinx.android.synthetic.main.activity_scrolling.*
import java.util.*
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.activity_scrolling.btnChallenge


class ScrollingActivity : AppCompatActivity() {

    var numAlpacas = 8


    lateinit var alpacaAdapter: AlpacaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)

        Glide.with(this).asGif().load(R.drawable.clouds).into(ivCloudsScroll)


        val numberOfColumns = 2
        recyclerAlpaca.setLayoutManager(GridLayoutManager(this, numberOfColumns))


        alpacaAdapter = AlpacaAdapter(this)
        recyclerAlpaca.adapter = alpacaAdapter

        for (i in 1..numAlpacas) {
            alpacaAdapter.addTodo(Alpaca(i))
        }


        //btnAdd.setOnClickListener {

            //alpacaAdapter.addTodo(
                //Alpaca(
                    //alpacaAdapter.itemCount + 1))

        //}











        btnChallenge.setOnClickListener {
            startActivity(Intent(this@ScrollingActivity, ChallengeActivity::class.java))
        }
    }

    override fun onRestart() {
        super.onRestart()
        //TODO{ANDREI}: get current number
    }




}
