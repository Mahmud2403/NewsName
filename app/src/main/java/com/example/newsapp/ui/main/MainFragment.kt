package com.example.newsapp.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentMainBinding
import com.example.newsapp.utils.Resourse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*


@AndroidEntryPoint
class MainFragment : Fragment() {

	lateinit var binding: FragmentMainBinding

	private val viewModel by viewModels<MainViewModel>()
	lateinit var newsAdapter: NewsAdapter

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		val view = inflater.inflate(R.layout.fragment_main, container, false)
		binding = FragmentMainBinding.bind(view)
		return binding.root

	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initAdapter()
		viewModel.newsLiveData.observe(viewLifecycleOwner){ response ->
			when (response) {
				is Resourse.Success ->{
					progress_bar.visibility = View.INVISIBLE
					response.data?.let {
						newsAdapter.differ.submitList(it.articles)
					}
				}
				is Resourse.Error ->{
					progress_bar.visibility = View.INVISIBLE
					response.data?.let {
						Log.e("CheckData", "MainFragmentError: ${it}")
					}
				}is Resourse.Loading ->{
					progress_bar.visibility = View.VISIBLE
				}
			}
		}
	}


	private fun initAdapter(){
		newsAdapter = NewsAdapter()
		rc_main.apply {
			adapter = newsAdapter
			layoutManager = LinearLayoutManager(activity)
		}
	}
}