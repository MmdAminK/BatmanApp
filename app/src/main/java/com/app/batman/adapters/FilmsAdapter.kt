package com.app.batman.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.batman.R
import com.app.batman.databinding.FilmCardViewBinding
import com.app.batman.models.Film

class FilmsAdapter (
    private val films: List<Film>
) : RecyclerView.Adapter<FilmsAdapter.MainPageViewHolder>() {
    private var itemClick: OnItemClickListener? = null
    private lateinit var filmCardViewBinding: FilmCardViewBinding
    interface OnItemClickListener {
        fun itemViewClick(position: Int)
    }

    fun setOnItem(onItem: OnItemClickListener) {
        itemClick = onItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainPageViewHolder {
        filmCardViewBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.film_card_view, parent, false)
        return MainPageViewHolder(filmCardViewBinding,itemClick!!)
    }

    override fun onBindViewHolder(holder: MainPageViewHolder, position: Int) {
        val film = films[position]
        holder.bind(film)
    }

    override fun getItemCount(): Int {
        return films.size
    }

    class MainPageViewHolder(
        private val filmCardViewBinding: FilmCardViewBinding,
        private val listener: OnItemClickListener
    )
        : RecyclerView.ViewHolder(filmCardViewBinding.root) {
        fun bind(film: Film) {
            filmCardViewBinding.film = film
            filmCardViewBinding.executePendingBindings()
        }
        init {
            filmCardViewBinding.root.setOnClickListener {
                val position: Int = layoutPosition
                if (position != RecyclerView.NO_POSITION) listener.itemViewClick(position)
            }
        }
    }
}


