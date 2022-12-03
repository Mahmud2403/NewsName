package com.example.newsapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentFavoriteBinding
import com.example.newsapp.databinding.FragmentMainBinding

class MainFragment : Fragment() {

	lateinit var binding: FragmentMainBinding
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		val view = inflater.inflate(R.layout.fragment_main, container, false)
		binding = FragmentMainBinding.bind(view)
		return binding.root

	}

}