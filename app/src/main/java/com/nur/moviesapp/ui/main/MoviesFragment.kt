package com.nur.moviesapp.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.nur.moviesapp.R
import com.nur.moviesapp.remote.model.Movie
import com.nur.moviesapp.ui.adapter.MovieAdapter
import kotlinx.android.synthetic.main.fragment_movies.*

class MoviesFragment : Fragment() {

    private lateinit var viewModel : MoviesVM
    private val listAdapter = MovieAdapter(arrayListOf())

    private val moviesListDataObserver = Observer<List<Movie>>{list ->
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

        viewModel = ViewModelProviders.of(this).get(MoviesVM::class.java)
        viewModel.movies.observe(viewLifecycleOwner,moviesListDataObserver)
        viewModel.loadError.observe(viewLifecycleOwner,errorListDataObserver)
        viewModel.loading.observe(viewLifecycleOwner,loadingLiveDataObserver)
        viewModel.refresh()

        movies_recycler_view.apply {
            layoutManager = GridLayoutManager(context,2)
            adapter = listAdapter
        }

        refreshLayout.setOnRefreshListener {
            movies_recycler_view.visibility = View.GONE
            list_error.visibility = View.GONE
            loading_view.visibility = View.GONE
            viewModel.getMoviesByPassDB()
            refreshLayout.isRefreshing = false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.list_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.actionSettings -> {
                view?.let{Navigation.findNavController(it).navigate(MoviesFragmentDirections.actionSettings())}
            }

        }
        return super.onOptionsItemSelected(item)
    }

}