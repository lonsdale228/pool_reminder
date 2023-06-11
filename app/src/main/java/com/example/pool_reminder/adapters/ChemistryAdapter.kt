package com.example.pool_reminder.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pool_reminder.R
import com.example.pool_reminder.models.ChemistryModel

class ChemistryAdapter(context:Context):RecyclerView.Adapter<ChemistryAdapter.ViewHolder>() {

    var dataList = emptyList<ChemistryModel>()

    internal fun setDataList(dataList:List<ChemistryModel>){
        this.dataList=dataList
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var image:ImageView
        var name:TextView
        var price:TextView
        init {
            image=itemView.findViewById(R.id.imageItem)
            name=itemView.findViewById(R.id.chemistryName)
            price=itemView.findViewById(R.id.chemistryPrice)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChemistryAdapter.ViewHolder {
        var view=LayoutInflater.from(parent.context).inflate(R.layout.chemistry_item,parent,false)
        return ViewHolder(view)
    }



    override fun onBindViewHolder(holder: ChemistryAdapter.ViewHolder, position: Int) {
        var data=dataList[position]

        holder.name.text=data.name
        holder.image.setImageResource(data.pic)
        holder.price.text=data.price.toString()
    }

    override fun getItemCount()=dataList.size
}