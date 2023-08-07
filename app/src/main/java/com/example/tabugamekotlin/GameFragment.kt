package com.example.tabugamekotlin

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabugamekotlin.Adapter.RecyclerViewAdapter
import com.example.tabugamekotlin.Model.Model
import com.example.tabugamekotlin.Service.ApiInterface
import com.example.tabugamekotlin.databinding.FragmentGameBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class GameFragment : Fragment() {

    private var recyclerViewAdapter : RecyclerViewAdapter? = null
    private var words : ArrayList<Model>? = null
    private var forbiddenWords : ArrayList<String> = arrayListOf()
    private lateinit var binding: FragmentGameBinding
    private lateinit var tt : CountDownTimer
    private val handler = Handler()
    private var isTeam1Turn = true
    private var textChanged = false
    private var scoreChanged = false
    private var currentIndex = 0
    private var abc = 0
    private var firstTeamScore = 0
    private var secondTeamScore = 0


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
        binding.teamName.text = arguments?.getString("teamName1")
        tt = object : CountDownTimer(5000,1000){
            override fun onTick(p0: Long) {
                binding.time.text = (p0/1000).toString()
            }
            override fun onFinish() {
                val dialogView = LayoutInflater.from(context).inflate(R.layout.dialogcard,null)
                val alert = AlertDialog.Builder(context)
                alert.setView(dialogView)
                var firstTeam = dialogView.findViewById<TextView>(R.id.dialogFirstTeam)
                var secondTeam = dialogView.findViewById<TextView>(R.id.dialogSecondTeam)
                var dialogScoreFirstTeam = dialogView.findViewById<TextView>(R.id.dialogFirstTeamScore)
                var dialogScoreSecondTeam = dialogView.findViewById<TextView>(R.id.dialogSecondTeamScore)
                firstTeam.text = arguments?.getString("teamName1")
                secondTeam.text = arguments?.getString("teamName2")
                dialogScoreFirstTeam.text = firstTeamScore.toString()
                dialogScoreSecondTeam.text = secondTeamScore.toString()
                var buttonGo =dialogView.findViewById<Button>(R.id.buttonGo)
                var alertDialog = alert.create()
                buttonGo.setOnClickListener {
                        if (!textChanged){
                            binding.teamName.text = arguments?.getString("teamName2")
                            binding.scoreText.text = secondTeamScore.toString()
                            scoreChanged = true
                            textChanged = true
                        } else {
                            binding.teamName.text = arguments?.getString("teamName1")
                            binding.scoreText.text = firstTeamScore.toString()
                            textChanged = false
                            scoreChanged = false
                        }
                    tt.start()
                    alertDialog.dismiss()
                    }
                alertDialog.setCancelable(false)
                alertDialog.show()


                    /*
                alert.setPositiveButton("Go") { dialogInterface: DialogInterface, i: Int ->
                    if (!textChanged){
                        binding.teamName.text = arguments?.getString("teamName2")
                        binding.scoreText.text = secondTeamScore.toString()
                        scoreChanged = true
                        textChanged = true
                    } else {
                        binding.teamName.text = arguments?.getString("teamName1")
                        binding.scoreText.text = firstTeamScore.toString()
                        textChanged = false
                        scoreChanged = false
                    }
                    tt.start()
                }.show()
                     */
            }
        }
        tt.start()
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
                                    if (!scoreChanged){
                                        firstTeamScore++
                                        binding.scoreText.text = firstTeamScore.toString()
                                    }else{
                                        secondTeamScore++
                                        binding.scoreText.text = secondTeamScore.toString()
                                    }

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
