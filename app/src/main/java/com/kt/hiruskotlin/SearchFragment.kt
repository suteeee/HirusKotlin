package com.kt.hiruskotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kt.hiruskotlin.viewModel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.view.*

class SearchFragment : Fragment() {
    private val URL_FLAG = 0
    val viewModel = SearchViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_search, container, false)
        val fm = fragmentManager
        val search = rootView.searchView_sv

        if (fm != null) {
            viewModel.search(search,URL_FLAG, fm, R.id.webAdd_lt)
        }

        return rootView
    }




}