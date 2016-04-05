package rapidzz.com.sayit.activity;

/**
 * Created by rapidzz on 29-Mar-16.
 */

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import rapidzz.com.sayit.R;
import rapidzz.com.sayit.app.Config;
import rapidzz.com.sayit.httpcontrol.CopyFile;
import rapidzz.com.sayit.model.AppChooser;
import rapidzz.com.sayit.model.Dialogues;
import rapidzz.com.sayit.model.DialoguesList;
import rapidzz.com.sayit.model.CheckInternet;
import rapidzz.com.sayit.model.FavoritiesList;
import rapidzz.com.sayit.utils.DatabaseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpecificCategory extends ListActivity {


    RequestQueue requestQueue;

    private static final int SHARE_TO_MESSENGER_REQUEST_CODE = 1;
    // ArrayList to show data in Listview
//    private static List<Dialogues> mDialogueList;
    private List<Dialogues> dialogues;
    public static int count = 0;
    // Graphics Button
    private ImageView playBtn;
    private ImageView fvrtBtn;
    private ImageView catIcon;
    private TextView mTitle;
    //private TextView mAudios;
    //private TextView mStatus;
    private ImageButton mBackBtn;
    private ImageView shareBtn;
    private TextView dialoguesTitle;
    private TextView dialoguesNo;

    private View progressDialog;

    //  ImageLoader imageLoader;

    View shareView;
    String shareName;
    // MediaPlayer
    private MediaPlayer mPlayer = new MediaPlayer();
    private ProgressDialog pDialog;

    // Listview

    // Flags to take Decisions
    private Boolean isPlay = true;
    private boolean playFlag = false;

    // Row Positions
    private int pos;
    private int latestPos = -1;

    // Row view
    private View latestView;
    private View view;

    // Audio Play Pos
    private static int playPos = -1;

    // Listview Adapter
    private JobListAdapter adapter;

    // Layout for Adds
    private LinearLayout mFvrtLayout;
    private String catLang;
    TextView catName;
    int catId;
    String catImage;
    String catTitle;
    String categoryName;
    int position;
    ImageView imageExtra;
    private AdView adView;
    private AppCompatDelegate delegate;
    TextView checkInternet;
    LinearLayout linear;
    String dialoguesUrl;
    int catNo;
    String catIddialogue;
    /// / Ads Variable

    // facebook

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_specific_category);


        mBackBtn = (ImageButton) findViewById(R.id.backBtnspecific);

        mBackBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });


        Intent i = getIntent();
        position = i.getExtras().getInt("position");
        catLang = i.getExtras().getString("lang");
        catNo = i.getExtras().getInt("catNo");
        Log.d("lang is", position + catLang);
        requestQueue = Volley.newRequestQueue(this);
        if (catNo==1) {
              Toast.makeText(getApplicationContext(),"this bollywood "+MainActivity.categoryItems.get(position).getId()+"  "+catLang,Toast.LENGTH_SHORT).show();
        }else {
              Toast.makeText(getApplicationContext(),"this holly "+HollywoodActivity.categoryItems.get(position).getId()+"  "+catLang,Toast.LENGTH_SHORT).show();

        }
        adView = new AdView(this);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId("ca-app-pub-7714528612911729/4846335496");


        RelativeLayout layout = (RelativeLayout) findViewById(R.id.specific_layout);
        layout.addView(adView);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) adView
                .getLayoutParams();
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        adView.setLayoutParams(params);

        linear = (LinearLayout) findViewById(R.id.linear_layout);


        AdRequest adRequest = new AdRequest.Builder().build();
        //  AdRequest.Builder.addTestDevice("79176A4D625C750A744A6477BF2E301D");
        adView.loadAd(adRequest);
      //  catName = (TextView) findViewById(R.id.catName);
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources()
                    .getColor(R.color.themecolor));
        }
  //      catName.setText(MainActivity.categoryItems.get(position).getCatTitle());
if (catNo==1 ) {
    catId = MainActivity.categoryItems.get(position).getId();
    categoryName = MainActivity.categoryItems.get(position).getCatTitle();
}else {
    catId = HollywoodActivity.categoryItems.get(position).getId();
    categoryName = HollywoodActivity.categoryItems.get(position).getCatTitle();
}

        if (catNo==1 && catLang.equals("hindi")) {
            dialoguesUrl = "http://www.pakwedds.com/sayit/dialogues.php?catid=" + catId + "&lang=hindi";
        }else if (catNo==2 && catLang.equals("english")) {
            dialoguesUrl = "http://www.pakwedds.com/sayit/dialogues.php?catid=" + catId + "&lang=english";
        }else if (catNo==1 && catLang.equals("both")) {
            dialoguesUrl = "http://www.pakwedds.com/sayit/dialogues.php?catid=" + catId + "&lang=hindi";
        }else if (catNo==2 && catLang.equals("both")) {
            dialoguesUrl = "http://www.pakwedds.com/sayit/dialogues.php?catid=" + catId + "&lang=english";
        }

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);


        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver,
                new IntentFilter("playAudio"));
        mTitle = (TextView) findViewById(R.id.list_item_userName);
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        dialogues = DialoguesList.get().getDialogues();
        checkInternet = (TextView) findViewById(R.id.checkInternet);

        if (CheckInternet.isNetConnected(getApplicationContext())) {


            adapter = new JobListAdapter(dialogues);
            setListAdapter(adapter);
            makeStringReq();
        } else {
            //   Toast.makeText(getApplicationContext(), "Check your Internet", Toast.LENGTH_SHORT).show();
            checkInternet.setVisibility(View.VISIBLE);
            linear.setVisibility(View.GONE);
        }

    }


    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
        dialogues.clear();
        Log.e("dialog is invoked", "true");

    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
        Log.e("dialog not invoked", "false");

    }


    private void makeStringReq() {
        showProgressDialog();

        StringRequest strReq = new StringRequest(Request.Method.GET,
                dialoguesUrl, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("response", response.toString());
                hideProgressDialog();
                dialogues.clear();
                parseData(response);


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error", "Error: " + error.getMessage());
                Toast.makeText(SpecificCategory.this, "Try again!! ", Toast.LENGTH_SHORT).show();
                hideProgressDialog();

            }
        }) {


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
           //     params.put(Config.CAT_ID, "" + catId);
             //   params.put("lang", catLang);

                return params;
            }

        };
        ;

        // Adding request to request queue
        // AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(strReq);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub

//        Toast.makeText(getApplicationContext(),"this called",Toast.LENGTH_SHORT).show();

        Log.d("a", "called");

        String filePath = getDir("audio", Context.MODE_PRIVATE).toString()
                + "/"
                + DialoguesList.get().getDialogues().get(position).getLink()
                + ".aac";
        File file = new File(filePath);
        if (file.exists()) {

            if (view == null)
                view = v;
            playFlag = false;
            progressDialog = view.findViewById(R.id.progress_row);
            playBtn = (ImageView) view.findViewById(R.id.playBtn);

            playBtn.setVisibility(View.VISIBLE);
            progressDialog.setVisibility(View.GONE);
            latestPos = -1;
            playAudio(v, position);
        } else {
            if (CheckInternet.isNetConnected(getApplicationContext())) {
                playFlag = true;
                if (view == null)
                    view = v;
                progressDialog = view.findViewById(R.id.progress_row);
                playBtn = (ImageView) view.findViewById(R.id.playBtn);

                playBtn.setVisibility(View.VISIBLE);
                progressDialog.setVisibility(View.GONE);

                latestView = view = v;
                latestPos = position;
                progressDialog = v.findViewById(R.id.progress_row);
                playBtn = (ImageView) v.findViewById(R.id.playBtn);

                playBtn.setVisibility(View.INVISIBLE);
                progressDialog.setVisibility(View.VISIBLE);
                String arg[] = new String[2];

                arg[1] = DialoguesList.get().getDialogues().get(position)
                        .getLink();
                arg[0] = "http://www.pakwedds.com/sayit/sound/"
                        + DialoguesList.get().getDialogues().get(position)
                        .getLink() + ".aac";
                new rapidzz.com.sayit.httpcontrol.HttpAudio(this, "")
                        .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, arg);

            } else {
                Toast.makeText(this, "Check your internet", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    public void Test(View view) {
        Log.d("this is test", dialogues.get(position).getDialogue());
    }

    private class JobListAdapter extends ArrayAdapter<Dialogues> {

        public JobListAdapter(List<Dialogues> members) {
            super(getApplicationContext(), 0, members);
        }

        @SuppressLint("InflateParams")
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(
                        R.layout.list_item_category, null);
            }

            // pos = position;
            Dialogues j = getItem(position);

            dialoguesTitle = (TextView) convertView
                    .findViewById(R.id.list_item_userName);
            dialoguesNo = (TextView) convertView
                    .findViewById(R.id.dialogueNo);

            String mDial = j.getDialogue().substring(0,
                    Math.min(j.getDialogue().length(), 20))
                    + " ...";
           // String dialogueNoText = categoryName;

            dialoguesTitle.setText(mDial);
            dialoguesNo.setText(categoryName);

            mFvrtLayout = (LinearLayout) convertView
                    .findViewById(R.id.fvrtBtnListener);

            fvrtBtn = (ImageView) convertView.findViewById(R.id.fvrtBtn);

            ImageView playBtn = (ImageView) convertView
                    .findViewById(R.id.playBtn);

            if (playPos != position)
                playBtn.setBackgroundResource(R.drawable.button_play);
            else {
                playBtn.setBackgroundResource(R.drawable.button_stop);
            }

            // adf
            progressDialog = convertView.findViewById(R.id.progress_row);

            if (latestPos != position) {
                progressDialog.setVisibility(View.GONE);
                playBtn.setVisibility(View.VISIBLE);

            } else {
                progressDialog.setVisibility(View.VISIBLE);
                playBtn.setVisibility(View.INVISIBLE);
            }


            if (FavoritiesList.get().getFvrt(
                    (dialogues.get(position).getId())))
                fvrtBtn.setBackgroundResource(R.drawable.star_filled);
            else {
                fvrtBtn.setBackgroundResource(R.drawable.star_unfilled);
            }


            // Favourite Button Click Listener
            mFvrtLayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    int posi = getListView().getPositionForView(v);
                    DatabaseHandler db = new DatabaseHandler(
                            getApplicationContext());
                    Boolean check = false;
                    if (FavoritiesList.get().getFavorities().size() > 0) {
                        check = FavoritiesList.get().getFvrt(
                                (dialogues.get(posi).getId()));
                        ;
                    }
                    if (!check) {

                        db.addFavourite(dialogues.get(posi));
                        fvrtBtn = (ImageView) v.findViewById(R.id.fvrtBtn);
                        fvrtBtn.setBackgroundResource(R.drawable.star_filled);

                    } else {

                        fvrtBtn = (ImageView) v.findViewById(R.id.fvrtBtn);
                        fvrtBtn.setBackgroundResource(R.drawable.star_unfilled);
                        db.removeFvrt(dialogues.get(posi).getId());
                    }

                }
            });

            shareBtn = (ImageView) convertView.findViewById(R.id.shareBtn);
            shareBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    int posi = getListView().getPositionForView(v);

                    String fName = getDir("audio", Context.MODE_PRIVATE)
                            .getAbsolutePath()
                            + "/"
                            + DialoguesList.get().getDialogues().get(posi)
                            .getLink() + ".aac";

                    File file = new File(fName);
                    shareName = fName;
                    if (file.exists())
                        onClickWhatsApp(v, fName);
                    else {
                        if (CheckInternet.isNetConnected(getApplicationContext())) {
                            String arg[] = new String[3];
                            arg[2] = "share";
                            arg[1] = DialoguesList.get().getDialogues()
                                    .get(posi).getLink();

                            arg[0] = "http://www.pakwedds.com/sayit/sound/"
                                    + DialoguesList.get().getDialogues().get(posi)
                                    .getLink() + ".aac";
                            new rapidzz.com.sayit.httpcontrol.HttpAudio(
                                    SpecificCategory.this, "share")
                                    .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, arg);
                            shareView = v;

                            // onClickWhatsApp(v, fName);
                        } else
                            Toast.makeText(getApplicationContext(),
                                    "Check your Internet", Toast.LENGTH_SHORT)
                                    .show();
                    }
                }
            });

            return convertView;

        }

        @Override
        public void notifyDataSetChanged() {
            // TODO Auto-generated method stub
            super.notifyDataSetChanged();
        }

    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        if (mPlayer != null) {
            if (mPlayer.isPlaying()) {
                mPlayer.stop();
                adapter.notifyDataSetChanged();
                mPlayer.reset();
                playPos = -1;
                isPlay = true;
            }
        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if (mPlayer != null) {
            if (mPlayer.isPlaying()) {
                mPlayer.stop();
                adapter.notifyDataSetChanged();
                mPlayer.reset();
                playPos = -1;
                isPlay = true;
            }
        }
    }

    public void playAudio(View v, int position) {

        String filePath = getDir("audio", Context.MODE_PRIVATE).toString()
                + "/"
                + DialoguesList.get().getDialogues().get(position).getLink()
                + ".aac";
        File file = new File(filePath);
        adapter.notifyDataSetChanged();
        if (file.exists()) {
            if (isPlay) {
                isPlay = !isPlay;
                playPos = pos = position;
                view = v;
                playBtn = (ImageView) view.findViewById(R.id.playBtn);
                playBtn.setBackgroundResource(R.drawable.button_stop);
                try {
                    String fName = getDir("audio", Context.MODE_PRIVATE)
                            .getAbsolutePath()
                            + "/"
                            + DialoguesList.get().getDialogues().get(position)
                            .getLink() + ".aac";
                    mPlayer.setDataSource(this, Uri.parse(fName));
                    mPlayer.prepareAsync();
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (SecurityException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else if (pos == getListView().getPositionForView(v)) {
                if (isPlay) {
                    playBtn = (ImageView) v.findViewById(R.id.playBtn);
                    playBtn.setBackgroundResource(R.drawable.button_stop);
                } else {
                    playBtn = (ImageView) v.findViewById(R.id.playBtn);
                    playBtn.setBackgroundResource(R.drawable.button_play);
                    if (mPlayer != null) {
                        // mPlayer.stop();
                        if (mPlayer.isPlaying()) {
                            mPlayer.stop();
                            mPlayer.reset();
                            playPos = -1;

                        }
                    }
                }
                isPlay = !isPlay;
                // pos = position;
            } else {

                // Log.e("PLAY", );
                if (mPlayer != null) {
                    if (mPlayer.isPlaying()) {
                        mPlayer.stop();
                        mPlayer.reset();
                        playPos = -1;
                        adapter.notifyDataSetChanged();
                        isPlay = true;

                    }
                    pos = position;

                    if (isPlay) {
                        playPos = position;
                        try {
                            String fName = getDir("audio", Context.MODE_PRIVATE)
                                    .getAbsolutePath()
                                    + "/"
                                    + DialoguesList.get().getDialogues()
                                    .get(position).getLink() + ".aac";
                            mPlayer.setDataSource(this, Uri.parse(fName));
                            mPlayer.prepareAsync();
                        } catch (IllegalArgumentException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (SecurityException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IllegalStateException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        // Log.e("Button", " Media Player");
                        playBtn = (ImageView) v.findViewById(R.id.playBtn);
                        playBtn.setBackgroundResource(R.drawable.button_stop);
                        isPlay = !isPlay;
                    }

                }

            }
        }

        mPlayer.setOnCompletionListener(new OnCompletionListener() {

            public void onCompletion(MediaPlayer mp) {
                playPos = -1;
                mPlayer.reset();
                isPlay = !isPlay;
                adapter.notifyDataSetChanged();
            }
        });

        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                mPlayer.start();
            }
        });

        latestPos = -1;

    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getStringExtra("action");
            if (action.equals("play")) {
                if (latestView != null) {
                    progressDialog = latestView.findViewById(R.id.progress_row);
                    playBtn = (ImageView) latestView.findViewById(R.id.playBtn);
                    progressDialog.setVisibility(View.GONE);
                    playBtn.setVisibility(View.VISIBLE);
                }

                // if (!mPlayer.isPlaying())
                if (playFlag && latestPos >= 0)

                    playAudio(latestView, latestPos);
                // latestPos = -1;
            } else if (action.equals("share")) {
                onClickWhatsApp(shareView, shareName);
            }
        }
    };

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
    }
//for whatsapp

    public void onClickWhatsApp(View view, String fName) {

        PackageManager pm = getPackageManager();
        String completePath = new CopyFile().destinationPath(fName);
        File file = new File(completePath);
        Uri uri = Uri.fromFile(file);
        String mimeType = "audio/mpeg";

        Intent intent = new Intent(Intent.ACTION_SEND);
        List<String> whitelist = Arrays.asList("com.whatsapp",
                "com.facebook.orca", "com.viber.voip", "tencent.mm");
        intent.setType(mimeType);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        // intent.putExtra(EXTRA_PROTOCOL_VERSION, PROTOCOL_VERSION);
        // intent.putExtra(EXTRA_APP_ID, YOUR_APP_ID);
        intent = AppChooser.create(pm, intent, "Share", whitelist);
        this.startActivityForResult(intent, SHARE_TO_MESSENGER_REQUEST_CODE);

    }

    private void parseData(String response) {
    Log.d("response is", response);
        int status = -1;
        JSONObject obj = null;
        JSONArray array = null;
        try {
            obj = new JSONObject(response);
            status = obj.getInt(Config.API_STATUS);

            if (status == 1) {

                array = obj.getJSONArray(Config.JASON_ARR);
            } else {
                Toast.makeText(SpecificCategory.this, "Try Again.", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (status == 1) {
            for (int i = 0; i < array.length(); i++) {
                //  Restaurant superHero = new SuperHeroes();
                JSONObject json = null;


                try {
                    json = array.getJSONObject(i);

                    int id = -1;
                    String dialogue = "";
                    String link = "";

                    id = Integer.parseInt(json.getString(Config.DIALOGUE_ID));

                    dialogue = json.getString(Config.DIALOGUE_TITLE);

                    link = json.getString(Config.DIALOGUE_AUDIO);

           /*     String[] parts = splitter.split("Ayat");
                      String part1 = parts[0]; // 004
                     String part2 = parts[1]; // 034556
*/
/*

                ayatTitle = splitter;
                ayatNo = "Ayat " ;

*/
        if (catNo==1) {
             catIddialogue = MainActivity.categoryItems.get(position).getCatTitle();
        }else{
            catIddialogue = HollywoodActivity.categoryItems.get(position).getCatTitle();

        }

                    Log.d("this will show", catIddialogue);
                    Dialogues dialogueList = new Dialogues(id, dialogue, link,catIddialogue);
                    dialogues.add(dialogueList);


                } catch (Exception e) {
                    e.printStackTrace();
                }



        }
        adapter.notifyDataSetChanged();

        //Finally initializing our adapter
        //    adapter = new OrderHistoryAdapter(listOrderHistory, getActivity());

        //Adding adapter to recyclerview
      /*  recyclerView.setAdapter(adapter);
        adapter = new MyRecyclerAdapter(ayats);
        recyclerView.setAdapter(adapter);
      */

        adapter = new JobListAdapter(dialogues);
        setListAdapter(adapter);

    }
}




}