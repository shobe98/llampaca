package com.ait.alpaca.data


data class Alpaca(
    var id: Int
) {
    companion object {
        val LAST_CHALLENGE = 17L // the number of alpacas
    }
}