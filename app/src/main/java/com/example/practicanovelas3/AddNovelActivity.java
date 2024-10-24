package com.example.practicanovelas3;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class AddNovelActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextAuthor;
    private EditText editTextDescription;
    private NovelViewModel novelViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_novel);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextAuthor = findViewById(R.id.editTextAuthor);
        editTextDescription = findViewById(R.id.editTextDescription);
        Button buttonSave = findViewById(R.id.buttonSave);

        // Obtener el ViewModel
        novelViewModel = new ViewModelProvider(this).get(NovelViewModel.class);

        // Acción al presionar el botón Guardar
        buttonSave.setOnClickListener(view -> saveNovel());
    }

    private void saveNovel() {
        String title = editTextTitle.getText().toString().trim();
        String author = editTextAuthor.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(author)) {
            Toast.makeText(this, "Por favor, ingresa el título y el autor", Toast.LENGTH_SHORT).show();
            return;
        }

        Novel novel = new Novel(title, author, description);
        novelViewModel.insert(novel);
        Toast.makeText(this, "Novela guardada", Toast.LENGTH_SHORT).show();
        finish();  // Cerrar la actividad después de guardar
    }
}

