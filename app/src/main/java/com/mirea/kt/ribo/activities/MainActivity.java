package com.mirea.kt.ribo.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.mirea.kt.ribo.handler.HTTPRunnable;
import com.mirea.kt.ribo.activities.MainPage;
import com.mirea.kt.ribo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("AuthPrefs", MODE_PRIVATE);

        if (isUserAuthenticated()) {
            Intent intent = new Intent(this, MainPage.class);
            startActivity(intent);
            finish();
            return;
        }

        setContentView(R.layout.activity_main);
        EditText editLogin = findViewById(R.id.login);
        EditText editPassword = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(view ->
        {
            String login = editLogin.getText().toString();
            String password = editPassword.getText().toString();
            if (!login.isEmpty() && !password.isEmpty()) {
                Authenticate(login, password);
            }
        });
    }

    public void Authenticate(String login, String password) {
        String server = "https://android-for-students.ru";
        String serverPath = "/coursework/login.php";
        HashMap<String,String> map = new HashMap<>();

        map.put("lgn", login);
        map.put("pwd", password);
        map.put("g", "RIBO-02-22");

        HTTPRunnable httpRunnable = new HTTPRunnable(server + serverPath, map);

        Thread th = new Thread(httpRunnable);
        th.start();

        try {
            th.join();
        }
        catch (Exception err) {
            Log.e("HTTPRunnable", "JSONException" + err);
        }
        finally {
            try {
                JSONObject res = new JSONObject(httpRunnable.getResponseBody());
                int result = res.getInt("result_code");
                if (result == 1) {
                    saveUserAuthentication();
                    Intent intent = new Intent(this, MainPage.class);
                    startActivity(intent);
                }

            } catch (JSONException err) {
                Log.e("HTTPRunnable", "JSONException" + err);;
            }
        }
    }

    private void saveUserAuthentication() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isAuthenticated", true);
        editor.apply();
    }

    private boolean isUserAuthenticated() {
        return sharedPreferences.getBoolean("isAuthenticated", false);
    }
}