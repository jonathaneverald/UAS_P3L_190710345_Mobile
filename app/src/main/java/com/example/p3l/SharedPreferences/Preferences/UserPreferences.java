package com.example.p3l.SharedPreferences.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.p3l.SharedPreferences.Entity.User;

public class UserPreferences {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    /* Mendefinisikan key */
    public static final String IS_LOGIN = "isLogin";
    public static final String ID = "id";
    public static final String KEY_ROLE = "role";

    public UserPreferences(Context context) {
        this.context = context;
        /* penamaan bebas namun disini digunakan "userPreferences" */
        sharedPreferences = context.getSharedPreferences("userPreferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setLogin(long id, String role) {
        /*Menyumpan data lagin ke sharedPreferences dengan key dan value */
        editor.putBoolean(IS_LOGIN, true);
        editor.putLong(ID, id);
        editor.putString(KEY_ROLE, role);

        /* Jangan lupa commit karena kalo hanya set editornya saja dan tidak commit akan sia-sia */
        editor.commit();
    }

    public User getUserLogin() {
        /* Mengembalikan object User untuk menampilkan data user jika user sudah login */
        String role;
        long id;

        id = sharedPreferences.getLong(ID, 0);
        role = sharedPreferences.getString(KEY_ROLE, null);

        return new User(id, role);
    }

    public boolean checkLogin() {
        /* Mengembalikan nilai is_login, jika sudah lgoin otomatis nilai true. Jika tidak akan return false */
        return sharedPreferences.getBoolean(IS_LOGIN,false);
    }

    public void logout() {
        /* Melakukan clear data yang ada pada sharedPreferences, jangan lupa di commit agar data terubah */
        editor.clear();
        editor.commit();
    }
}
