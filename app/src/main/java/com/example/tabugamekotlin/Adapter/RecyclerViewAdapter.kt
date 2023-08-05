package com.example.tabugamekotlin.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tabugamekotlin.Model.Model
import com.example.tabugamekotlin.databinding.RecyclerRowBinding

class RecyclerViewAdapter(var words : ArrayList<Model>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {


    class ViewHolder(val binding : RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind ( wordsModel : ArrayList<Model> , forbidden : ArrayList<Model>){
         //   binding.forbbidenWord.text = wordsModel[0].forbiddenWords.toString()
            binding.forbbidenWord.text = forbidden[0].forbiddenWords.toString()



        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return words[0].forbiddenWords!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      //  holder.bind(words.get(position))
        holder.binding.forbbidenWord.text = words[0].forbiddenWords!![position]




    }


}