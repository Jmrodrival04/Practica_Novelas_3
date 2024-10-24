package com.example.practicanovelas3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NovelViewModel novelViewModel;
    private NovelAdapter adapter;
    private SharedPreferences sharedPreferences;
    private boolean isDarkMode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // Obtener las preferencias y aplicar el tema
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        isDarkMode = sharedPreferences.getBoolean("dark_mode", false);
        if (isDarkMode) {
            setTheme(androidx.appcompat.R.style.Theme_AppCompat_DayNight_DarkActionBar);
        } else {
            setTheme(androidx.appcompat.R.style.Theme_AppCompat_Light);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configurar el RecyclerView para mostrar la lista de novelas
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // Inicializar el adaptador del RecyclerView
        adapter = new NovelAdapter();
        recyclerView.setAdapter(adapter);

        // Configurar el ViewModel para observar los cambios en los datos de las novelas
        novelViewModel = new ViewModelProvider(this).get(NovelViewModel.class);
        novelViewModel.getAllNovels().observe(this, new Observer<List<Novel>>() {
            @Override
            public void onChanged(List<Novel> novels) {
                // Actualizar el adaptador con la nueva lista de novelas
                adapter.setNovels(novels);
            }
        });

        // Configurar el FloatingActionButton (FAB) para agregar nuevas novelas
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            // Abrir la actividad para agregar una nueva novela
            Intent intent = new Intent(MainActivity.this, AddNovelActivity.class);
            startActivity(intent);
        });

        // Configurar la acción cuando se hace clic en un elemento del RecyclerView
        adapter.setOnItemClickListener(novel -> {
            // Abrir la actividad de detalles de la novela seleccionada
            Intent intent = new Intent(MainActivity.this, NovelDetailActivity.class);
            intent.putExtra("NOVEL_ID", novel.getId());
            startActivity(intent);
        });

        // Botón para cambiar el tema
        Button buttonChangeTheme = findViewById(R.id.buttonChangeTheme);
        buttonChangeTheme.setOnClickListener(v -> {
            // Cambiar entre tema oscuro y claro
            isDarkMode = !isDarkMode;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("dark_mode", isDarkMode);
            editor.apply();

            // Reiniciar la actividad para aplicar el nuevo tema
            recreate();
        });
    }
}

