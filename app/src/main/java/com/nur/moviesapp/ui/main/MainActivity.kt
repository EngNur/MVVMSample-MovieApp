package com.nur.moviesapp.ui.main

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.BoringLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.nur.moviesapp.R
import com.nur.moviesapp.ui.details.MovieDetailsFragment
import com.nur.moviesapp.util.PERMISSION_SEND_SMS
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var  navController : NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this,R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this,navController)
    }


    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController,null)
    }

    fun checkSMSPermission(){
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.SEND_SMS)){
AlertDialog.Builder(this)
    .setTitle("Send SMS Permission")
    .setMessage("This app requires access to send an SMS")
    .setPositiveButton("Ask Me"){dialog , whitch -> requestSMSPermission() }
    .setNegativeButton("No"){dialog , whitch -> notifyDetailsFragment(false)}
    .show()
            }else{
                requestSMSPermission()
            }
        }else{
            notifyDetailsFragment(true)
        }
    }

    fun notifyDetailsFragment(isGranted : Boolean){
        val activeFragment = nav_host_fragment.childFragmentManager.primaryNavigationFragment
        if(activeFragment is MovieDetailsFragment){
            (activeFragment as MovieDetailsFragment).onPermissionResult(isGranted)
        }
    }
    fun requestSMSPermission(){
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS), PERMISSION_SEND_SMS)

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
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}