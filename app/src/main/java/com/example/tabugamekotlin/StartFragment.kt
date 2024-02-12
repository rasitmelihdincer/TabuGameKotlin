package com.example.tabugamekotlin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tabugamekotlin.databinding.FragmentStartBinding
import androidx.navigation.ui.navigateUp
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds


class StartFragment : Fragment() {
    var time : Long = 0
    var passLimit : Int = 0
    var tabuLimit : Int = 0
    var finishScore : Int = 0

    private lateinit var binding: FragmentStartBinding
    lateinit var mAdView : AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentStartBinding.inflate(inflater,container,false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*
        time = arguments?.getLong("defaultTime")!!
        passLimit = arguments?.getInt("passLimit")!!
        tabuLimit = arguments?.getInt("tabuLimit")!!
        finishScore = arguments?.getInt("finishScore")!!

         */
        MobileAds.initialize(requireContext()) {}
        mAdView = binding.adView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        binding.settingButton.setOnClickListener {
            val action = StartFragmentDirections.actionStartFragment2ToSettingFragment()
            Navigation.findNavController(it).navigate(action)
        }

        binding.howToPlay.setOnClickListener {
            val action = StartFragmentDirections.actionStartFragment2ToHowToPlayFragment()
            Navigation.findNavController(it).navigate(action)
        }


    }

}