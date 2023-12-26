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


class StartFragment : Fragment() {
    var time : Long = 0
    var passLimit : Int = 0
    var tabuLimit : Int = 0
    var finishScore : Int = 0

    private lateinit var binding: FragmentStartBinding

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
        binding.startGameButton.setOnClickListener {
          //  val action = StartFragmentDirections.actionStartFragment2ToGameFragment2(binding.firstTeamName.text.toString(),binding.secondTeamName.text.toString(),time,passLimit,tabuLimit,finishScore)
          // Navigation.findNavController(it).navigate(action)
        }
        binding.settingButton.setOnClickListener {
            val action = StartFragmentDirections.actionStartFragment2ToSettingFragment()
            Navigation.findNavController(it).navigate(action)
        }


    }

}