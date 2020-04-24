package com.codercamp.covid_19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RequestQueue requestQueue;
    public TextView TotalCase, TotalDeath, TotalRecovered, TotalUnresolved, NewCase, NewDeath, ActiveCase, SeriouseCase, Rank;
    ProgressDialog progressDialog;
    AlertDialog.Builder builder;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading.....");
        //progressDialog.getLayoutInflater().inflate(R.layout.test,null,false);
        progressDialog.setCanceledOnTouchOutside(false);
        getData();


        swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                    getData();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.country:
                //Toast.makeText(getApplicationContext(),"Item 1 Selected",Toast.LENGTH_LONG).show();
                startActivity(new Intent(MainActivity.this,All_World.class));
                return true;
            case R.id.profile:
                startActivity(new Intent(MainActivity.this,Profile.class));
                //Toast.makeText(getApplicationContext(),"Item 2 Selected",Toast.LENGTH_LONG).show();
                return true;

            case R.id.covid:
                startActivity(new Intent(MainActivity.this,BdInfo.class));
                //Toast.makeText(getApplicationContext(),"Item 3 Selected",Toast.LENGTH_LONG).show();
                return true;
            case R.id.item3:
                startActivity(new Intent(MainActivity.this,LivaData.class));
                //Toast.makeText(getApplicationContext(),"Item 3 Selected",Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to close this application ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.setTitle("Exit");
        alert.setIcon(R.drawable.ic_warning_black_24dp);
        alert.show();
    }

    public void getData() {


        String url = "https://api.thevirustracker.com/free-api?countryTotal=BD";

        progressDialog.show();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                TotalCase = findViewById(R.id.TotalCaseID);
                TotalDeath = findViewById(R.id.deth);
                TotalRecovered = findViewById(R.id.TotalRecovId);
                TotalUnresolved = findViewById(R.id.TotalUnSolID);
                NewCase = findViewById(R.id.NewCaseID);
                NewDeath = findViewById(R.id.newDeathID);
                ActiveCase = findViewById(R.id.ActiveCaseID);
                SeriouseCase = findViewById(R.id.SeriouseCaseID);
                Rank = findViewById(R.id.RankID);


                try {

                    JSONArray jsonArray = response.getJSONArray("countrydata");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject countrydata = jsonArray.getJSONObject(i);

                        String total_cases = countrydata.getString("total_cases");
                        String death = countrydata.getString("total_deaths");
                        String total_recovered = countrydata.getString("total_recovered");
                        String total_unresolved = countrydata.getString("total_unresolved");
                        String total_new_cases_today = countrydata.getString("total_new_cases_today");
                        String total_new_deaths_today = countrydata.getString("total_new_deaths_today");
                        String total_active_cases = countrydata.getString("total_active_cases");
                        String total_serious_cases = countrydata.getString("total_serious_cases");
                        String total_danger_rank = countrydata.getString("total_danger_rank");

                        Log.d("Tag", death);
                        //Toast.makeText(MainActivity.this, "hello"+death, Toast.LENGTH_SHORT).show();

                        TotalCase.setText(total_cases);
                        TotalDeath.setText(death);
                        TotalRecovered.setText(total_recovered);
                        TotalUnresolved.setText(total_unresolved);
                        NewCase.setText(total_new_cases_today);
                        NewDeath.setText(total_new_deaths_today);
                        ActiveCase.setText(total_active_cases);
                        SeriouseCase.setText(total_serious_cases);
                        Rank.setText(total_danger_rank);




                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(MainActivity.this, "No Internet found.", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });

        requestQueue.add(request);

    }

    public void info(View view) {
        startActivity(new Intent(MainActivity.this,Info.class));
    }

}