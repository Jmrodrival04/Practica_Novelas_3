package com.example.practicanovelas3;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NovelRepository {

    private NovelDao novelDao;
    private LiveData<List<Novel>> allNovels;
    private ExecutorService executorService;

    public NovelRepository(Application application) {
        NovelDatabase database = NovelDatabase.getInstance(application);
        novelDao = database.novelDao();
        allNovels = novelDao.getAllNovels();
        executorService = Executors.newFixedThreadPool(2);
    }

    public void insert(Novel novel) {
        executorService.execute(() -> novelDao.insert(novel));
    }

    public void update(Novel novel) {
        executorService.execute(() -> novelDao.update(novel));
    }

    public void delete(Novel novel) {
        executorService.execute(() -> novelDao.delete(novel));
    }

    public void deleteAllNovels() {
        executorService.execute(() -> novelDao.deleteAllNovels());
    }

    public LiveData<List<Novel>> getAllNovels() {
        return allNovels;
    }
}