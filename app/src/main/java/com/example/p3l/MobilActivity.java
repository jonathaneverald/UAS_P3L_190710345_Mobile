package com.example.p3l;

import static com.android.volley.Request.Method.GET;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.p3l.Volley.adapters.MobilAdapter;
import com.example.p3l.Volley.api.MobilApi;
import com.example.p3l.Volley.models.MobilResponse;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MobilActivity extends AppCompatActivity {

    private MobilAdapter adapter;
    private LinearLayout layoutLoading;
    private RequestQueue queue;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobil);

        queue = Volley.newRequestQueue(MobilActivity.this);

        layoutLoading = findViewById(R.id.layout_loading);
        back = findViewById(R.id.btn_back1);

        RecyclerView rvMobil = findViewById(R.id.rv_mobil);
        adapter = new MobilAdapter(new ArrayList<>(), MobilActivity.this);
        rvMobil.setLayoutManager(new LinearLayoutManager(MobilActivity.this, LinearLayoutManager.VERTICAL, false));
        rvMobil.setAdapter(adapter);

        getAllMobil();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getAllMobil() {
        setLoading(true);
        StringRequest stringRequest = new StringRequest(GET, MobilApi.GET_ALL_URL, new Response.Listener<String>() {
            @Override public void onResponse(String response) {
                Gson gson = new Gson();
                /* Deserialiasai data dari response JSON dari API menjadi java object MahasiswaResponse menggunakan Gson */
                MobilResponse mobilResponse = gson.fromJson(response, MobilResponse.class);

                adapter.setMobilList(mobilResponse.getMobilList());
                setLoading(false);

                Toast.makeText(MobilActivity.this, mobilResponse.getMessage(), Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setLoading(false);
                try {
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);

                    Toast.makeText(MobilActivity.this, errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(MobilActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            // Menambahkan header pada request
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");

                return headers;
            }
        };

        // Menambahkan request ke request queue
        queue.add(stringRequest);
    }

    private void setLoading(boolean isLoading) {
        if (isLoading) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE); layoutLoading.setVisibility(View.VISIBLE);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            layoutLoading.setVisibility(View.GONE);
        }
    }
}