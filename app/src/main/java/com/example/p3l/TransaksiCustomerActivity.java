package com.example.p3l;

import static com.android.volley.Request.Method.GET;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.p3l.SharedPreferences.Entity.User;
import com.example.p3l.SharedPreferences.Preferences.UserPreferences;
import com.example.p3l.Volley.adapters.TransaksiAdapter;
import com.example.p3l.Volley.api.TransaksiApi;
import com.example.p3l.Volley.models.TransaksiResponse;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TransaksiCustomerActivity extends AppCompatActivity {

    private TransaksiAdapter adapter;
    private LinearLayout layoutLoading;
    private RequestQueue queue;
    private UserPreferences preferences;
    private User user;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_customer);

        queue = Volley.newRequestQueue(TransaksiCustomerActivity.this);
        preferences = new UserPreferences(TransaksiCustomerActivity.this);
        user = preferences.getUserLogin();

        layoutLoading = findViewById(R.id.layout_loading);
        back = findViewById(R.id.btn_back3);

        RecyclerView rvTransaksi = findViewById(R.id.rv_transaksi_customer);
        adapter = new TransaksiAdapter(new ArrayList<>(), TransaksiCustomerActivity.this);
        rvTransaksi.setLayoutManager(new LinearLayoutManager(TransaksiCustomerActivity.this, LinearLayoutManager.VERTICAL, false));
        rvTransaksi.setAdapter(adapter);

        getAllTransaksi();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getAllTransaksi() {
        setLoading(true);
        StringRequest stringRequest = new StringRequest(GET, TransaksiApi.GET_BY_ID_URL + user.getId(), new Response.Listener<String>() {
            @Override public void onResponse(String response) {
                Gson gson = new Gson();
                /* Deserialiasai data dari response JSON dari API menjadi java object MahasiswaResponse menggunakan Gson */
                TransaksiResponse transaksiResponse = gson.fromJson(response, TransaksiResponse.class);

                adapter.setTransaksiList(transaksiResponse.getTransaksiList());
                setLoading(false);

                Toast.makeText(TransaksiCustomerActivity.this, transaksiResponse.getMessage(), Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setLoading(false);
                try {
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);

                    Toast.makeText(TransaksiCustomerActivity.this, errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(TransaksiCustomerActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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