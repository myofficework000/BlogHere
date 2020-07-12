package com.example.abhishekpathak.ui.main

import android.content.Context
import android.util.SparseBooleanArray
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.Post
import com.example.abhishekpathak.R
import com.example.abhishekpathak.ui.extensions.basicDiffUtil
import com.example.abhishekpathak.ui.extensions.inflate
import kotlinx.android.synthetic.main.post_item.view.*


class PostAdapter(val context: Context, private val listener: (Post) -> Unit) :
    RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    var posts: MutableList<Post> by basicDiffUtil(
        mutableListOf(),
        areItemsTheSame = { old, new -> old.postId == new.postId }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.post_item, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = posts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts[position]
        holder.bind(post)
        holder.itemView.setOnClickListener {
            listener(post)
        }

        if (getDotStatusVisibility(position) && position <= 19) {
            holder.itemView.dotUnreadStatus.visibility = GONE
        } else if (!getDotStatusVisibility(position) && position <= 19) {
            holder.itemView.dotUnreadStatus.visibility = VISIBLE
        } else if (position > 19) {
            holder.itemView.dotUnreadStatus.visibility = GONE
        }

        if (getFavoriteVisibility(position)) {
            holder.itemView.is_favorite_post_iv.visibility = VISIBLE
        } else {
            holder.itemView.is_favorite_post_iv.visibility = GONE
        }
    }

    fun getDotStatusVisibility(position: Int): Boolean {
        return posts[position].wasRead
    }

    fun getFavoriteVisibility(position: Int): Boolean {
        return posts[position].favorite
    }

    fun clearPostsList() {
        val size = posts.size
        posts.clear()
        notifyItemRangeRemoved(0, size)
    }


    fun addIntoPostsList() {
        val size = posts.size
        notifyItemInserted(size+1)
    }

    fun deletePost(position: Int) {
        posts.removeAt(position)
        notifyItemRemoved(position)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        lateinit var postTitle: TextView
        lateinit var dotStatus: ImageView
        private val selectedItems = SparseBooleanArray()

        init {
            super.itemView
        }

        fun bind(post: Post) {
            dotStatus = itemView.findViewById(R.id.dotUnreadStatus)
            postTitle = itemView.findViewById(R.id.titleTextView)
            postTitle.text = post.title
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            if (selectedItems.get(adapterPosition, false)) {
                selectedItems.delete(adapterPosition)
                view?.isSelected = false
            } else {
                selectedItems.put(adapterPosition, true)
                view?.isSelected = true
            }
        }
    }
}