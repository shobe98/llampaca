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





class AlpacaAdapter(val context: Context) : RecyclerView.Adapter<AlpacaAdapter.ViewHolder>() {



    var todoList = mutableListOf<Alpaca>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val todoRow = LayoutInflater.from(context).inflate(
            R.layout.alpaca_cell, parent, false
        )
        val lp = todoRow.getLayoutParams() as GridLayoutManager.LayoutParams
        lp.height = 3 * parent.getMeasuredHeight() / 8;

        todoRow.setLayoutParams(lp)
        return ViewHolder(todoRow)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var alpaca = todoList.get(holder.adapterPosition)
        holder.tvName.text = context.getString(Alpaca.alpacaMap.getValue(alpaca.id).second)
        holder.ivAlpaca.setImageResource(Alpaca.alpacaMap.getValue(alpaca.id).first)



        holder.itemView.setOnClickListener {

            var alpaca = todoList.get(holder.adapterPosition)
            val intent = Intent(this.context, AlpacaPopup::class.java)
            intent.putExtra("name", Alpaca.alpacaMap.getValue(alpaca.id).second)
            intent.putExtra("desc", Alpaca.alpacaMap.getValue(alpaca.id).third)
            intent.putExtra("image", Alpaca.alpacaMap.getValue(alpaca.id).first)

            context.startActivity(intent)

        }




    }

    fun deleteTodo(index: Int){
        todoList.removeAt(index)
        notifyItemRemoved(index)
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