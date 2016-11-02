package com.yanis.getmyphonenumber;

import android.app.Service;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.IBinder;
import android.provider.ContactsContract;


/**
 * Created by dongwei on 16/10/28.
 */

public class ServiceSynchContract extends Service {


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private ContentObserver mObserver = new ContentObserver(new Handler()) {

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            GetNumber.getNumber(ServiceSynchContract.this);
        }
    };

    @Override
    public void onStart(Intent intent,int startId){
        super.onStart(intent,startId);

        getContentResolver().registerContentObserver(ContactsContract.Contacts.CONTENT_URI,true , mObserver);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        getContentResolver().unregisterContentObserver(mObserver);
    }
}
