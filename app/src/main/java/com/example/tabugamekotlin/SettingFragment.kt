package com.example.tabugamekotlin

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.tabugamekotlin.databinding.FragmentGameBinding
import com.example.tabugamekotlin.databinding.FragmentSettingBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

class SettingFragment : Fragment() {

    private lateinit var binding : FragmentSettingBinding
    var value : Long = 45000
    var passLimit : Int = 3
    var tabuLimit : Int = 3
    var finishScore : Int = 30
    var time : Long = 45000
    lateinit var mAdView : AdView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     //   findNavController().navigate(action)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*
        time = arguments?.getLong("defaultTime")!!
        passLimit = arguments?.getInt("passLimit")!!
        tabuLimit = arguments?.getInt("tabuLimit")!!
        finishScore = arguments?.getInt("finishScore")!!
        binding.saveButton.setOnClickListener {

        }
         */
        MobileAds.initialize(requireContext()) {}
        mAdView = binding.adView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)


        binding.settingsTimeText.text = "Time : ${value/1000} sn "
        binding.timeSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
               value = p1.toLong()
               value = value * 1000
               binding.settingsTimeText.text = "Time : ${value/1000} sn "

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                value = value

            }
        })
        binding.passSeekbar.max = 4
        binding.settingsPassText.text = "Pass Limit: ${passLimit}"
        binding.passSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                passLimit = (p1 + 1) * 1
                binding.settingsPassText.text = "Pass Limit: ${passLimit}"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                passLimit = passLimit
            }

        })

        binding.scoreSeekbar.max = 6
        binding.scoreSeekbar.progress = 3
        binding.settingsScore.text = "Finish Score: ${finishScore}"
        binding.scoreSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                finishScore = (p1 + 3) * 10
                binding.settingsScore.text = "Finish Score: ${finishScore}"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                finishScore = finishScore
            }

        })




        binding.saveButton.setOnClickListener {
            val action = SettingFragmentDirections.actionSettingFragmentToGameFragment2(binding.firstTeamName.text.toString(),binding.secondTeamName.text.toString(),value,passLimit,tabuLimit,finishScore)
            Navigation.findNavController(it).navigate(action)
        }

    }

}