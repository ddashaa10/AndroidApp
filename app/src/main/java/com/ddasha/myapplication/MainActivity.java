package com.ddasha.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ddasha.myapplication.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.CallableStatement;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {

    Button btnLogIn, btnSignUp;
    EditText email, password;

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference users;
    RelativeLayout root;

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_MyApplication);
        setContentView(R.layout.activity_authorization);

        //присваивание переменным элементы формы
        btnLogIn = findViewById(R.id.button3);
        btnSignUp = findViewById(R.id.signUp);
        email = findViewById(R.id.emailAuth);
        password = findViewById(R.id.passwordAuth);
        root = findViewById(R.id.root_element);

        //БД
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        //Убираем нижнюю панель
        View overlay = root;
        overlay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);

        //Проверка на авторизованного пользователя
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            Intent i = new Intent(MainActivity.this, Main.class);
            startActivity(i);
            finish();
        }

        //вызов методов при нажатии на кнопки
        btnLogIn.setOnClickListener(v -> authUser());
        btnSignUp.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Registration.class)));
    }

    private void authUser() {
        if(TextUtils.isEmpty(email.getText().toString())){
            Snackbar.make(root, "Введите почту", Snackbar.LENGTH_SHORT).show();
            return;
        }
        if(password.getText().toString().length()<5){
            Snackbar.make(root, "Введите пароль", Snackbar.LENGTH_SHORT).show();
            return;
        }
        auth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnSuccessListener(authResult -> {
            Intent intent = new Intent(MainActivity.this, Main.class);
            startActivity(intent);
            finish();
        }).addOnFailureListener(e -> Snackbar.make(root, "Ошибка авторизации. "+ e.getMessage(), Snackbar.LENGTH_SHORT).show());

    }
}
