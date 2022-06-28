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
import com.example.p3l.Volley.api.CustomerApi;
import com.example.p3l.Volley.models.Customer;
import com.example.p3l.Volley.models.CustomerProfilResponse;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ProfilCustomerActivity extends AppCompatActivity {

    private LinearLayout layoutLoading;
    private RequestQueue queue;
    private MaterialButton edit, back;
    private TextView nama, alamat, tanggal_lahir, jenis_kelamin, email, no_telp, tanda_pengenal, status_dokumen;
    private UserPreferences preferences;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_customer);

        queue = Volley.newRequestQueue(ProfilCustomerActivity.this);
        preferences = new UserPreferences(ProfilCustomerActivity.this);
        user = preferences.getUserLogin();

        layoutLoading = findViewById(R.id.layout_loading);
        nama = findViewById(R.id.nama);
        alamat = findViewById(R.id.alamat);
        tanggal_lahir = findViewById(R.id.tanggal_lahir);
        jenis_kelamin = findViewById(R.id.jenis_kelamin);
        email = findViewById(R.id.email);
        no_telp = findViewById(R.id.no_telp);
        tanda_pengenal = findViewById(R.id.tanda_pengenal);
        status_dokumen = findViewById(R.id.status_dokumen);
        edit = findViewById(R.id.btnEdit);
        back = findViewById(R.id.btn_back4);

        getProfil();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfilCustomerActivity.this, EditCustomerActivity.class));
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
        StringRequest stringRequest = new StringRequest(GET, CustomerApi.GET_BY_ID_URL + user.getId(), new Response.Listener<String>() {
            @Override public void onResponse(String response) {
                Gson gson = new Gson();
                /* Deserialiasai data dari response JSON dari API menjadi java object MahasiswaResponse menggunakan Gson */
                CustomerProfilResponse customerResponse = gson.fromJson(response, CustomerProfilResponse.class);
                Customer customer = customerResponse.getCustomer();

                nama.setText(customer.getNama_customer());
                alamat.setText(customer.getAlamat_customer());
                tanggal_lahir.setText(customer.getTanggal_lahir());
                jenis_kelamin.setText(customer.getJenis_kelamin());
                email.setText(customer.getEmail());
                no_telp.setText(customer.getNo_telp());
                tanda_pengenal.setText(customer.getTanda_pengenal());
                status_dokumen.setText(customer.getStatus_dokumen());

                setLoading(false);

                Toast.makeText(ProfilCustomerActivity.this, customerResponse.getMessage(), Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setLoading(false);
                try {
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);

                    Toast.makeText(ProfilCustomerActivity.this, errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(ProfilCustomerActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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