package com.ait.alpaca

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_challenge.*
import kotlinx.android.synthetic.main.activity_scrolling.*

class ScrollingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        //setSupportActionBar(toolbar)



        val chars = mapOf(
            1 to Triple(R.drawable.za, getString(R.string.a_title), getString(R.string.a_desc)),
            2 to Triple(R.drawable.zb, getString(R.string.b_title), getString(R.string.b_desc)),
            3 to Triple(R.drawable.zc, getString(R.string.c_title), getString(R.string.c_desc)),
            4 to Triple(R.drawable.zd, getString(R.string.d_title), getString(R.string.d_desc)),
            5 to Triple(R.drawable.ze, getString(R.string.e_title), getString(R.string.e_desc)),
            6 to Triple(R.drawable.zf, getString(R.string.f_title), getString(R.string.f_desc)),
            7 to Triple(R.drawable.zg, getString(R.string.g_title), getString(R.string.g_desc)),
            8 to Triple(R.drawable.zh, getString(R.string.h_title), getString(R.string.h_desc)),
            9 to Triple(R.drawable.zi, getString(R.string.i_title), getString(R.string.i_desc)),
            10 to Triple(R.drawable.zk, getString(R.string.j_title), getString(R.string.j_desc)),
            11 to Triple(R.drawable.zl, getString(R.string.k_title), getString(R.string.k_desc)),
            12 to Triple(R.drawable.zm, getString(R.string.l_title), getString(R.string.l_desc)),
            13 to Triple(R.drawable.zn, getString(R.string.m_title), getString(R.string.m_desc)),
            14 to Triple(R.drawable.zo, getString(R.string.n_title), getString(R.string.n_desc)),
            15 to Triple(R.drawable.zp, getString(R.string.o_title), getString(R.string.o_desc)),
            16 to Triple(R.drawable.zq, getString(R.string.p_title), getString(R.string.p_desc)))



            btnChallenge.setOnClickListener {
            startActivity(Intent(this@ScrollingActivity, ChallengeActivity::class.java))
        }
    }


}
