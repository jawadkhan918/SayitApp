package rapidzz.com.sayit.activity;

/**
 * Created by Talhazk on 25-Mar-16.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

//import com.rapidzz.sayinstyle.fragment.FragmentChangeActivity;

import rapidzz.com.sayit.R;

public class DecideActivity extends Activity {

    ImageButton hindiBtn;
    ImageButton englishBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decide);
//        getActionBar().hide();
        englishBtn = (ImageButton) findViewById(R.id.englishBtn);
        hindiBtn = (ImageButton) findViewById(R.id.hindiBtn);

        englishBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                saveInSharedPref(2);
                Intent i = new Intent(DecideActivity.this,
                        HollywoodActivity.class);


                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(i);

            }
        });
        hindiBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                saveInSharedPref(1);
               Intent i = new Intent(DecideActivity.this,
                        MainActivity.class);

                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(i);


            }
        });

    }

    public int readFromSharedPref(Context con) {

        SharedPreferences sp = con.getSharedPreferences("rapidzz.com.sayit",
                Context.MODE_PRIVATE);

        return sp.getInt("rapidzz.com.sayit", 0);
    }

    public void saveInSharedPref(int value) {
        SharedPreferences sp = getSharedPreferences("rapidzz.com.sayit",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor Ed = sp.edit();
        Ed.putInt("rapidzz.com.sayit.status", value);
        Ed.commit();

    }
}
