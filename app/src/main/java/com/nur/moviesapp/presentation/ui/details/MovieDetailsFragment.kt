package com.nur.moviesapp.presentation.ui.details

import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.lifecycle.Observer
import android.os.Bundle
import android.telephony.SmsManager
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.nur.moviesapp.R
import com.nur.moviesapp.common.util.PermissionHelper
import com.nur.moviesapp.common.util.PERMISSION_SEND_SMS
import com.nur.moviesapp.databinding.DialogSendSmsBinding
import com.nur.moviesapp.databinding.FragmentMovieDetailsBinding
import com.nur.moviesapp.data.remote.model.MovieDetails
import com.nur.moviesapp.data.remote.model.MoviePalette
import com.nur.moviesapp.domain.model.SMSInfo
import com.nur.moviesapp.presentation.ui.MainActivity
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_movie_details.*
import kotlinx.android.synthetic.main.fragment_movie_details.list_error
import kotlinx.android.synthetic.main.fragment_movie_details.loading_view
import javax.inject.Inject

class MovieDetailsFragment : DaggerFragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: MovieDetailsVM
    var movieId: String = ""
    var movieDetails: MovieDetails? = null
    private lateinit var binding: FragmentMovieDetailsBinding

    private var sendSMSStarted = false

    private val detailsDataObserver = Observer<MovieDetails> { details ->
        details?.let {
            binding.movie = details
            list_error.visibility = View.GONE
            details_view.visibility = View.VISIBLE
            movieDetails = details
            details.Poster.let {
                setUpBackgroundColor(it)
            }
        }
    }

    private val errorListDataObserver = Observer<Boolean>{isError ->
        details_view.visibility = if(isError)View.GONE else View.VISIBLE
        list_error.visibility  = if(isError)View.VISIBLE else View.GONE
    }

    private val loadingLiveDataObserver = Observer<Boolean> { isLoading ->
        loading_view.visibility = if (isLoading) View.VISIBLE else View.GONE
    }




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            movieId = MovieDetailsFragmentArgs.fromBundle(it).movieId

        }
        viewModel = ViewModelProvider(this,viewModelFactory).get(MovieDetailsVM::class.java)
        viewModel.movieDetails.observe(viewLifecycleOwner, detailsDataObserver)
        viewModel.loading.observe(viewLifecycleOwner, loadingLiveDataObserver)
        viewModel.loadError.observe(viewLifecycleOwner, errorListDataObserver)
         viewModel.getMovieDetails(movieId)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.details_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_send_sms -> {
                sendSMSStarted = true
                notifyDetailsFragment(PermissionHelper.instance.checkSMSPermission(requireContext(),requireActivity()))
            }
            R.id.action_share -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_SUBJECT,"Check out this movie!!")
                intent.putExtra(Intent.EXTRA_TEXT,"oh man check out this movie!!  ${movieDetails?.Title}")
                intent.putExtra(Intent.EXTRA_STREAM,movieDetails?.Poster)
                startActivity(Intent.createChooser(intent,"Share with"))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun onPermissionResult(permissionGranted: Boolean) {
        if (sendSMSStarted && permissionGranted) {
            context?.let {
                val smsInfo = SMSInfo(
                    "",
                    "oh man check out this movie!!  ${movieDetails?.Title}",
                    movieDetails?.Poster
                )
                val dialogBinding = DataBindingUtil.inflate<DialogSendSmsBinding>(
                    LayoutInflater.from(it),
                    R.layout.dialog_send_sms,
                    null,
                    false
                )
                AlertDialog.Builder(it)
                    .setView(dialogBinding.root)
                    .setPositiveButton("Send SMS"){dialog , whitch ->
                        if(!dialogBinding.smsDestination.text.isNullOrEmpty()){
                            smsInfo.to = dialogBinding.smsDestination.text.toString()
                            sendSMS(smsInfo)
                        }
                    }
                    .setNegativeButton("Cancel"){dialog , whitch -> }
                    .show()
                dialogBinding.smsInfo = smsInfo
            }
        }
    }

    private fun sendSMS(smsInfo : SMSInfo){
        val intent = Intent(context, MainActivity::class.java)
        val pi = PendingIntent.getActivity(context,0,intent,0)
        val sms = SmsManager.getDefault()
        sms.sendTextMessage(smsInfo.to,null,smsInfo.text,pi,null)
    }

    private fun notifyDetailsFragment(isGranted : Boolean){
            onPermissionResult(isGranted)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_SEND_SMS -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    notifyDetailsFragment(true)
                else
                    notifyDetailsFragment(false)
            }
        }
    }

    private fun setUpBackgroundColor(url: String) {
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource)
                        .generate { palette ->
                            binding.palette = MoviePalette(palette?.lightMutedSwatch?.rgb ?: 0)
                        }
                }
                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }

}