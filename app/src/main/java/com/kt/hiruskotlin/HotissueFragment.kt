package com.kt.hiruskotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import com.kt.hiruskotlin.ViewModel.HotIssueViewModel
import com.kt.hiruskotlin.databinding.FragmentHotissueBinding
import kotlinx.android.synthetic.main.fragment_hotissue.*
import kotlinx.android.synthetic.main.fragment_hotissue.view.*

class HotissueFragment : Fragment() {
    val texts = Array(10){MutableLiveData<String>()}
    val title = MutableLiveData<String>()
    lateinit var viewModel : HotIssueViewModel
    lateinit var binding : FragmentHotissueBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_hotissue, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            frg = this@HotissueFragment
        }
        val root = binding.root

        viewModel = HotIssueViewModel(root)
        viewModel.dataSet(texts,title)

        binding.invalidateAll()

        return root
    }

}