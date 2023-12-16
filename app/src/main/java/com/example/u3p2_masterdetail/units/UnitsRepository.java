package com.example.u3p2_masterdetail.units;

import android.app.Application;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

// Class with the values and basic methods of units
public class UnitsRepository {
    private final UnitsDataBase.UnitsDao unitsDao; // Database of units (Data Access Object)
    private final Executor executor; // Execute on background threads

    // Constructor initializes Executor and Database
    public UnitsRepository(Application application) {
        unitsDao = UnitsDataBase.getInstance(application).getUnitsDao();
        executor = Executors.newSingleThreadExecutor();
    }

    // Extended method from Database to get all units
    public LiveData<List<Unit>> get() {
        return unitsDao.get();
    }

    // Methods that change the Database are executed in the background

    // Insert a new unit
    public void insert(Unit unit) {
        executor.execute(() ->
                unitsDao.insert(unit)
        );
    }

    // Delete a unit
    public void delete(Unit unit) {
        executor.execute(() ->
                unitsDao.delete(unit)
        );
    }

    // Update a unit
    public void update(Unit oldUnit, Unit newUnit) {
        executor.execute(() -> {
            // Update individual fields of the old unit with the values of the new unit (preserve id)
            oldUnit.setName(newUnit.getName());
            oldUnit.setImage(newUnit.getImage());
            oldUnit.setMp(newUnit.getMp());
            oldUnit.setCost(newUnit.getCost());
            oldUnit.setXp(newUnit.getXp());
            oldUnit.setHp(newUnit.getHp());
            oldUnit.setDescription(newUnit.getDescription());

            // Update the unit in the database
            unitsDao.update(oldUnit);
        });
    }
}