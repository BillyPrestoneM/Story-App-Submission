package com.example.storyappsubmission.presentation.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.storyappsubmission.R
import com.example.storyappsubmission.data.model.ListStoryItem
import com.bumptech.glide.Glide
import com.example.storyappsubmission.presentation.ui.detailstory.DetailStoryActivity

class StoryAdapter : ListAdapter<ListStoryItem, StoryAdapter.StoryViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_main_row, parent, false)
        return StoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val story = getItem(position)
        holder.bind(story)
    }

    class StoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.tvUpcoming)
        private val imageView: ImageView = itemView.findViewById(R.id.ivPictureStory)
        private val cardView: View = itemView.findViewById(R.id.card_view)
        fun bind(story: ListStoryItem) {
            nameTextView.text = story.name
            Glide.with(itemView.context)
                .load(story.photoUrl)
                .into(imageView)

            cardView.setOnClickListener {
                Log.d("StoryAdapter", "Clicked on story with ID: ${story.id}")
                val intent = Intent(itemView.context, DetailStoryActivity::class.java)
                intent.putExtra("STORY_ID", story.id)
                itemView.context.startActivity(intent)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
