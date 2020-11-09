package com.weight68kg.callforwarding

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        phoneNumber1()
        tv_call.setOnClickListener {
            val intent = Intent(Intent.ACTION_CALL)
            val data: Uri = Uri.parse("tel:${et.text}")
            intent.data = data
            startActivity(intent)

        }

        tv_call1.setOnClickListener {
            val phoneUri = Uri.parse("tel:**67*${et.text}%23")
            val intent = Intent(Intent.ACTION_CALL, phoneUri)
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Log.e("Shawn", "open: 没有权限")

            }
            startActivityForResult(intent,0x111)

//            val intent2 = Intent(Intent.ACTION_CALL)
//            val data: Uri = Uri.parse("tel:${15506025361}")
//            intent2.data = data
//            startActivity(intent2)
        }
    }


    private fun phoneNumber1() {
        val systemService = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_SMS
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_NUMBERS
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        val line1Number = systemService.line1Number
        Log.e("tag", "$line1Number")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("tag","$resultCode")
    }
}