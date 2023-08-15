package com.example.tabugamekotlin

import android.animation.ValueAnimator
import android.app.AlertDialog
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabugamekotlin.Adapter.RecyclerViewAdapter
import com.example.tabugamekotlin.Model.Model
import com.example.tabugamekotlin.Service.ApiInterface
import com.example.tabugamekotlin.databinding.FragmentGameBinding
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class GameFragment : Fragment() {

    private var recyclerViewAdapter : RecyclerViewAdapter? = null
    private lateinit var words : ArrayList<Model>
    private lateinit var binding: FragmentGameBinding
    private lateinit var tt : CountDownTimer
    private var textChanged = false
    private var scoreChanged = false
    private var currentIndex = 0
    private var firstTeamScore = 0
    private var secondTeamScore = 0
    private var x = 0
    private var y = 0
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
        val time = requireArguments().getLong("time")
        tt = object : CountDownTimer(10000,1000){
            override fun onTick(p0: Long) {
                binding.time.text = "Time " + (p0/1000).toString()
                val progress = (p0 / 150)
                binding.progressBar.setProgress(progress.toInt())

            }
            override fun onFinish() {
                val dialogView = LayoutInflater.from(context).inflate(R.layout.dialogcard,null)
                val alert = AlertDialog.Builder(context)
                alert.setView(dialogView)
                var firstTeam = dialogView.findViewById<TextView>(R.id.dialogFirstTeam)
                var secondTeam = dialogView.findViewById<TextView>(R.id.dialogSecondTeam)
                var dialogScoreFirstTeam = dialogView.findViewById<TextView>(R.id.dialogFirstTeamScore)
                var dialogScoreSecondTeam = dialogView.findViewById<TextView>(R.id.dialogSecondTeamScore)
                val firstTeamName : String =  arguments?.getString("teamName1")!!
                val secondTeamName : String = arguments?.getString("teamName2")!!
                firstTeam.text = firstTeamName
                secondTeam.text = secondTeamName
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
                            x = 0
                            y = 0
                        } else {
                            binding.teamName.text = arguments?.getString("teamName1")
                            binding.scoreText.text = firstTeamScore.toString()
                            textChanged = false
                            scoreChanged = false
                            x = 0
                            y = 0
                        }
                    tt.start()
                    alertDialog.dismiss()
                    }
                alertDialog.setCancelable(false)
                alertDialog.show()
                val finishScore = arguments?.getInt("finish")
                if(firstTeamScore == finishScore || firstTeamScore >= finishScore!!) {
                    val action = GameFragmentDirections.actionGameFragment2ToFinishFragment(firstTeamName,secondTeamName,firstTeamScore,secondTeamScore)
                    tt.cancel()
                    alertDialog.dismiss()
                    view?.let { Navigation.findNavController(it).navigate(action) }
                } else if (secondTeamScore == finishScore || secondTeamScore >= finishScore) {
                    val action = GameFragmentDirections.actionGameFragment2ToFinishFragment(firstTeamName,secondTeamName,firstTeamScore,secondTeamScore)
                    tt.cancel()
                    alertDialog.dismiss()
                    view?.let { Navigation.findNavController(it).navigate(action) }
                }
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
                                   data()
                            }
                            val passLimit : Int = arguments?.getInt("pass")!!
                            binding.passButton.setOnClickListener {
                                if (!scoreChanged){
                                    x++
                                    if (x <= passLimit ){
                                        data()
                                    }
                                }
                                else if (scoreChanged){
                                    x++
                                        if (x <= passLimit){
                                            data()

                                        }
                                }
                            }
                            var tabuLimit : Int = arguments?.getInt("tabu")!!
                            binding.tabuButton.setOnClickListener {
                                if (!scoreChanged){
                                    y++
                                    if (y <= tabuLimit ){
                                        firstTeamScore--
                                        binding.scoreText.text = firstTeamScore.toString()
                                        data()
                                    }
                                }
                                else if (scoreChanged){
                                    y++
                                    if (y <= tabuLimit){
                                        secondTeamScore--
                                        binding.scoreText.text = secondTeamScore.toString()
                                        data()

                                    }
                                }
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

    fun data(){
        currentIndex++
        binding.mainWord.text = words!![currentIndex].word
        recyclerViewAdapter!!.words[0].forbiddenWords?.clear()
        words!![currentIndex].forbiddenWords?.get(0)?.let { it1 ->
            recyclerViewAdapter!!.words[0].forbiddenWords?.add(
                it1)
        }
        words!![currentIndex].forbiddenWords?.get(1)?.let { it1 ->
            recyclerViewAdapter!!.words[0].forbiddenWords?.add(
                it1)
        }
        words!![currentIndex].forbiddenWords?.get(2)?.let { it1 ->
            recyclerViewAdapter!!.words[0].forbiddenWords?.add(
                it1)
        }
        words!![currentIndex].forbiddenWords?.get(3)?.let { it1 ->
            recyclerViewAdapter!!.words[0].forbiddenWords?.add(
                it1)
        }
        words!![currentIndex].forbiddenWords?.get(4)?.let { it1 ->
            recyclerViewAdapter!!.words[0].forbiddenWords?.add(
                it1)
        }
        recyclerViewAdapter!!.notifyDataSetChanged()
    }

}

