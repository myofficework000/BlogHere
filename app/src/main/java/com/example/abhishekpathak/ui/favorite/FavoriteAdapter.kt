package com.example.abhishekpathak.ui.favorite

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.Post
import com.example.abhishekpathak.R
import com.example.abhishekpathak.ui.extensions.basicDiffUtil
import com.example.abhishekpathak.ui.extensions.inflate

class FavoriteAdapter(private val listener: (Post) -> Unit) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    var animes: MutableList<Post> by basicDiffUtil(
        mutableListOf(),
        areItemsTheSame = { old, new -> old.postId == new.postId }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.post_item, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = animes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val anime = animes[position]
        holder.bind(anime)
        holder.itemView.setOnClickListener {
            listener(anime)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var postTitle: TextView

        fun bind(post: Post) {
            postTitle = itemView.findViewById(R.id.titleTextView)
            postTitle.text = post.title
        }
    }
}