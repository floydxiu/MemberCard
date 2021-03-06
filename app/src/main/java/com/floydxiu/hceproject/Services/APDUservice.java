package com.floydxiu.hceproject.Services;

import android.content.Intent;
import android.nfc.cardemulation.HostApduService;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;

import com.floydxiu.hceproject.DataType.ApduCommand;

/**
 * Created by Floyd on 2016/11/23.
 */

public class APDUservice extends HostApduService {
    final String TAG = "HceService";
    String[] aidStr = {"F0010203040506", "F0394148148100"};
    static byte[][] aidByte = {
            {(byte)0xF0, (byte)0x01, (byte)0x02, (byte)0x03, (byte)0x04, (byte)0x05, (byte)0x06},
            {(byte)0xF0, (byte)0x39, (byte)0x41, (byte)0x48, (byte)0x14, (byte)0x81, (byte)0x00}
    };

    String TransCode;

    Intent cardtransactivity;

    public static String APDU_RESPONSE_NONACTIVE = "NA";
    public static String APDU_RESPONSE_UNKNOWN_CMD = "NC";

    boolean lock = true;

    public APDUservice() {
        super();
    }

    @Override
    public byte[] processCommandApdu(byte[] apdu, Bundle extras) {
        if(TransCode == null){ //check service is start or not
            return APDU_RESPONSE_NONACTIVE.getBytes();
        }
        else{
            //在此處理Reader傳入的訊息
            ApduCommand apduCommand = new ApduCommand(apdu);
            if(apduCommand.step == 0){
                if( lock == true){
                    Intent intent = new Intent("MemberCard.hce.app.action.NOTIFY_STATE");
                    intent.putExtra("STATE", true);
                    this.sendBroadcast(intent);
                    lock = false;
                    return TransCode.getBytes();
                }
                else{
                    return APDU_RESPONSE_NONACTIVE.getBytes();
                }
            }
            else{
                return APDU_RESPONSE_UNKNOWN_CMD.getBytes();
            }
        }
    }

    //當連線中斷
    @Override
    public void onDeactivated(int reason) {
        Log.i(TAG, "Deactivated: " + reason);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, TAG + " create");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, TAG + " destroyed");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        cardtransactivity = intent;
        //service開始之後會執行此部分
        if(intent.getExtras() != null){
            TransCode = intent.getExtras().getString("TransCode");
            System.out.println("putExtra sucess");
        }
        Log.i(TAG, TAG+" started");
        return super.onStartCommand(intent, flags, startId);
    }

    public class apduBinder extends Binder{
        public APDUservice getService(){
            return APDUservice.this;
        }
    }
}
