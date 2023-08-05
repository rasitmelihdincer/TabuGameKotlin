package com.example.tabugamekotlin

import android.content.Intent
import android.net.DnsResolver
import android.net.DnsResolver.Callback
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabugamekotlin.Adapter.RecyclerViewAdapter
import com.example.tabugamekotlin.Model.Model
import com.example.tabugamekotlin.Service.ApiInterface
import com.example.tabugamekotlin.Service.ApiService
import com.example.tabugamekotlin.databinding.FragmentGameBinding
import com.example.tabugamekotlin.databinding.FragmentStartBinding
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.FieldPosition


class GameFragment : Fragment() {

    private var recyclerViewAdapter : RecyclerViewAdapter? = null
    private var words : ArrayList<Model>? = null
    private var forbiddenWords : ArrayList<String> = arrayListOf()
    private lateinit var binding: FragmentGameBinding
    private var currentIndex = 0
    private var abc = 0


    private val BASE_URL = "https://raw.githubusercontent.com/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentGameBinding.inflate(inflater,container,false)
        val layoutManeger : RecyclerView.LayoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManeger
        return binding.root

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData2()
      //  arguments?.let {
      //  }
    }

    private fun loadData2(){
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiInterface::class.java)
        val call = service.getData()
        call.enqueue(object : retrofit2.Callback<List<Model>> {
            override fun onResponse(
                call: Call<List<Model>>,
                response: Response<List<Model>>,
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        words = ArrayList(it)
                        words?.let {
                            recyclerViewAdapter = RecyclerViewAdapter(it )
                            binding.recyclerView.adapter = recyclerViewAdapter
                            binding.mainWord.text = words!![0].word
                            binding.trueButton.setOnClickListener {
                                if (abc <= 1000){
                                    abc++
                                }
                                if (currentIndex <= 1000){
                                    currentIndex++
                                }
                                binding.mainWord.text = words!![currentIndex].word
                                recyclerViewAdapter!!.words[0].forbiddenWords?.clear()
                                words!![abc].forbiddenWords?.get(0)?.let { it1 ->
                                    recyclerViewAdapter!!.words[0].forbiddenWords?.add(
                                        it1)
                                }
                                words!![abc].forbiddenWords?.get(1)?.let { it1 ->
                                    recyclerViewAdapter!!.words[0].forbiddenWords?.add(
                                        it1)
                                }
                                words!![abc].forbiddenWords?.get(2)?.let { it1 ->
                                    recyclerViewAdapter!!.words[0].forbiddenWords?.add(
                                        it1)
                                }
                                words!![abc].forbiddenWords?.get(3)?.let { it1 ->
                                    recyclerViewAdapter!!.words[0].forbiddenWords?.add(
                                        it1)
                                }
                                words!![abc].forbiddenWords?.get(0)?.let { it1 ->
                                    recyclerViewAdapter!!.words[0].forbiddenWords?.add(
                                        it1)
                                }
                                recyclerViewAdapter!!.notifyDataSetChanged()
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<Model>>, t: Throwable) {
                println("sıkıntı var")
            }


        })

    }




}
