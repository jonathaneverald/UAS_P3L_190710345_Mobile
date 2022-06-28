package com.example.p3l;

import static com.android.volley.Request.Method.POST;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import com.example.p3l.Volley.api.DriverApi;
import com.example.p3l.Volley.models.DriverProfil;
import com.example.p3l.Volley.models.DriverProfilResponse;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class EditDriverActivity extends AppCompatActivity {

    private EditText alamat, email, noTelp, password;
    private RequestQueue queue;
    private LinearLayout layoutLoading;
    private UserPreferences preferences;
    private User user;
    private AutoCompleteTextView bahasa;

    private static final String[] BAHASA_LIST = new String[]{"Indonesia", "Inggris",
            "Indonesia dan Inggris"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_driver);

        queue = Volley.newRequestQueue(this);
        preferences = new UserPreferences(EditDriverActivity.this);
        user = preferences.getUserLogin();

        alamat = findViewById(R.id.ed_alamat_driver);
        email = findViewById(R.id.ed_email_driver);
        noTelp = findViewById(R.id.ed_noTelp_driver);
        password = findViewById(R.id.ed_password_driver);
        bahasa = findViewById(R.id.ed_bahasa);
        layoutLoading = findViewById(R.id.layout_loading);

        ArrayAdapter<String> adapterBahasa =
                new ArrayAdapter<>(this, R.layout.item_list, BAHASA_LIST);
        bahasa.setAdapter(adapterBahasa);

        Button btnCancel = findViewById(R.id.btn_cancel_driver);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button btnSave = findViewById(R.id.btn_save_driver);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditDriverActivity.this);
                builder.setMessage("Apakah kamu ingin mengubah data ini?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                updateDriver();
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

    private void updateDriver(){
        setLoading(true);

        String temp = "";
        if(!password.getEditableText().toString().isEmpty())
            temp = password.getEditableText().toString();

        DriverProfil driver2 = new DriverProfil(
                alamat.getEditableText().toString(),
                email.getEditableText().toString(),
                noTelp.getEditableText().toString(),
                bahasa.getText().toString(),
                temp
        );

        StringRequest stringRequest = new StringRequest(POST,
                DriverApi.UPDATE_URL + user.getId(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();

                DriverProfilResponse driverResponse2 =
                        gson.fromJson(response, DriverProfilResponse.class);

                Toast.makeText(EditDriverActivity.this,
                        driverResponse2.getMessage(), Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(EditDriverActivity.this,
                            errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(EditDriverActivity.this, e.getMessage(),
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
                String requestBody = gson.toJson(driver2);

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