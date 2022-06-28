package com.example.p3l;

import static com.android.volley.Request.Method.GET;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
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
import com.example.p3l.Volley.api.PegawaiApi;
import com.example.p3l.Volley.models.PegawaiResponse;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ManagerActivity extends AppCompatActivity {

    private LinearLayout layoutLoading;
    private RequestQueue queue;
    private UserPreferences preferences;
    private User user;
    private MaterialButton laporan_sewa_mobil, laporan_detail_pendapatan, laporan_top_driver, laporan_top_customer, laporan_performa_driver;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        queue = Volley.newRequestQueue(ManagerActivity.this);
        preferences = new UserPreferences(ManagerActivity.this);
        user = preferences.getUserLogin();

        laporan_sewa_mobil = findViewById(R.id.laporan_sewa_mobil);
        laporan_detail_pendapatan = findViewById(R.id.laporan_detail_pendapatan);
        laporan_top_driver = findViewById(R.id.laporan_top_driver);
        laporan_top_customer = findViewById(R.id.laporan_top_customer);
        laporan_performa_driver = findViewById(R.id.laporan_performa_driver);
        layoutLoading = findViewById(R.id.layout_loading);
        logout = findViewById(R.id.btn_logout_manager);

        laporan_sewa_mobil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManagerActivity.this, LaporanActivity.class);
                intent.putExtra("laporan","laporan_sewa_mobil");
                startActivity(intent);
            }
        });

        laporan_detail_pendapatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManagerActivity.this, LaporanActivity.class);
                intent.putExtra("laporan","laporan_detail_pendapatan");
                startActivity(intent);
            }
        });

        laporan_top_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManagerActivity.this, LaporanActivity.class);
                intent.putExtra("laporan","laporan_top_driver");
                startActivity(intent);
            }
        });

        laporan_top_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManagerActivity.this, LaporanActivity.class);
                intent.putExtra("laporan","laporan_top_customer");
                startActivity(intent);
            }
        });

        laporan_performa_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManagerActivity.this, LaporanActivity.class);
                intent.putExtra("laporan","laporan_performa_driver");
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ManagerActivity.this);
                builder.setMessage("Are you sure want to exit?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //  Keluar dari aplikasi
                                logout();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();
            }
        });
    }

    private void logout() {
        setLoading(true);
        StringRequest stringRequest = new StringRequest(GET, PegawaiApi.LOGOUT_URL, new Response.Listener<String>() {
            @Override public void onResponse(String response) {
                Gson gson = new Gson();
                /* Deserialiasai data dari response JSON dari API menjadi java object MahasiswaResponse menggunakan Gson */
                PegawaiResponse pegawaiResponse = gson.fromJson(response, PegawaiResponse.class);

                setLoading(false);
                preferences.logout();
                checkLogin();

                Toast.makeText(ManagerActivity.this, pegawaiResponse.getMessage(), Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setLoading(false);
                try {
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);

                    Toast.makeText(ManagerActivity.this, errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(ManagerActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void checkLogin() {
        /* this function will check if user login, akan memunculkan toast jika tidak redirect ke login activity */
        if(!preferences.checkLogin()) {
            Intent intent = new Intent(ManagerActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            Toast.makeText(ManagerActivity.this, "Berhasil Logout!", Toast.LENGTH_SHORT).show();
        }
    }

    private void setLoading(boolean isLoading) {
        if (isLoading) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            layoutLoading.setVisibility(View.VISIBLE);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            layoutLoading.setVisibility(View.INVISIBLE);
        }
    }
}