package com.example.tabugamekotlin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.tabugamekotlin.databinding.FragmentStartBinding
import androidx.navigation.ui.navigateUp


class StartFragment : Fragment() {

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

        binding.startGameButton.setOnClickListener {
            val firstTeamName =binding.firstTeamName.text.toString()
            val action = StartFragmentDirections.actionStartFragment2ToGameFragment2(firstTeamName)
            Navigation.findNavController(it).navigate(action)



        }

    }

}