package com.example.u3p2_masterdetail.units;

import android.app.Application;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

// Class with the values and basic method of units
public class UnitsRepository {
    private final UnitsDataBase.UnitsDao unitsDao; // DataBase of unit (Data Access Object)
    private final Executor executor; // Execute on background threads

    // Constructor init Executor and Database
    public UnitsRepository(Application application){
        unitsDao = UnitsDataBase.getInstance(application).getUnitsDao();
        executor = Executors.newSingleThreadExecutor();
    }

    // Extended method from DataBase
    public LiveData<List<Unit>> get(){
        return unitsDao.get();
    }

    // Methods that change the DataBase are executed in the background
    public void insert(Unit unit){
        executor.execute(() ->
            unitsDao.insert(unit)
        );
    }

    public void delete(Unit unit){
        executor.execute(() ->
            unitsDao.delete(unit)
        );
    }

    public void update(Unit oldUnit, Unit newUnit){
        executor.execute(() -> {
            oldUnit.setName(newUnit.getName());
            oldUnit.setImage(newUnit.getImage());
            oldUnit.setMp(newUnit.getMp());
            oldUnit.setCost(newUnit.getCost());
            oldUnit.setXp(newUnit.getXp());
            oldUnit.setHp(newUnit.getHp());
            oldUnit.setDescription(newUnit.getDescription());
            unitsDao.update(oldUnit);
        });
    }

}
