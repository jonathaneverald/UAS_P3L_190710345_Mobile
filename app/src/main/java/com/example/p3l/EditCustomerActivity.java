package com.example.p3l;

import static com.android.volley.Request.Method.POST;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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

public class EditCustomerActivity extends AppCompatActivity {

    private EditText alamat, email, noTelp, password;
    private RequestQueue queue;
    private LinearLayout layoutLoading;
    private UserPreferences preferences;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_customer);

        queue = Volley.newRequestQueue(this);
        preferences = new UserPreferences(EditCustomerActivity.this);
        user = preferences.getUserLogin();

        alamat = findViewById(R.id.ed_alamat);
        email = findViewById(R.id.ed_email);
        noTelp = findViewById(R.id.ed_noTelp);
        password = findViewById(R.id.ed_password);
        layoutLoading = findViewById(R.id.layout_loading);

        Button btnCancel = findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditCustomerActivity.this);
                builder.setMessage("Apakah kamu ingin mengubah data ini?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                updateCustomer();
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

    private void updateCustomer(){
        setLoading(true);

        String temp = "";
        if(!password.getEditableText().toString().isEmpty())
            temp = password.getEditableText().toString();

        CustomerProfil customer2 = new CustomerProfil(
                alamat.getEditableText().toString(),
                email.getEditableText().toString(),
                noTelp.getEditableText().toString(),
                temp
        );

        StringRequest stringRequest = new StringRequest(POST,
                CustomerApi.UPDATE_URL + + user.getId(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();

                CustomerProfilResponse customerResponse =
                        gson.fromJson(response, CustomerProfilResponse.class);

                Toast.makeText(EditCustomerActivity.this,
                        customerResponse.getMessage(), Toast.LENGTH_SHORT).show();

                finish();
                setLoading(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setLoading(false);

                try {
                    String responseBody = new String(error.networkResponse.data,
                            StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);
                    Toast.makeText(EditCustomerActivity.this,
                            errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(EditCustomerActivity.this, e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");

                return headers;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                Gson gson = new Gson();
                 /* Serialisasi data dari java object MahasiswaResponse
                 menjadi JSON string menggunakan Gson */
                String requestBody = gson.toJson(customer2);

                return requestBody.getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public String getBodyContentType() {

                return "application/json";
            }
        };
        queue.add(stringRequest);
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