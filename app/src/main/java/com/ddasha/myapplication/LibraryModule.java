package com.ddasha.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class LibraryModule extends AppCompatActivity {

    Button back;
    RelativeLayout root;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_MyApplication);
        setContentView(R.layout.activity_library_module);

        //присваивание переменным элементы формы
        back=findViewById(R.id.back);
        root = findViewById(R.id.root_element);

        //Убираем нижнюю панель
        View overlay = root;
        overlay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);

        //вызов методов при нажатии на кнопки
        back.setOnClickListener(v -> startActivity(new Intent(LibraryModule.this, Main.class)));

    }

    private void addNewModule(Button button) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(1000, 600);
        layoutParams.setMargins(50, 170, 50, 0);
        button.setLayoutParams(layoutParams);
        button.setText(name);
        root.addView(button);
    }
}