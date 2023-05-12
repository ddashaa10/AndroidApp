package com.ddasha.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;

public class Main extends AppCompatActivity {

    ImageView createModule, library;
    Button account;
    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_MyApplication);
        setContentView(R.layout.activity_main);

        //Убираем нижнюю панель
        View overlay = findViewById(R.id.root_element);
        overlay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);

        //присваивание переменным элементы формы
        account = findViewById(R.id.account);
        createModule = findViewById(R.id.createIcon);
        library = findViewById(R.id.libraryIcon);
        calendarView = findViewById(R.id.calendarView);

        //вызов методов при нажатии на кнопки
        account.setOnClickListener(v -> startActivity(new Intent(Main.this, Account.class)));
        createModule.setOnClickListener(v -> startActivity(new Intent(Main.this, CreateModule.class)));
        library.setOnClickListener(v -> startActivity(new Intent(Main.this, LibraryModule.class)));

    }
}