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


class GridRandomAdapter(
    var animeList: List<AnimeDetails>,
    val listener: OnClickListener
) : RecyclerView.Adapter<GridRandomAdapter.GridViewHolder>() {
    inner class GridViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),
        View.OnClickListener{
            init {
                itemView.setOnClickListener (
                    this
                    )
            }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION){
                listener.onClick(position)
            }
        }
        }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_anime,parent,false)
        return GridViewHolder(view)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        holder.itemView.apply {

            val title = this.findViewById<TextView>(R.id.tv_Title)
            val cover = this.findViewById<ImageView>(R.id.iv_coverImage)

            if (animeList[position].titles.en != null)
                title.text =animeList[position].titles.en
            else
                title.text =animeList[position].titles.jp
            cover.load(animeList[position].cover_image){
                crossfade(true)
                crossfade(1000)
            }

        }
    }

    override fun getItemCount(): Int {
        return animeList.size
    }

    interface OnClickListener {
        fun onClick(position: Int)
    }
}