package com.weight68kg.callforwarding

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import com.weight68kg.callforwarding.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rxPermissions = RxPermissions(this)
        val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        rxPermissions.request(
                Manifest.permission.READ_SMS,
                Manifest.permission.READ_PHONE_NUMBERS,
                Manifest.permission.READ_PHONE_STATE
        ).subscribe({
            if (it) {
                phone.setText("${telephonyManager.line1Number}")
            } else {
                Toast.makeText(this, "没有权限获取当前手机手机号", Toast.LENGTH_SHORT).show()
            }
        })


        call.setOnClickListener {
            rxPermissions.request(Manifest.permission.CALL_PHONE)
                    .subscribe {
                        if (it) {
//                            val uri = Uri.parse("tel:%23%2367%23")
                            val uri = Uri.parse("tel:**67*${et_call_phone.text}%23")
                            val intent = Intent(Intent.ACTION_CALL, uri)
                            startActivity(intent)


                            val handler = Handler(mainLooper)
                            handler.postDelayed({
                                val uri2 = Uri.parse("tel:${phone.text}")
                                val intent2 = Intent(Intent.ACTION_CALL, uri2)
                                startActivity(intent2)
                            }, 3000)
                        } else {
                            Toast.makeText(this, "没有权限获拨打手机", Toast.LENGTH_SHORT).show()
                        }
                    }
        }




    }

    override fun onRestart() {
        super.onRestart()
        val handler = Handler(mainLooper)
        handler.postDelayed({
            val intent1 = Intent(Intent.ACTION_CALL, Uri.parse("tel:%23%2367%23"))
            startActivity(intent1)
        }, 700)

    }

}