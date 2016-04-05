package rapidzz.com.sayit.activity;

/**
 * Created by rapidzz on 05-Mar-16.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import rapidzz.com.sayit.R;

/**
 * Created by rapidzz on 29-Jun-15.
 */
public class SplashActivity extends Activity {

  /*  *//** Duration of wait **//*
    private final int SPLASH_DISPLAY_LENGTH =1000;

    *//** Called when the activity is first created. *//*
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splash);

        *//* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*//*
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                *//* Create an Intent that will start the Menu-Activity. *//*
                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
*/

        public static int catId;
        @SuppressLint("NewApi")
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.splash);

            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(getResources()
                        .getColor(R.color.themecolor));
            }

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {

                    if (readFromSharedPref(getApplicationContext()) == 1) {

                        Intent i = new Intent(SplashActivity.this,
                                MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(i);
                    }else   if (readFromSharedPref(getApplicationContext()) == 2) {

                        Intent i = new Intent(SplashActivity.this,
                                HollywoodActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(i);
                    } else {
                        Intent i = new Intent(SplashActivity.this,
                                DecideActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(i);
                    }
                }
            }, 1000);
        }

        public int readFromSharedPref(Context con) {

            SharedPreferences sp = con.getSharedPreferences("rapidzz.com.sayit",
                    Context.MODE_PRIVATE);
            Log.d("share",sp.getInt("rapidzz.com.sayit.status", 0)+"" );
            return sp.getInt("rapidzz.com.sayit.status", 0);
        }
    }
