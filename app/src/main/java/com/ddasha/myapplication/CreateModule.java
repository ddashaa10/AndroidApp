package com.ddasha.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateModule extends AppCompatActivity {

    Button back, add_block, add_module;
    LinearLayout layout;
    RelativeLayout root;
    EditText name;

    FirebaseDatabase database;
    DatabaseReference users, modules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_MyApplication);
        setContentView(R.layout.activity_create_module);

        //присваивание переменным элементы формы
        layout = findViewById(R.id.linerCreate);
        root = findViewById(R.id.root_element);
        name = findViewById(R.id.name_module);
        back=findViewById(R.id.back);
        add_block=findViewById(R.id.add_block);
        add_module=findViewById(R.id.add_module);

        //БД
        database = FirebaseDatabase.getInstance();
        modules = database.getReference("Modules");
        users = database.getReference("Users");

        //Убираем нижнюю панель
        View overlay = root;
        overlay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);

        //вызов методов при нажатии на кнопки
        back.setOnClickListener(v -> startActivity(new Intent(CreateModule.this, Main.class)));
        add_module.setOnClickListener(v -> {
            Intent intent = new Intent(CreateModule.this, LibraryModule.class);
            intent.putExtra("create", "true");
            intent.putExtra("name", name.getText().toString());
            startActivity(intent);
        });
        add_block.setOnClickListener(v -> {addNewLayout(layout, 300, 200);});
    }

    private void addNewLayout(LinearLayout layout, int width, int height) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
        layoutParams.setMargins(0, 175, 0, 0);
        layout.setLayoutParams(layoutParams);
    }
}