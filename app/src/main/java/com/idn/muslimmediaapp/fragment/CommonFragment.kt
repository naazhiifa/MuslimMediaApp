package com.idn.muslimmediaapp.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.idn.muslimmediaapp.NewsViewModel
import com.idn.muslimmediaapp.databinding.FragmentCommonBinding


/**
 * A simple [Fragment] subclass.
 * Use the [CommonFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CommonFragment : Fragment() {
    private var _binding : FragmentCommonBinding? = null
    private val binding get() = _binding as FragmentCommonBinding

    private var _commonViewModel : NewsViewModel? = null
    private val commonViewModel get() = _commonViewModel as NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommonBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        

    }
}