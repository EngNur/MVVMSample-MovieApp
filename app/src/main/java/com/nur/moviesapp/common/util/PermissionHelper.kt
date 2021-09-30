package com.nur.moviesapp.common.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class PermissionHelper {

  init {
  }
    companion object {
        val instance by lazy { PermissionHelper() }
    }


     fun checkSMSPermission(context: Context, activity: Activity) : Boolean{
        var isPermissionGranted = false
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    Manifest.permission.SEND_SMS
                )){
                AlertDialog.Builder(context)
                    .setTitle("Send SMS Permission")
                    .setMessage("This app requires access to send an SMS")
                    .setPositiveButton("Ask Me"){ dialog, whitch -> requestSMSPermission(activity) }
                    .setNegativeButton("No"){ dialog, whitch -> isPermissionGranted =  false}
                    .show()
            }else{
                requestSMSPermission(activity)
            }
        }else{
            isPermissionGranted = true
        }

        return isPermissionGranted
    }

    private fun requestSMSPermission(activity: Activity){
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.SEND_SMS),
            PERMISSION_SEND_SMS
        )
    }

}