package com.joellui.ryuu.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.joellui.ryuu.R
import com.joellui.ryuu.model.AnimeDetails

class SearchResultAdapter(
    var searchResult: List<AnimeDetails>,
    val listener: OnClickListener
): RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder>() {
    inner class SearchResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener{
            init {
                itemView.setOnClickListener (
                    this
                )
            }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position!= RecyclerView.NO_POSITION){
                listener.searchResultClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_anime,parent,false)
        return SearchResultViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.itemView.apply {
            val title = findViewById<TextView>(R.id.tv_Title)
            val cover = findViewById<ImageView>(R.id.iv_coverImage)

            title.text = searchResult[position].titles.en
            cover.load(searchResult[position].cover_image)
        }
    }

    override fun getItemCount(): Int {
        return searchResult.size
    }

    interface OnClickListener{
        fun searchResultClick(position: Int)
    }
}