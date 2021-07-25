package com.kt.hiruskotlin.viewModel

import android.widget.SearchView
import androidx.fragment.app.FragmentManager
import com.kt.hiruskotlin.WebFragment

class SearchViewModel {
    fun search(searchView: SearchView, code:Int, fm: FragmentManager, id: Int) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val web = WebFragment(code)
                if (query != null) {
                    web.urlCode = query
                }
                fm.beginTransaction().replace(id, web).commit()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }
}