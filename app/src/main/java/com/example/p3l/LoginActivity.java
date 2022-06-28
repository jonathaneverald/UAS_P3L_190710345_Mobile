package com.example.p3l;

import static com.android.volley.Request.Method.POST;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.p3l.SharedPreferences.Preferences.UserPreferences;
import com.example.p3l.Volley.api.CustomerApi;
import com.example.p3l.Volley.api.DriverApi;
import com.example.p3l.Volley.api.PegawaiApi;
import com.example.p3l.Volley.models.Customer;
import com.example.p3l.Volley.models.CustomerResponse;
import com.example.p3l.Volley.models.Driver;
import com.example.p3l.Volley.models.DriverResponse;
import com.example.p3l.Volley.models.Login;
import com.example.p3l.Volley.models.Pegawai;
import com.example.p3l.Volley.models.PegawaiResponse;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private static final String[] ROLE_LIST = new String[]{"Driver", "Customer", "Manager"};

    TextView here;
    MaterialButton btnlogin;
    private TextInputLayout email, password;
    private AutoCompleteTextView edRole;
    private UserPreferences userPreferences;
    private RequestQueue queue;
    private LinearLayout layoutLoading;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userPreferences = new UserPreferences(LoginActivity.this);

        checkLogin();

        queue = Volley.newRequestQueue(this);
        btnlogin = findViewById(R.id.btnLogin);
        edRole = findViewById(R.id.ed_role);
        email = findViewById(R.id.inputLayoutEmail);
        password = findViewById(R.id.inputLayoutPassword);
        layoutLoading = findViewById(R.id.layout_loading);

        ArrayAdapter<String> adapterRole =
                new ArrayAdapter<>(this, R.layout.item_list, ROLE_LIST);
        edRole.setAdapter(adapterRole);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateForm()) {
                    login();
                }
            }
        });
    }

    private boolean validateForm() {
        /* Check email & password is empty or not */
        if(edRole.getText().toString().trim().isEmpty()) {
            Toast.makeText(LoginActivity.this, "Masuk Sebagai Masih Kosong", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(email.getEditText().getText().toString().trim().isEmpty() || password.getEditText().getText().toString().trim().isEmpty()) {
            Toast.makeText(LoginActivity.this, "Email Atau Password Kosong", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void login() {
        setLoading(true);
        Login login = new Login(
                email.getEditText().getText().toString(),
                password.getEditText().getText().toString()
        );

        if(edRole.getText().toString().trim().equals("Customer")) {
            StringRequest stringRequest = new StringRequest(POST, CustomerApi.LOGIN_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Gson gson = new Gson();
                            CustomerResponse customerResponse =
                                    gson.fromJson(response, CustomerResponse.class);

                            Toast.makeText(LoginActivity.this,
                                    customerResponse.getMessage(), Toast.LENGTH_SHORT).show();

                            Customer[] customer = customerResponse.getCustomer();

                            System.out.println(customer[0].getId_customer());
                            userPreferences.setLogin(customer[0].getId_customer(), "Customer");
                            setLoading(false);
                            if(userPreferences.checkLogin()) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    setLoading(false);
                    try {
                        String responseBody = new String(error.networkResponse.data,
                                StandardCharsets.UTF_8);
                        JSONObject errors = new JSONObject(responseBody);

                        Toast.makeText(LoginActivity.this,
                                errors.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(LoginActivity.this, e.getMessage(),
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
                    String requestBody = gson.toJson(login);

                    return requestBody.getBytes(StandardCharsets.UTF_8);
                }

                @Override
                public String getBodyContentType() {
                    return "application/json";
                }
            };
            queue.add(stringRequest);
        } else if (edRole.getText().toString().trim().equals("Manager")) {
            StringRequest stringRequest = new StringRequest(POST, PegawaiApi.LOGIN_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Gson gson = new Gson();
                            PegawaiResponse pegawaiResponse =
                                    gson.fromJson(response, PegawaiResponse.class);

                            Toast.makeText(LoginActivity.this,
                                    pegawaiResponse.getMessage(), Toast.LENGTH_SHORT).show();

                            Pegawai[] pegawai = pegawaiResponse.getPegawai();
                            setLoading(false);
                            if(!pegawai[0].getRole().getJabatan().equals("Manager")) {
                                Toast.makeText(LoginActivity.this,
                                        "Selain Manager, Pegawai lain tidak bisa Masuk!", Toast.LENGTH_SHORT).show();
                            } else {
                                userPreferences.setLogin(pegawai[0].getId_pegawai(), "Manager");
                                if(userPreferences.checkLogin()) {
                                    Intent intent = new Intent(LoginActivity.this, ManagerActivity.class);
                                    startActivity(intent);
                                }
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    setLoading(false);
                    try {
                        String responseBody = new String(error.networkResponse.data,
                                StandardCharsets.UTF_8);
                        JSONObject errors = new JSONObject(responseBody);

                        Toast.makeText(LoginActivity.this,
                                errors.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(LoginActivity.this, e.getMessage(),
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
                    String requestBody = gson.toJson(login);

                    return requestBody.getBytes(StandardCharsets.UTF_8);
                }

                @Override
                public String getBodyContentType() {
                    return "application/json";
                }
            };
            queue.add(stringRequest);
        } else if (edRole.getText().toString().trim().equals("Driver")) {
            StringRequest stringRequest = new StringRequest(POST, DriverApi.LOGIN_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Gson gson = new Gson();
                            DriverResponse driverResponse =
                                    gson.fromJson(response, DriverResponse.class);

                            Toast.makeText(LoginActivity.this,
                                    driverResponse.getMessage(), Toast.LENGTH_SHORT).show();

                            Driver[] driver = driverResponse.getDriver();
                            setLoading(false);
                            userPreferences.setLogin(driver[0].getId_driver(), "Driver");
                            if(userPreferences.checkLogin()) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    setLoading(false);
                    try {
                        String responseBody = new String(error.networkResponse.data,
                                StandardCharsets.UTF_8);
                        JSONObject errors = new JSONObject(responseBody);

                        Toast.makeText(LoginActivity.this,
                                errors.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(LoginActivity.this, e.getMessage(),
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
                    String requestBody = gson.toJson(login);

                    return requestBody.getBytes(StandardCharsets.UTF_8);
                }

                @Override
                public String getBodyContentType() {
                    return "application/json";
                }
            };
            queue.add(stringRequest);
        }
    }

    private void checkLogin() {
        if(userPreferences.checkLogin()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
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
