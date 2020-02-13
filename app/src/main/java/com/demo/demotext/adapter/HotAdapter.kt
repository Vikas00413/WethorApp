package com.demo.demotext.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.demo.demotext.R
import com.demo.demotext.databinding.HotDataBinding
import com.demo.model.response.Children

class HotAdapter (
    var context: Context
) :
    RecyclerView.Adapter<HotDataViewHolder>(), Filterable {
    var items: ArrayList<Children> = ArrayList()
    var itemsCopy: ArrayList<Children> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotDataViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)

        var studentListBinding: HotDataBinding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.layout_row_hot,
                parent,
                false
            )


        return HotDataViewHolder(studentListBinding)


    }

    override fun onBindViewHolder(holder: HotDataViewHolder, position: Int) {
        var studentModel = items[position]
        holder.bind(studentModel)


    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(
                charSequence: CharSequence?,
                filterResults: Filter.FilterResults
            ) {
                items.clear()
                items.addAll(filterResults.values as ArrayList<Children>)
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): Filter.FilterResults {
                val queryString = charSequence?.toString()?.toLowerCase()

                val filterResults = Filter.FilterResults()
                if (queryString == null || queryString.isEmpty()) {
                    filterResults.values = itemsCopy
                } else
                    filterResults.values = itemsCopy.filter {
                        it.kind!!.toLowerCase().contains(queryString) ||
                                it.data!!.subreddit!!.toLowerCase().contains(queryString)||it.data!!.selftext!!.toLowerCase().contains(queryString)
                    }
                return filterResults
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addData(list: List<Children>) {
        AppUtil.showLogMessage("e", "datafound", "" + list.size)
        if (items.isNotEmpty())
            items.clear()
        if (itemsCopy.isNotEmpty())
            itemsCopy.clear()
        items.addAll(list)
        itemsCopy.addAll(list)
        notifyDataSetChanged()
    }
}

class HotDataViewHolder(var studentListBinding: HotDataBinding) :
    RecyclerView.ViewHolder(studentListBinding.root) {

    fun bind(dataModel: Children) {
        this.studentListBinding.data = dataModel
        this.studentListBinding.executePendingBindings()
    }
}
