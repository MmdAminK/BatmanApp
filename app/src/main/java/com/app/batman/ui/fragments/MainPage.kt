package com.app.batman.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.room.withTransaction
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.app.batman.R
import com.app.batman.adapters.FilmsAdapter
import com.app.batman.database.BatmanDb
import com.app.batman.databinding.FragmentMainPageBinding
import com.app.batman.models.Film
import com.app.batman.viewmodels.MainPageViewModel
import kotlinx.android.synthetic.main.fragment_main_page.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.collectLatest
import java.text.FieldPosition

class MainPage : Fragment() {
    private lateinit var viewModel: MainPageViewModel
    private lateinit var mainPageBinding: FragmentMainPageBinding
    private lateinit var filmsAdapter: FilmsAdapter
    private lateinit var batmanDb: BatmanDb
    private lateinit var navController: NavController
    private var size: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainPageBinding =
            DataBindingUtil.inflate(
                inflater, R.layout.fragment_main_page, container, false
            )
        return mainPageBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainPageViewModel::class.java)
        filmsAdapter = FilmsAdapter(ArrayList())
        batmanDb = BatmanDb.getInstance(requireContext())
        mainPageBinding.mainPageViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
            }

            @SuppressLint("SetTextI18n")
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                mainPageBinding.slideCounter.text = "${position + 1} / 10"
            }

            override fun onPageSelected(position: Int) {
            }
        })
        CoroutineScope(IO).launch {
            val res = async {
                batmanDb.filmDao().findFilms()
            }
            withContext(Main) {
                val films = res.await()
                filmsAdapter = FilmsAdapter(films)
                size = films.size
                viewPagerInit(films)
                viewModel.result.observe(viewLifecycleOwner, Observer {
                    it?.let {
                        filmsAdapter = FilmsAdapter(it.films!!)
                        if (it.films!!.size != size)
                            viewPagerInit(it.films!!)
                    }
                })
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun viewPagerInit(films: List<Film>) {
        mainPageBinding.apply {
            filmsAdapter.setOnItem(object : FilmsAdapter.OnItemClickListener {
                override fun itemViewClick(position: Int) {
                    Log.i("ksdkdkd", "itemViewClick:${films[position].imdbId} ")
                    onCardClick(films[position].imdbId, position)
                }
            })
            filmsAdapter.stateRestorationPolicy =
                RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

            with(mainPageViewPager) {
                adapter = filmsAdapter
                clipToPadding = false
                clipChildren = false
                offscreenPageLimit = 3
            }
            val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.pageMargin)
            val offsetPx = resources.getDimensionPixelOffset(R.dimen.offset)
            mainPageViewPager.setPageTransformer { page, position ->
                val viewPager2 = page.parent.parent as ViewPager2
                val offset = position * -(2 * offsetPx + pageMarginPx)
                if (viewPager2.orientation == ViewPager2.ORIENTATION_HORIZONTAL) {
                    if (ViewCompat.getLayoutDirection(viewPager2) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                        page.translationX = -offset
                    } else {
                        page.translationX = offset
                    }
                } else {
                    page.translationY = offset
                }
            }
        }
    }

    private fun onCardClick(imdbId: String, position: Int) {
        val bundle = bundleOf("imdbId" to imdbId)
        viewModel.page = position
        navController.navigate(
            R.id.action_mainPage_to_filmDetail,
            bundle
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancelJob()
    }
}