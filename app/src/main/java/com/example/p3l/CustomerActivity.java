package com.example.p3l;

import static com.android.volley.Request.Method.GET;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.example.p3l.SharedPreferences.Entity.User;
import com.example.p3l.SharedPreferences.Preferences.UserPreferences;
import com.example.p3l.Volley.api.CustomerApi;
import com.example.p3l.Volley.models.CustomerProfil;
import com.example.p3l.Volley.models.CustomerProfilResponse;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class CustomerActivity extends AppCompatActivity {

    private LinearLayout layoutLoading;
    private RequestQueue queue;
    Button mobil, promo, transaksi, profil, logout;
    private UserPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        queue = Volley.newRequestQueue(CustomerActivity.this);
        preferences = new UserPreferences(CustomerActivity.this);

        layoutLoading = findViewById(R.id.layout_loading);
        mobil = findViewById(R.id.btn_mobil);
        promo = findViewById(R.id.btn_promo);
        transaksi = findViewById(R.id.btn_transaksi_customer);
        profil = findViewById(R.id.btn_profil_customer);
        logout = findViewById(R.id.btn_logout_customer);

        mobil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CustomerActivity.this, MobilActivity.class));
            }
        });

        promo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CustomerActivity.this, PromoActivity.class));
            }
        });

        transaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CustomerActivity.this, TransaksiCustomerActivity.class));
            }
        });

        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CustomerActivity.this, ProfilCustomerActivity.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CustomerActivity.this);
                builder.setMessage("Are you sure want to exit?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //  Keluar dari aplikasi
                                logoutProfil();
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();
            }
        });
    }

    private void logoutProfil() {
        setLoading(true);
        StringRequest stringRequest = new StringRequest(GET, CustomerApi.LOGOUT_URL, new Response.Listener<String>() {
            @Override public void onResponse(String response) {
                Gson gson = new Gson();
                /* Deserialiasai data dari response JSON dari API menjadi java object MahasiswaResponse menggunakan Gson */
                CustomerProfilResponse customerResponse = gson.fromJson(response, CustomerProfilResponse.class);

                setLoading(false);
                preferences.logout();
                checkLogin();

                Toast.makeText(CustomerActivity.this, customerResponse.getMessage(), Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setLoading(false);
                try {
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);

                    Toast.makeText(CustomerActivity.this, errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(CustomerActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void checkLogin() {
        /* this function will check if user login, akan memunculkan toast jika tidak redirect ke login activity */
        if(!preferences.checkLogin()) {
            Intent intent = new Intent(CustomerActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            Toast.makeText(CustomerActivity.this, "Berhasil Logout!", Toast.LENGTH_SHORT).show();
        }
    }
}