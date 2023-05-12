package com.ddasha.myapplication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ddasha.myapplication.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class Account extends AppCompatActivity {

    ImageView btnHome;
    TextView loginAccount, emailAccount, letter;
    Button logout, delete;
    View circle;

    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseAuth.AuthStateListener authListener;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_MyApplication);
        setContentView(R.layout.activity_account);

        //Убираем нижнюю панель
        View overlay = findViewById(R.id.root_element);
        overlay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);

        //присваивание переменным элементы формы
        btnHome = findViewById(R.id.home);
        loginAccount = findViewById(R.id.loginAccount);
        emailAccount = findViewById(R.id.emailAccount);
        logout = findViewById(R.id.logout);
        delete = findViewById(R.id.delete);
        circle = findViewById(R.id.circle_account);
        letter = findViewById(R.id.letter);

        //БД
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        //Рандомный цвет
//        Random rnd = new Random();
//        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
//        circle.setBackgroundColor(color);

        //передаем данные из БД
        FirebaseUser user = auth.getCurrentUser();
        String email = user.getEmail();
        emailAccount.setText(email);

        //Певая буква имени
        String firstLetter = String.valueOf(email.charAt(0));
        letter.setText(firstLetter);

        //вызов методов при нажатии на кнопки
        delete.setOnClickListener(v -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(Account.this);
            dialog.setMessage("Вы уверены, что хотите удалить свой аккаунт?");
            dialog.setPositiveButton("Удалить", (dialog1, which) -> {
                user.delete();
                startActivity(new Intent(Account.this, MainActivity.class));
            });
            dialog.setNegativeButton("Отмена", (dialog12, which) -> dialog12.dismiss());
            AlertDialog alertDialog = dialog.create();
            alertDialog.show();
        });

        logout.setOnClickListener(v -> {
            auth.signOut();
            startActivity(new Intent(Account.this, MainActivity.class));
            finish();
        });

        btnHome.setOnClickListener(v -> startActivity(new Intent(Account.this, Main.class)));
    }
}