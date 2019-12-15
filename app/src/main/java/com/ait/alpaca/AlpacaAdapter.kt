package com.ait.alpaca



import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ait.alpaca.data.Alpaca
import kotlinx.android.synthetic.main.alpaca_cell.view.*
import androidx.recyclerview.widget.GridLayoutManager





class AlpacaAdapter(private val context: Context) : RecyclerView.Adapter<AlpacaAdapter.ViewHolder>() {



    private var todoList = mutableListOf<Alpaca>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val todoRow = LayoutInflater.from(context).inflate(
            R.layout.alpaca_cell, parent, false
        )
        val lp = todoRow.layoutParams as GridLayoutManager.LayoutParams
        lp.height = 3 * parent.measuredHeight / 8

        todoRow.layoutParams = lp
        return ViewHolder(todoRow)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val alpaca = todoList[holder.adapterPosition]
        holder.tvName.text = context.getString(Alpaca.alpacaMap[alpaca.id - 1].second)
        holder.ivAlpaca.setImageResource(Alpaca.alpacaMap[alpaca.id - 1].first)



        holder.itemView.setOnClickListener {

            val alpaca = todoList[holder.adapterPosition]
            val intent = Intent(this.context, AlpacaPopup::class.java)
            intent.putExtra("name", Alpaca.alpacaMap[alpaca.id - 1].second)
            intent.putExtra("desc", Alpaca.alpacaMap[alpaca.id - 1].third)
            intent.putExtra("image", Alpaca.alpacaMap[alpaca.id - 1].first)

            context.startActivity(intent)

        }




    }

    fun addTodo(alpaca: Alpaca) {
        todoList.add(alpaca)
        notifyItemInserted(todoList.lastIndex)
    }

    fun size(): Int = todoList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName = itemView.tvName
        val ivAlpaca = itemView.ivAlpaca
    }

}