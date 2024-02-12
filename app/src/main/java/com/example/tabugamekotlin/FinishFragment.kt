package com.example.tabugamekotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.tabugamekotlin.databinding.FragmentFinishragmentBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

class FinishFragment : Fragment() {

    private lateinit var binding : FragmentFinishragmentBinding
    lateinit var mAdView : AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFinishragmentBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MobileAds.initialize(requireContext()) {}
        mAdView = binding.adView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        var firstTeamName = arguments?.getString("WinnigTeam")
        var secondTeamName = arguments?.getString("SeconTeam")
        var firstTeamScore = arguments?.getInt("WinnigTeamScore")
        var secondTeamScore = arguments?.getInt("SecondTeamScore")

        if ((firstTeamScore != null) && (secondTeamScore != null) ) {
            if (firstTeamScore >= secondTeamScore!!){
                binding.winnigTeam.text = firstTeamName
                binding.secondTeam.text = secondTeamName
                binding.firstTeam.text = firstTeamName
                binding.firstTeamFinishScore.text =   "Score : " + firstTeamScore.toString()
                binding.secondTeamFinishScore.text = "Score : " + secondTeamScore.toString()
            } else if (secondTeamScore >= firstTeamScore!!){
                binding.winnigTeam.text = secondTeamName
                binding.firstTeam.text = secondTeamName
                binding.secondTeam.text = firstTeamName
                binding.firstTeamFinishScore.text = "Score : " +secondTeamScore.toString()
                binding.secondTeamFinishScore.text = "Score : " +firstTeamScore.toString()
            }
        }
        binding.menuButton.setOnClickListener {
            val action = FinishFragmentDirections.actionFinishFragmentToStartFragment2()
            Navigation.findNavController(view).navigate(action)
        }
    }
}