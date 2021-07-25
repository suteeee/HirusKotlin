package com.kt.hiruskotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.kt.hiruskotlin.databinding.FragmentUserBinding
import com.kt.hiruskotlin.model.MySharedPrefsModel

class UserFragment : Fragment() {
    lateinit var binding: FragmentUserBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false)

        binding.prefs = MySharedPrefsModel(requireContext())
        binding.invalidateAll()
        return binding.root
    }
}