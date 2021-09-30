package com.nur.moviesapp.presentation.ui.movieList

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.nur.moviesapp.R
import com.nur.moviesapp.domain.model.MovieDomain
import com.nur.moviesapp.presentation.ui.adapter.MovieAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_movies.*
import javax.inject.Inject

class MovieListFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel : MovieListVM
    private val listAdapter = MovieAdapter()

    private val moviesListDataObserver = Observer<List<MovieDomain>>{ list ->
        list?.let {
            movies_recycler_view.visibility = View.VISIBLE
            listAdapter.updateList(list)
        }
    }

    private val errorListDataObserver = Observer<Boolean>{isError ->
        list_error.visibility  = if(isError)View.VISIBLE else View.GONE
    }

    private val loadingLiveDataObserver = Observer<Boolean>{isLoading ->
        loading_view.visibility  = if(isLoading)View.VISIBLE else View.GONE
        if(refreshLayout.isRefreshing )
            refreshLayout.isRefreshing = false
        if(isLoading){
            movies_recycler_view.visibility = View.GONE
            list_error.visibility = View.GONE
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_movies,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =ViewModelProvider(this,viewModelFactory).get(MovieListVM::class.java)
        viewModel.movies.observe(viewLifecycleOwner,moviesListDataObserver)
        viewModel.loadError.observe(viewLifecycleOwner,errorListDataObserver)
        viewModel.loading.observe(viewLifecycleOwner,loadingLiveDataObserver)
        viewModel.refresh()

        movies_recycler_view.apply {
            layoutManager = GridLayoutManager(context,2)
            adapter = listAdapter
        }

        refreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.list_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.actionSettings -> {
               view?.let{Navigation.findNavController(it).navigate(MovieListFragmentDirections.actionSettings())}
            }
        }
        return super.onOptionsItemSelected(item)
    }

}