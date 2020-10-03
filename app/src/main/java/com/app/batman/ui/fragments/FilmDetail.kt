package com.app.batman.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.app.batman.R
import com.app.batman.database.BatmanDb
import com.app.batman.databinding.FragmentFilmDetailBinding
import com.app.batman.models.Film
import com.app.batman.utilities.LogicUtilityFunctions.onBackPressed
import com.app.batman.viewmodels.FilmDetailViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main


class FilmDetail : Fragment() {
    private lateinit var navController: NavController
    private lateinit var viewModel: FilmDetailViewModel
    private lateinit var fragmentFilmDetailBinding: FragmentFilmDetailBinding
    private var imdbId: String = ""
    private lateinit var batmanDb: BatmanDb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imdbId = requireArguments().getString("imdbId").toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentFilmDetailBinding =
            DataBindingUtil.inflate(
                inflater, R.layout.fragment_film_detail, container, false
            )
        return fragmentFilmDetailBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        batmanDb = BatmanDb.getInstance(requireContext())
        viewModel = ViewModelProvider(this).get(FilmDetailViewModel::class.java)
        fragmentFilmDetailBinding.film = Film()
        getFilmDetail(imdbId)
        CoroutineScope(Dispatchers.IO).launch {
            val res = async {
                batmanDb.filmDao().findFilmDetails(imdbId)
            }
            withContext(Main) {
                fragmentFilmDetailBinding.film = res.await()
                viewModel.result.observe(viewLifecycleOwner, Observer {
                    if (it != null)
                        fragmentFilmDetailBinding.film = it
                })
            }
        }
        onBackPressed(requireActivity()) {
            navController.popBackStack()
        }
    }

    private fun getFilmDetail(imdbId: String) {
        viewModel.inputData(imdbId)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancelJob()
    }
}