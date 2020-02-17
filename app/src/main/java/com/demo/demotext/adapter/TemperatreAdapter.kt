package com.demo.demotext.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.demo.demotext.R

import com.demo.demotext.databinding.TempDataBinding
import com.demo.model.custom.Temprature

/**
 * Here is adapter class show all next 5 day temprature list
 */

class TemperatreAdapter (
    var context: Context
) :
    RecyclerView.Adapter<HotDataViewHolder>(){
    var items: ArrayList<Temprature> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotDataViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)

        var studentListBinding: TempDataBinding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.layout_row_temp,
                parent,
                false
            )


        return HotDataViewHolder(studentListBinding)


    }

    override fun onBindViewHolder(holder: HotDataViewHolder, position: Int) {
        var studentModel = items[position]
        holder.bind(studentModel)


    }




    override fun getItemCount(): Int {
        return items.size
    }

    fun addData(list: List<Temprature>) {
        AppUtil.showLogMessage("e", "datafound", "" + list.size)
        if (items.isNotEmpty())
            items.clear()

        items.addAll(list)

        notifyDataSetChanged()
    }
}

class HotDataViewHolder(var studentListBinding: TempDataBinding) :
    RecyclerView.ViewHolder(studentListBinding.root) {

    fun bind(dataModel: Temprature) {
        this.studentListBinding.data = dataModel
        this.studentListBinding.executePendingBindings()
    }
}
