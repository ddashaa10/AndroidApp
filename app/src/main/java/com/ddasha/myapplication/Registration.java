package com.ddasha.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.ddasha.myapplication.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class Registration extends AppCompatActivity {

    EditText password, name, email;
    Button reg_btn;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference users;
    RelativeLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_MyApplication);
        setContentView(R.layout.activity_registration);

        //присваивание переменным элементы формы
        root = findViewById(R.id.root_element);
        reg_btn = findViewById(R.id.button);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        name = findViewById(R.id.name);

        //Убираем нижнюю панель
        View overlay = root;
        overlay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);

        //БД
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        //вызов методов при нажатии на кнопки
        reg_btn.setOnClickListener(v -> regUserAuth());
    }

    private void regUserAuth() {
        if(TextUtils.isEmpty(email.getText().toString())){
            Snackbar.make(root, "Введите почту", Snackbar.LENGTH_SHORT).show();
            return;
        }
        if(password.getText().toString().length()<5){
            Snackbar.make(root, "Введите пароль", Snackbar.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(name.getText().toString())){
            Snackbar.make(root, "Введите имя", Snackbar.LENGTH_SHORT).show();
            return;
        }
        //Регистрация пользователя
        auth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnSuccessListener(authResult -> {
                    User user = new User();
                    user.setPassword(password.getText().toString());
                    user.setName(name.getText().toString());
                    user.setEmail(email.getText().toString());

                    users.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnSuccessListener(unused -> {
                                startActivity(new Intent(Registration.this, MainActivity.class));
                                finish();
                            }).addOnFailureListener(e -> Snackbar.make(root, "Ошибка регистрации. "+ e.getMessage(), Snackbar.LENGTH_SHORT).show());
                });
    }
}