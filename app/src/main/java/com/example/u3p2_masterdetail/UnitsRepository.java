package com.example.u3p2_masterdetail;

import android.app.Application;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

// Class with the values and basic method of units
public class UnitsRepository {
    UnitsDataBase.UnitsDao unitsDao; // DataBase of unit (Data Access Object)
    Executor executor; // Execute on background threads

    // Constructor init Executor and Database
    UnitsRepository(Application application){
        unitsDao = UnitsDataBase.getInstance(application).getUnitsDao();
        executor = Executors.newSingleThreadExecutor();
    }

    // Extended method from DataBase
    LiveData<List<Unit>> get(){
        return unitsDao.get();
    }

    // Methods that change the DataBase are executed in the background
    void insert(Unit unit){
        executor.execute(() ->
            unitsDao.insert(unit)
        );
    }

    void delete(Unit unit){
        executor.execute(() ->
            unitsDao.delete(unit)
        );
    }

    void update(Unit unit, int image){
        executor.execute(() -> {
            unit.image = image;
            unitsDao.update(unit);
        });
    }

}
