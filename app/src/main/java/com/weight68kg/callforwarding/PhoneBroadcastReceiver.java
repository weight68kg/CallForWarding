package com.weight68kg.callforwarding;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.ArrayList;

public class PhoneBroadcastReceiver extends BroadcastReceiver {


    String TAG = "tag";
    TelephonyManager telMgr;

    @Override
    public void onReceive(Context context, Intent intent) {


        telMgr = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
        switch (telMgr.getCallState()) {
            //来电
            case TelephonyManager.CALL_STATE_RINGING:
//                String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
//                Log.v(TAG, "number:" + number);
//                Intent myintent = new Intent(context, PhoneService.class);
//                myintent.setAction("com.lvshandian.menshen.service.PhoneReciever");
//                context.startService(myintent);
//                if (!getPhoneNum(context).contains(number)) {
//                    SharedPreferences phonenumSP = context.getSharedPreferences("in_phone_num", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = phonenumSP.edit();
//                    editor.putString(number, number);
//                    editor.commit();
//                    endCall();
//                }
                break;
            //响铃
            case TelephonyManager.CALL_STATE_OFFHOOK:
                break;
            //挂断
            case TelephonyManager.CALL_STATE_IDLE:
                Log.e("tag", "CALL_STATE_IDLE");

//                Intent intent1 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:##67#"));
//                context.startActivity(intent1);
                break;
        }

    }


    private ArrayList<String> getPhoneNum(Context context) {
        ArrayList<String> numList = new ArrayList<String>();
        //得到ContentResolver对象
        ContentResolver cr = context.getContentResolver();
        //取得电话本中开始一项的光标
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            // 取得联系人ID
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
            // 取得电话号码(可能存在多个号码)
            while (phone.moveToNext()) {
                String strPhoneNumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                numList.add(strPhoneNumber);
                Log.v("tag", "strPhoneNumber:" + strPhoneNumber);
            }

            phone.close();
        }
        cursor.close();
        return numList;
    }


}
