package com.ait.alpaca



import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.alpaca_cell.view.*
import java.util.*

class AlpacaAdapter : RecyclerView.Adapter<AlpacaAdapter.ViewHolder> {

    var alpacaList = mutableListOf<Int>()

    val context: Context

    constructor(context: Context, alpacas: List<Int>) {
        this.context = context

        alpacaList.addAll(alpacas)

        //for (i in 0..20){
        //    alpacaList.add(Alpaca("2019", "Alpaca $i", false))
        //}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val alpacaRow = LayoutInflater.from(context).inflate(
            R.layout.alpaca_cell, parent, false
        )
        return ViewHolder(alpacaRow)
    }

    override fun getItemCount(): Int {
        return alpacaList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var alpaca = alpacaList.get(holder.adapterPosition)


    }


    fun addAlpaca(alpaca: Int) {
        alpacaList.add(alpaca)
        notifyItemInserted(alpacaList.lastIndex)
    }


    fun onDismissed(position: Int) {

    }

    fun onItemMoved(fromPosition: Int, toPosition: Int) {
        Collections.swap(alpacaList, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvAlpacaName = itemView.tvAlpacaName

    }
}

