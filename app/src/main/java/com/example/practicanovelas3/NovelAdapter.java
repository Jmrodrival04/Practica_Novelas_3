package com.example.practicanovelas3;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class NovelAdapter extends RecyclerView.Adapter<NovelAdapter.NovelViewHolder> {

    private List<Novel> novels = new ArrayList<>(); // Inicializar con lista vacía
    private OnItemClickListener listener;

    // Constructor vacío para no requerir parámetros al inicializar
    public NovelAdapter() {
    }

    @NonNull
    @Override
    public NovelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_novel, parent, false);
        return new NovelViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NovelViewHolder holder, int position) {
        Novel currentNovel = novels.get(position);
        holder.tvTitle.setText(currentNovel.getTitle());
        holder.tvAuthor.setText(currentNovel.getAuthor());
    }

    @Override
    public int getItemCount() {
        return novels.size();
    }

    // Método para actualizar la lista de novelas en el adaptador
    public void setNovels(List<Novel> novels) {
        this.novels = novels;
        notifyDataSetChanged();
    }

    // ViewHolder que representa cada elemento en el RecyclerView
    class NovelViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private TextView tvAuthor;

        public NovelViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);

            // Configurar clic en cada elemento
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(novels.get(position));
                }
            });
        }
    }

    // Interfaz para manejar el clic en un elemento del RecyclerView
    public interface OnItemClickListener {
        void onItemClick(Novel novel);
    }

    // Método para definir el listener desde el exterior
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
