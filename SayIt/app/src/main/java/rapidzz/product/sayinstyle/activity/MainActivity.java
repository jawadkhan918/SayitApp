package rapidzz.product.sayinstyle.activity;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import rapidzz.product.sayinstyle.*;
import rapidzz.product.sayinstyle.app.Config;
import rapidzz.product.sayinstyle.fragment.InviteFragment;
import rapidzz.product.sayinstyle.model.Category;
import rapidzz.product.sayinstyle.model.CheckInternet;
import rapidzz.product.sayinstyle.utils.DatabaseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //  private br.liveo.model.HelpLiveo mHelpLiveo;
    // String loginURL="http://www.pakwedds.com/sayit/get_category.php";
    String data = "";
    String jsonResponse;
    Category item;

    public static List<Category> categoryItems = new ArrayList<Category>();
    RecyclerView recyclerView;
    RequestQueue requestQueue;
    MyRecyclerAdapter adapter;
    TextView output;
    LinearLayoutManager linearLayoutManager;
    private ProgressDialog pDialog;
    private AdView adView;
    TextView checkInternet;
    Button internetBtn;
    LinearLayout internetLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //      setContentView(R.layout.list_view);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(this);
        DatabaseHandler db = new DatabaseHandler(
                getApplicationContext());
        db.getAllFavorities();
        internetBtn = (Button) findViewById(R.id.retryBtn);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        internetLayout = (LinearLayout) findViewById(R.id.internetLayout);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

//        linearLayoutManager = new LinearLayoutManager(this);
  //      recyclerView.setLayoutManager(linearLayoutManager);
/*
        adapter = new MyRecyclerAdapter(categoryItems);
        recyclerView.setAdapter(adapter);
*/


        adapter = new MyRecyclerAdapter(categoryItems);
        recyclerView.setAdapter(adapter);

        saveInSharedPref(1);
            Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(this.getResources().getColor(R.color.themecolor));
            }

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
           drawer.clearFocus();
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

        checkInternet = (TextView) findViewById(R.id.checkInternet);
        if (!categoryItems.isEmpty()){

            checkInternet.setVisibility(View.GONE);
            internetBtn.setVisibility(View.GONE);
            internetLayout.setVisibility(View.GONE);

            recyclerView.setVisibility(View.VISIBLE);

        }
        else {
            if (CheckInternet.isNetConnected(getApplicationContext())) {
                pDialog = new ProgressDialog(MainActivity.this);
                pDialog.setMessage("Loading...");
                pDialog.setCancelable(false);

                recyclerView.setVisibility(View.VISIBLE);

                adView = new AdView(this);
                adView.setAdSize(AdSize.SMART_BANNER);
                adView.setAdUnitId("ca-app-pub-7714528612911729/2194456699");

                RelativeLayout layout = (RelativeLayout) findViewById(R.id.main_activity_layout);
                layout.addView(adView);

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) adView
                        .getLayoutParams();
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                adView.setLayoutParams(params);

                AdRequest adRequest = new AdRequest.Builder().build();

                adView.loadAd(adRequest);
                //    categoryItems.clear();
                if (categoryItems.isEmpty())
                    getCategoryList();
                output = (TextView) findViewById(R.id.category_name);
            } else {
                Toast.makeText(getApplicationContext(), "Check your Internet", Toast.LENGTH_SHORT).show();
                checkInternet.setVisibility(View.VISIBLE);
                internetBtn.setVisibility(View.VISIBLE);
                internetLayout.setVisibility(View.VISIBLE);
            }

        }
        }

    public void retry(View view) {

    if (!categoryItems.isEmpty()){

        checkInternet.setVisibility(View.GONE);
        internetBtn.setVisibility(View.GONE);
        internetLayout.setVisibility(View.GONE);

        recyclerView.setVisibility(View.VISIBLE);

    }
    else{   if (CheckInternet.isNetConnected(getApplicationContext())) {

            checkInternet.setVisibility(View.GONE);
            internetBtn.setVisibility(View.GONE);
            internetLayout.setVisibility(View.GONE);

            recyclerView.setVisibility(View.VISIBLE);
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);
            //  recyclerView.setVisibility(View.VISIBLE);

            adView = new AdView(this);
            adView.setAdSize(AdSize.SMART_BANNER);
            adView.setAdUnitId("ca-app-pub-7714528612911729/2194456699");

            RelativeLayout layout = (RelativeLayout) findViewById(R.id.main_activity_layout);
            layout.addView(adView);

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) adView
                    .getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            adView.setLayoutParams(params);

            AdRequest adRequest = new AdRequest.Builder().build();

            adView.loadAd(adRequest);
            //  categoryItems.clear();

            if (categoryItems.isEmpty())
                getCategoryList();
        /*    recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
            linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            adapter = new MyRecyclerAdapter(categoryItems);
            recyclerView.setAdapter(adapter);
        */
            output = (TextView) findViewById(R.id.category_name);



        }else {

            Toast.makeText(getApplicationContext(), "Check your Internet", Toast.LENGTH_SHORT).show();
        checkInternet.setVisibility(View.VISIBLE);
        internetBtn.setVisibility(View.VISIBLE);
        internetLayout.setVisibility(View.VISIBLE);

    }
    }
    }

    private void getCategoryList() {
        showProgressDialog();

        JsonArrayRequest req = new JsonArrayRequest(Config.CATEGORY_LIST,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("response.tosth", response.toString());
                        hideProgressDialog();

                        try {
                            // Parsing json array response
                            // loop through each json object


                            jsonResponse = "";
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject category = (JSONObject) response
                                        .get(i);

                                String  catStatus = category.getString("cat_status");;

                            if (catStatus.equals("hindi")||catStatus.equals("both")) {
                                int id = Integer.parseInt(category.getString("id"));
                                String name = category.getString("cat_title");
                                String image = category.getString("cat_backimg");
                                String logo = category.getString("cat_logo");
                                String catCountHindi = category.getString("cat_count_hindi");
                                ;
                                String catCountEnglish = category.getString("cat_count_english");
                                ;
                                String catUpcoming = category.getString("cat_upcoming");
                                ;

                                // String logo = person.getString("email");
                                Log.e("name is", name);
                                Log.e("id is", "" + id);
                                Log.e("hindi", catCountHindi);
                                Log.e("english", catCountEnglish);
                                //del this
                                item = new Category(id, image, catCountEnglish, catCountHindi, logo, catStatus, name, catUpcoming);
                                categoryItems.add(item);

                                //     Toast.makeText(getApplicationContext(), "name is " +item.getName()  , Toast.LENGTH_SHORT).show();
                                adapter.notifyDataSetChanged();
                            }
                            }

                            //   output.setText(categoryItems.getName());

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressDialog();
                checkInternet.setVisibility(View.VISIBLE);
                VolleyLog.d("Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Try Again!!", Toast.LENGTH_SHORT).show();
                checkInternet.setVisibility(View.VISIBLE);
                internetBtn.setVisibility(View.VISIBLE);
                internetLayout.setVisibility(View.VISIBLE);

            }
        });


        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        req.setRetryPolicy(policy);
        requestQueue.add(req);

    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
        Log.e("dialog is invoked", "true");

    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
        Log.e("dialog not invoked", "false");

    }


    private class MyRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        //    private static final int TYPE_HEADER = 0;
        private static final int TYPE_ITEM = 0;


        List listItems;

        public MyRecyclerAdapter(List listItems) {
            this.listItems = listItems;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_ITEM) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
                return new VHItem(v);

            }
            throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
        }

        private Category getItem(int position) {
            return categoryItems.get(position);
        }


        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            // Category currentItem = getItem(position);
            //  VHItem VHitem = (VHItem) holder;
            //    tempPath=currentItem.getPath();
            //   VHitem.output.setText(categoryItems.getName());
            //   VHitem.iv.setImageBitmap(camera.loadImageFromStorage(currentItem.getPath(),currentItem.getUserName()));
/*
            if (CategoriesList.get().getCategories().size() > 0) {
                String status = "";
                Log.e("LOAD", "Working");
                for (Categories cat : CategoriesList.get().getCategories()) {
                    status = cat.getStatus();
                    if (status.equals("both") || status.equals("hindi"))
                        mCategories.add(cat);
                }
            } else {
                Log.e("LOAD", "Load Cate");
                loadCategories();
            }
*/




            Category cat = categoryItems.get(position);

            Category currentItem = getItem(position);
            VHItem VHitem = (VHItem) holder;
            VHitem.categoryName.setText(currentItem.getCatTitle());
            VHitem.categoryName.setTextColor(Color.parseColor("#FFFFFF"));
//
            VHitem.categoryStatus.setText("Hindi");
            VHitem.categoryStatus.setTextColor(Color.parseColor("#FFFFFF"));

            VHitem.categoryAudios.setText(currentItem.getCatCountHindi());
            VHitem.categoryAudios.setTextColor(Color.parseColor("#FFFFFF"));
            VHitem.audiosText.setTextColor(Color.parseColor("#FFFFFF"));


            Picasso
                    .with(getApplicationContext())
                    .load(cat.getCatBackImg())
                    .fit() // will explain later
                    .placeholder(getApplicationContext().getResources().getDrawable(R.color.nliveo_black))
                    .error(getApplicationContext().getResources().getDrawable(R.color.nliveo_black))
                    .into(((VHItem) holder).image);

            Picasso
                    .with(getApplicationContext())
                    .load(cat.getCatLogo())
                    .fit() // will explain later
                    .placeholder(getApplicationContext().getResources().getDrawable(R.color.nliveo_black))
                    .error(getApplicationContext().getResources().getDrawable(R.color.nliveo_black))
                    .into(((VHItem) holder).logo);


        }

        //    need to override this method
        @Override
        public int getItemViewType(int position) {
            return TYPE_ITEM;
        }

        //increasing getItemcount to 1. This will be the row of header.
        @Override
        public int getItemCount() {
            return listItems.size();
        }

        class VHItem extends RecyclerView.ViewHolder {
            TextView categoryName;
            ImageView image;
            ImageView logo;
            TextView categoryAudios;
            TextView categoryStatus;

            TextView audiosText;

            public VHItem(View itemView) {
                super(itemView);

                this.categoryName = (TextView) itemView.findViewById(R.id.category_name);
                this.image = (ImageView) itemView.findViewById(R.id.image);
                this.logo = (ImageView) itemView.findViewById(R.id.logo_image);

                this.categoryAudios = (TextView) itemView.findViewById(R.id.availableAudios);

                this.audiosText = (TextView) itemView.findViewById(R.id.audiosText);

                this.categoryStatus = (TextView) itemView.findViewById(R.id.catStatus);
                itemView.setClickable(true);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int position = getAdapterPosition();
             //           Toast.makeText(v.getContext(), "clickable " + position, Toast.LENGTH_SHORT).show();

                        Intent intent;
                        intent = new Intent(getApplicationContext(), SpecificCategory.class);
                        intent.putExtra("position", position);
                        intent.putExtra("catNo", 1);
                        intent.putExtra("lang",categoryItems.get(position).getCatStatus());
                        startActivity(intent);


                    }
                });
            }
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id =0;
        id = item.getItemId();
        if (id == R.id.hollyMenu) {
            // Handle the camera action
            Intent i = new Intent(getApplicationContext(),HollywoodActivity.class);
            startActivity(i);
            finish();
           //Toast.makeText(getApplicationContext(), "categoires", Toast.LENGTH_SHORT).show();

        }else if (id == R.id.bollyMenu) {

            Intent i = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
            finish();
        }        else if (id == R.id.favoritesMenu) {

            Intent i = new Intent(getApplicationContext(),FavoritiesActivity.class);
            startActivity(i);

        } else if (id == R.id.shareMenu) {
            Intent i = new Intent(getApplicationContext(),InviteFragment.class);
            startActivity(i);

        }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        return true;

    }


    public void saveInSharedPref(int value) {
        SharedPreferences sp = this.getSharedPreferences(
                "rapidzz.product.sayinstyle", Context.MODE_PRIVATE);
        SharedPreferences.Editor Ed = sp.edit();
        Ed.putInt("rapidzz.product.sayinstyle.status", value);
        // Ed.putString(,Value);
        Ed.commit();

    }

}