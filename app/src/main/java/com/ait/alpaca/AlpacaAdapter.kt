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





class AlpacaAdapter : RecyclerView.Adapter<AlpacaAdapter.ViewHolder> {



    var todoList = mutableListOf<Alpaca>()

    val context: Context
    var alpacaMap: Map<Int, Triple<Int, String, String>>
    constructor(context: Context){
        this.context = context

        this.alpacaMap = mapOf(
            1 to Triple(R.drawable.za, context.getString(R.string.a_title), context.getString(R.string.a_desc)),
            2 to Triple(R.drawable.zb, context.getString(R.string.b_title), context.getString(R.string.b_desc)),
            3 to Triple(R.drawable.zc, context.getString(R.string.c_title), context.getString(R.string.c_desc)),
            4 to Triple(R.drawable.zd, context.getString(R.string.d_title), context.getString(R.string.d_desc)),
            5 to Triple(R.drawable.ze, context.getString(R.string.e_title), context.getString(R.string.e_desc)),
            6 to Triple(R.drawable.zf, context.getString(R.string.f_title), context.getString(R.string.f_desc)),
            7 to Triple(R.drawable.zg, context.getString(R.string.g_title), context.getString(R.string.g_desc)),
            8 to Triple(R.drawable.zh, context.getString(R.string.h_title), context.getString(R.string.h_desc)),
            9 to Triple(R.drawable.zi, context.getString(R.string.i_title), context.getString(R.string.i_desc)),
            10 to Triple(R.drawable.zj, context.getString(R.string.j_title), context.getString(R.string.j_desc)),
            11 to Triple(R.drawable.zk, context.getString(R.string.k_title), context.getString(R.string.k_desc)),
            12 to Triple(R.drawable.zl, context.getString(R.string.l_title), context.getString(R.string.l_desc)),
            13 to Triple(R.drawable.zm, context.getString(R.string.m_title), context.getString(R.string.m_desc)),
            14 to Triple(R.drawable.zn, context.getString(R.string.n_title), context.getString(R.string.n_desc)),
            15 to Triple(R.drawable.zo, context.getString(R.string.o_title), context.getString(R.string.o_desc)),
            16 to Triple(R.drawable.zp, context.getString(R.string.p_title), context.getString(R.string.p_desc)),
            17 to Triple(R.drawable.zq, context.getString(R.string.q_title), context.getString(R.string.q_desc)))




        //for (i in 0..20){
        //    todoList.add(Todo("2019", "Todo $i", false))
        //}
    }



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
        holder.tvName.text = alpacaMap.getValue(alpaca.id).second
        holder.ivAlpaca.setImageResource(alpacaMap.getValue(alpaca.id).first)



        holder.itemView.setOnClickListener {

            var alpaca = todoList.get(holder.adapterPosition)
            val intent = Intent(this.context, AlpacaPopup::class.java)
            intent.putExtra("name", alpacaMap.getValue(alpaca.id).second)
            intent.putExtra("desc", alpacaMap.getValue(alpaca.id).third)
            intent.putExtra("image", alpacaMap.getValue(alpaca.id).first)

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

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName = itemView.tvName
        val ivAlpaca = itemView.ivAlpaca
    }

}