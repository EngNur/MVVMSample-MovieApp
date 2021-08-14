package com.nur.moviesapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.nur.moviesapp.R
import com.nur.moviesapp.databinding.ItemMovieBinding
import com.nur.moviesapp.remote.model.Movie
import com.nur.moviesapp.ui.ClickListener
import com.nur.moviesapp.ui.main.MoviesFragmentDirections
import com.nur.moviesapp.util.getProgressDrawable
import com.nur.moviesapp.util.loadImage
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter(private val moviesList: ArrayList<Movie>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() , ClickListener {

    fun updateList(newList : List<Movie>){
        moviesList.clear()
        moviesList.addAll(newList)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemMovieBinding>(inflater,R.layout.item_movie,parent,false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.binding.movie = moviesList[position]
        holder.binding.listener = this
    }

    override fun getItemCount() = moviesList.size

    class MovieViewHolder(var binding: ItemMovieBinding):RecyclerView.ViewHolder(binding.root)

    override fun onClick(v: View) {
        for (item in moviesList){
            if(v.tag == item.imdbID){
                val action = MoviesFragmentDirections.actionDetials(item)
                Navigation.findNavController(v).navigate(action)
            }
        }
    }

}