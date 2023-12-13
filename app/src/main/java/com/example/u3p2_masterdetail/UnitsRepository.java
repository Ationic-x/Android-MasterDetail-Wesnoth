package com.example.u3p2_masterdetail;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UnitsRepository {
    UnitsDataBase.UnitsDao unitsDao;
    Executor executor;

    interface Callback{
        void cuandoFinalice(List<Unit> units);
    }

    UnitsRepository(Application application){
        unitsDao = UnitsDataBase.getInstance(application).getUnitsDao();
        executor = Executors.newSingleThreadExecutor();
    }

    LiveData<List<Unit>> get(){
        return unitsDao.get();
    }

    void insert(Unit unit){
        executor.execute(() -> {
            unitsDao.insert(unit);
        });
    }

    void delete(Unit unit){
        executor.execute(() -> {
            unitsDao.delete(unit);
        });
    }

}
