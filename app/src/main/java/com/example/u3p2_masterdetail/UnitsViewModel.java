package com.example.u3p2_masterdetail;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class UnitsViewModel extends AndroidViewModel {
    UnitsRepository unitsRepository;
    MutableLiveData<Unit> unitSelected = new MutableLiveData<>();

    public UnitsViewModel(@NonNull Application application){
        super(application);

        unitsRepository = new UnitsRepository(application);
    }

    LiveData<List<Unit>> get(){
        return unitsRepository.get();
    }

    void insert(Unit unit){
        unitsRepository.insert(unit);
    }

    void delete(Unit unit){
        unitsRepository.delete(unit);
    }

    MutableLiveData<Unit> getSelected() {
        return unitSelected;
    }

    void setSelected(Unit unit) {
        unitSelected.setValue(unit);
    }
}
