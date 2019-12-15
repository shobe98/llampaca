package com.ait.alpaca.data

import com.ait.alpaca.R


data class Alpaca(
    var id: Int
) {
    companion object {
        val LAST_CHALLENGE = 17L // the number of alpacas
        val alpacaMap = mapOf(
            1 to Triple(R.drawable.za, R.string.a_title, R.string.a_desc),
            2 to Triple(R.drawable.zb, R.string.b_title, R.string.b_desc),
            3 to Triple(R.drawable.zc, R.string.c_title, R.string.c_desc),
            4 to Triple(R.drawable.zd, R.string.d_title, R.string.d_desc),
            5 to Triple(R.drawable.ze, R.string.e_title, R.string.e_desc),
            6 to Triple(R.drawable.zf, R.string.f_title, R.string.f_desc),
            7 to Triple(R.drawable.zg, R.string.g_title, R.string.g_desc),
            8 to Triple(R.drawable.zh, R.string.h_title, R.string.h_desc),
            9 to Triple(R.drawable.zi, R.string.i_title, R.string.i_desc),
            10 to Triple(R.drawable.zj, R.string.j_title, R.string.j_desc),
            11 to Triple(R.drawable.zk, R.string.k_title, R.string.k_desc),
            12 to Triple(R.drawable.zl, R.string.l_title, R.string.l_desc),
            13 to Triple(R.drawable.zm, R.string.m_title, R.string.m_desc),
            14 to Triple(R.drawable.zn, R.string.n_title, R.string.n_desc),
            15 to Triple(R.drawable.zo, R.string.o_title, R.string.o_desc),
            16 to Triple(R.drawable.zp, R.string.p_title, R.string.p_desc),
            17 to Triple(R.drawable.zq, R.string.q_title, R.string.q_desc))

    }
}


