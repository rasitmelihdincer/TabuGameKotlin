package com.example.tabugamekotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tabugamekotlin.databinding.FragmentFinishragmentBinding

class FinishFragment : Fragment() {

    private lateinit var binding : FragmentFinishragmentBinding
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

        var firstTeamName = arguments?.getString("WinnigTeam")
        var secondTeamName = arguments?.getString("SeconTeam")
        var firstTeamScore = arguments?.getInt("WinnigTeamScore")
        var secondTeamScore = arguments?.getInt("SecondTeamScore")

        if ((firstTeamScore != null) && (secondTeamScore != null) ) {
            if (firstTeamScore >= secondTeamScore!!){
                binding.winnigTeam.text = firstTeamName
                binding.firstTeam.text = firstTeamName
                binding.firstTeamFinishScore.text = firstTeamScore.toString()
                binding.secondTeamFinishScore.text = secondTeamScore.toString()
            } else if (secondTeamScore >= firstTeamScore!!){
                binding.winnigTeam.text = secondTeamName
                binding.firstTeam.text = secondTeamName
                binding.firstTeamFinishScore.text = secondTeamScore.toString()
                binding.secondTeamFinishScore.text = firstTeamScore.toString()
            }
        }

    }
}