package com.example.newsapp.ui.search

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentSearchBinding
import com.example.newsapp.ui.main.NewsAdapter
import com.example.newsapp.utils.Resourse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

	lateinit var binding: FragmentSearchBinding

	private val viewModel by viewModels<SearchViewModel>()
	lateinit var searchAdapter: NewsAdapter

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		val view = inflater.inflate(R.layout.fragment_search, container, false)
		binding = FragmentSearchBinding.bind(view)
		return binding.root

	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initAdapter()
		var job: Job? = null
		et_search.addTextChangedListener{ text: Editable? ->
			job?.cancel()
			job = MainScope().launch {
				delay(500L)
				text?.let {
					if(it.toString().isNotEmpty()) {
						viewModel.getSearchNews(it.toString())
					}
				}
			}
		}
		viewModel.searchNewsLiveData.observe(viewLifecycleOwner) { response ->
			when (response) {
				is Resourse.Success -> {
					search_progress_bar.visibility = View.INVISIBLE
					response.data?.let {
						searchAdapter.differ.submitList(it.articles)
					}
				}
				is Resourse.Error -> {
					search_progress_bar.visibility = View.INVISIBLE
					response.data?.let {
						Log.e("CheckData", "MainFragmentError: ${it}")
					}
				}
				is Resourse.Loading -> {
					search_progress_bar.visibility = View.VISIBLE
				}
			}
		}
	}


	private fun initAdapter() {
		searchAdapter = NewsAdapter()
		rc_search.apply {
			adapter = searchAdapter
			layoutManager = LinearLayoutManager(activity)
		}
	}
}