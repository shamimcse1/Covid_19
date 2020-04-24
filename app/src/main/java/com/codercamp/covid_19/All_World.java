package com.codercamp.covid_19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
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

public class All_World extends AppCompatActivity {
    RequestQueue requestQueue;
    public TextView TotalCase, TotalDeath, TotalRecovered, TotalUnresolved, NewCase, NewDeath, ActiveCase, SeriouseCase, Rank;
    ProgressDialog progressDialog;
    AlertDialog.Builder builder;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__world);

        requestQueue = Volley.newRequestQueue(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading.....");
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
    public void getData() {


        String url = "https://api.thevirustracker.com/free-api?global=stats";

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

                    JSONArray jsonArray = response.getJSONArray("results");

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
                        String total_danger_rank = countrydata.getString("total_affected_countries");

                        Log.d("Tag", death);

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
                Toast.makeText(All_World.this, "No Internet found.", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });

        requestQueue.add(request);
    }


}
