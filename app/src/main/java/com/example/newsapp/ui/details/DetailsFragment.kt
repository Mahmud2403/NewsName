package com.example.newsapp.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

	lateinit var binding: FragmentDetailsBinding
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		val view = inflater.inflate(R.layout.fragment_details, container, false)
		binding = FragmentDetailsBinding.bind(view)
		return binding.root
	}

}