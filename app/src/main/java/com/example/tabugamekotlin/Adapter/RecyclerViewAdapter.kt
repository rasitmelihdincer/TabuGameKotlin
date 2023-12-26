package com.example.tabugamekotlin.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tabugamekotlin.Model.Model
import com.example.tabugamekotlin.databinding.RecyclerRowBinding

class RecyclerViewAdapter(var words : Model) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(val binding : RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      //  holder.bind(words.get(position))
        holder.binding.forbbidenWord.text = words.data[0].forbiddenWords[position]

    }
    fun updateForbiddenWords(newWords: ArrayList<String>) {
        words.data[0].forbiddenWords = newWords
        notifyDataSetChanged()
    }
}