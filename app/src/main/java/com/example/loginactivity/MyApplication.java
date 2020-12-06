package com.example.loginactivity;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class MyApplication extends Application {

  //  private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    public void onCreate() {
        super.onCreate();

//        Realm.init(this);
//        RealmConfiguration config = new RealmConfiguration.Builder()
//                .name("elite.realm")                // user defined name
//                .schemaVersion(0)
//                .deleteRealmIfMigrationNeeded()
//                .build();
//        Realm.setDefaultConfiguration(config);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/montserrat_regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

       // mFirebaseAnalytics =FirebaseAnalytics.getInstance(this);
    }

//    public void logEvent(String ServiceName,String ServiceID,String ID){
//
//        Bundle bundle = new Bundle();
//        bundle.putString(ServiceName,ServiceID);
//        bundle.putString("User",ID);
//
//        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT,bundle);
//    }


}