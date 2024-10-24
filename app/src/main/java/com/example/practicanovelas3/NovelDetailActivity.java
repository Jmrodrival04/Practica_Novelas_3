package com.example.practicanovelas3;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

public class NovelDetailActivity extends AppCompatActivity {

    private NovelViewModel novelViewModel;
    private TextView tvDetailTitle;
    private TextView tvDetailAuthor;
    private TextView tvDetailDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novel_detail);

        tvDetailTitle = findViewById(R.id.tvDetailTitle);
        tvDetailAuthor = findViewById(R.id.tvDetailAuthor);
        tvDetailDescription = findViewById(R.id.tvDetailDescription);

        // Obtener el id de la novela seleccionada
        int novelId = getIntent().getIntExtra("NOVEL_ID", -1);

        // Obtener el ViewModel y cargar la novela
        novelViewModel = new ViewModelProvider(this).get(NovelViewModel.class);
        novelViewModel.getAllNovels().observe(this, new Observer<List<Novel>>() {
            @Override
            public void onChanged(List<Novel> novels) {
                for (Novel novel : novels) {
                    if (novel.getId() == novelId) {
                        showNovelDetails(novel);
                        break;
                    }
                }
            }
        });
    }

    private void showNovelDetails(Novel novel) {
        tvDetailTitle.setText(novel.getTitle());
        tvDetailAuthor.setText(novel.getAuthor());
        tvDetailDescription.setText(novel.getDescription());
    }
}
