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
import com.example.p3l.Volley.adapters.MobilAdapter;
import com.example.p3l.Volley.adapters.PromoAdapter;
import com.example.p3l.Volley.api.PromoApi;
import com.example.p3l.Volley.models.PromoResponse;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PromoActivity extends AppCompatActivity {

    private PromoAdapter adapter;
    private LinearLayout layoutLoading;
    private RequestQueue queue;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo);

        queue = Volley.newRequestQueue(PromoActivity.this);

        layoutLoading = findViewById(R.id.layout_loading);
        back = findViewById(R.id.btn_back2);

        RecyclerView rvPromo = findViewById(R.id.rv_promo);
        adapter = new PromoAdapter(new ArrayList<>(), PromoActivity.this);
        rvPromo.setLayoutManager(new LinearLayoutManager(PromoActivity.this, LinearLayoutManager.VERTICAL, false));
        rvPromo.setAdapter(adapter);

        getAllPromo();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getAllPromo() {
        setLoading(true);
        StringRequest stringRequest = new StringRequest(GET, PromoApi.GET_ALL_URL, new Response.Listener<String>() {
            @Override public void onResponse(String response) {
                Gson gson = new Gson();
                /* Deserialiasai data dari response JSON dari API menjadi java object MahasiswaResponse menggunakan Gson */
                PromoResponse promoResponse = gson.fromJson(response, PromoResponse.class);

                adapter.setPromoList(promoResponse.getPromoList());
                setLoading(false);

                Toast.makeText(PromoActivity.this, promoResponse.getMessage(), Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setLoading(false);
                try {
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);

                    Toast.makeText(PromoActivity.this, errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(PromoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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