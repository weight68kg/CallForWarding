package com.weight68kg.callforwarding

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.tbruyelle.rxpermissions3.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    val rxPermissions = RxPermissions(this);
    var line1Number = ""

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val systemService = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        rxPermissions.request(
            Manifest.permission.READ_SMS,
            Manifest.permission.READ_PHONE_NUMBERS,
            Manifest.permission.READ_PHONE_STATE
        )
            .subscribe { granted ->
                if (granted) { //
                    line1Number = systemService.line1Number
                } else {

                }
            }


        tv_call.setOnClickListener {
            val intent = Intent(Intent.ACTION_CALL)
            val data: Uri = Uri.parse("tel:${et.text}")
            intent.data = data
            startActivity(intent)

        }

        tv_call1.setOnClickListener {


            rxPermissions
                .request(Manifest.permission.CALL_PHONE)
                .subscribe { granted ->
                    if (granted) { // Always true pre-M
                        // I can control the camera now
                        val phoneUri = Uri.parse("tel:**67*${et.text}%23")
                        val intent = Intent(Intent.ACTION_CALL, phoneUri)
                        startActivity(intent)

                        val handler = Handler(mainLooper)
                        handler.postDelayed({
                            val intent2 = Intent(Intent.ACTION_CALL)
                            val data: Uri = Uri.parse("tel:${line1Number}")
                            intent2.data = data
                            startActivity(intent2)
                        }, 2000)
                    } else {
                        // Oups permission denied
                    }

                }


        }
    }


}