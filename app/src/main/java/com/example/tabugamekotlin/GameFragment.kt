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
import com.example.tabugamekotlin.Model.Data
import com.example.tabugamekotlin.Model.Model
import com.example.tabugamekotlin.Service.ApiInterface
import com.example.tabugamekotlin.databinding.FragmentGameBinding
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random


class GameFragment : Fragment() {

    private var recyclerViewAdapter : RecyclerViewAdapter? = null
    private lateinit var words : Model
    private lateinit var binding: FragmentGameBinding
    private lateinit var tt : CountDownTimer
    private var textChanged = false
    private var scoreChanged = false
    private var currentIndex = 0
    private var firstTeamScore = 0
    private var secondTeamScore = 0
    private var x = 0
    private var y = 0
    private var z = 3
    private val BASE_URL = "https://www.taboocardsapi.com/"
    var teamName1 : String? = null
    var teamName2 : String? = null
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
         teamName1  = arguments?.getString("teamName1")
        teamName2 = arguments?.getString("teamName2")
        binding.teamName.text = teamName1
        binding.teamName2.text = teamName2
        val time : Long = arguments?.getLong("time")!!

        tt = object : CountDownTimer(time,1000){
            override fun onTick(time: Long) {
                binding.time.text = (time/1000).toString()
                val progress = time / 600
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
                        //    binding.scoreText.setText("Score : ${secondTeamScore}")
                            scoreChanged = true
                            textChanged = true
                            x = 0
                            y = 0
                            z = 3
                            binding.passButton.text = "Pass ${z}"
                        } else {
                            binding.teamName.text = arguments?.getString("teamName1")
                        //    binding.scoreText.setText("Score : ${firstTeamScore}")
                            textChanged = false
                            scoreChanged = false
                            x = 0
                            y = 0
                            z = 3
                            binding.passButton.text = "Pass ${z}"
                        }
                    tt.start()
                    alertDialog.dismiss()
                    }
                alertDialog.setCancelable(false)
                alertDialog.show()
                    val finishScore : Int = arguments?.getInt("finish")!!
                    if(firstTeamScore >= finishScore) {
                        val action = GameFragmentDirections.actionGameFragment2ToFinishFragment(firstTeamName,secondTeamName,firstTeamScore,secondTeamScore)
                        alertDialog.dismiss()
                        tt.cancel()
                        view?.let { Navigation.findNavController(it).navigate(action) }
                    } else if (secondTeamScore >= finishScore) {
                        val action = GameFragmentDirections.actionGameFragment2ToFinishFragment(firstTeamName,secondTeamName,firstTeamScore,secondTeamScore)
                        alertDialog.dismiss()
                        tt.cancel()
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
        call.enqueue(object : retrofit2.Callback<Model> {
            override fun onResponse(
                call: Call<Model>,
                response: Response<Model>,
            ) {
                if (response.isSuccessful) {
                    println("aaaa")
                    response.body()?.let {
                        words = it
                        words?.let {
                            println("bbbb")
                            println(response.body())
                            recyclerViewAdapter = RecyclerViewAdapter(words)
                            binding.recyclerView.adapter = recyclerViewAdapter
                            binding.mainWord.text = words.data[0].title
                            binding.trueButton.setOnClickListener {
                                if (!scoreChanged){
                                    firstTeamScore++

                                    binding.teamName.text = teamName1+ " :" + firstTeamScore
                                }else{
                                    secondTeamScore++

                                    binding.teamName2.text = teamName2 + " :"+ secondTeamScore
                                }
                                   data()
                            }
                            var passLimit : Int = arguments?.getInt("pass")!!
                            binding.passButton.setOnClickListener {
                                if (!scoreChanged){
                                    x++
                                    if (x <= passLimit ){
                                        z--
                                        binding.passButton.text = "Pass ${z}"
                                        data()
                                    }
                                }
                                else if (scoreChanged){
                                    x++
                                        if (x <= passLimit){
                                             z--
                                            binding.passButton.text = "Pass ${z}"

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
                                        binding.teamName.text = teamName1+ " :" + firstTeamScore
                                        data()
                                    }
                                }
                                else if (scoreChanged){
                                    y++
                                    if (y <= tabuLimit){
                                        secondTeamScore--
                                        binding.teamName2.text = teamName2 + " :"+ secondTeamScore
                                        data()

                                    }
                                }
                            }

                        }
                    }
                }
            }
            override fun onFailure(call: Call<Model>, t: Throwable) {
                println("sıkıntı varaaa")
                t.printStackTrace()
            }
        })
    }
    var randomInt = getRandomInt(1..1000)
    fun data(){
        randomInt++
        binding.mainWord.text = words.data[randomInt].title
         recyclerViewAdapter?.updateForbiddenWords(words.data[randomInt].forbiddenWords)
        recyclerViewAdapter!!.notifyDataSetChanged()
    }
    fun getRandomInt(range: IntRange): Int {
        return Random.nextInt(range.first, range.last + 1)
    }

}

