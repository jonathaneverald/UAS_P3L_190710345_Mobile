package com.example.p3l;

import static com.android.volley.Request.Method.GET;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.p3l.SharedPreferences.Entity.User;
import com.example.p3l.SharedPreferences.Preferences.UserPreferences;
import com.example.p3l.Volley.api.DriverApi;
import com.example.p3l.Volley.models.Driver;
import com.example.p3l.Volley.models.DriverProfilResponse;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ProfilDriverActivity extends AppCompatActivity {

    private LinearLayout layoutLoading;
    private RequestQueue queue;
    private MaterialButton edit, back, status;
    private TextView nama, alamat, tanggal_lahir, jenis_kelamin, email, no_telp, bahasa, tarif_driver, rerata_rating, status_driver;
    private UserPreferences preferences;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_driver);

        queue = Volley.newRequestQueue(ProfilDriverActivity.this);
        preferences = new UserPreferences(ProfilDriverActivity.this);
        user = preferences.getUserLogin();

        layoutLoading = findViewById(R.id.layout_loading);
        nama = findViewById(R.id.nama_driver);
        alamat = findViewById(R.id.alamat_driver);
        tanggal_lahir = findViewById(R.id.tanggal_lahir_driver);
        jenis_kelamin = findViewById(R.id.jenis_kelamin_driver);
        email = findViewById(R.id.email_driver);
        no_telp = findViewById(R.id.no_telp_driver);
        bahasa = findViewById(R.id.bahasa_driver);
        tarif_driver = findViewById(R.id.tarif_driver);
        rerata_rating = findViewById(R.id.rerata_rating);
        status_driver = findViewById(R.id.status_driver);
        edit = findViewById(R.id.btnEditDriver);
        back = findViewById(R.id.btn_back6);
        status = findViewById(R.id.btnStatus);

        getProfil();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfilDriverActivity.this, EditDriverActivity.class));
            }
        });

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder2 = new AlertDialog.Builder(ProfilDriverActivity.this);
                builder2.setMessage("Apakah kamu ingin mengubah status ini?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //  Keluar dari aplikasi
                                ubahStatus();
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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getProfil();
    }

    private void getProfil() {
        setLoading(true);
        StringRequest stringRequest = new StringRequest(GET, DriverApi.GET_BY_ID_URL + user.getId(), new Response.Listener<String>() {
            @Override public void onResponse(String response) {
                Gson gson = new Gson();
                /* Deserialiasai data dari response JSON dari API menjadi java object MahasiswaResponse menggunakan Gson */
                DriverProfilResponse driverResponse = gson.fromJson(response, DriverProfilResponse.class);
                Driver driver = driverResponse.getDriver();

                nama.setText(driver.getNama_driver());
                alamat.setText(driver.getAlamat_driver());
                tanggal_lahir.setText(driver.getTanggal_lahir());
                jenis_kelamin.setText(driver.getJenis_kelamin());
                email.setText(driver.getEmail());
                no_telp.setText(driver.getNo_telp());
                bahasa.setText(driver.getBahasa());
                tarif_driver.setText("Rp "+String.format("%.2f",driver.getTarif_driver()));
                rerata_rating.setText(String.format("%.2f",driver.getRerata_rating()));
                status_driver.setText(driver.getStatus_driver());

                setLoading(false);

                Toast.makeText(ProfilDriverActivity.this, driverResponse.getMessage(), Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setLoading(false);
                try {
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);

                    Toast.makeText(ProfilDriverActivity.this, errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(ProfilDriverActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void ubahStatus() {
        setLoading(true);
        StringRequest stringRequest = new StringRequest(GET, DriverApi.UPDATE_STATUS_URL + + user.getId(), new Response.Listener<String>() {
            @Override public void onResponse(String response) {
                Gson gson = new Gson();
                /* Deserialiasai data dari response JSON dari API menjadi java object MahasiswaResponse menggunakan Gson */
                DriverProfilResponse driverResponse = gson.fromJson(response, DriverProfilResponse.class);

                setLoading(false);

                Toast.makeText(ProfilDriverActivity.this, driverResponse.getMessage(), Toast.LENGTH_LONG).show();
                getProfil();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setLoading(false);
                try {
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);

                    Toast.makeText(ProfilDriverActivity.this, errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(ProfilDriverActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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