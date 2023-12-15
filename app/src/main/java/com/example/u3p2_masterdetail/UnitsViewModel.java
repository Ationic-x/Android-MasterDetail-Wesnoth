package com.example.u3p2_masterdetail;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

// ViewModel info related Fragments (view) and Database + Unit (Model)
public class UnitsViewModel extends AndroidViewModel {
    UnitsRepository unitsRepository; // Repository where is stored the values
    MutableLiveData<Unit> unitSelected = new MutableLiveData<>(); // Mutable value (change in view)
    MutableLiveData<Integer> unitImage = new MutableLiveData<>(R.drawable.unit_unknown);

    // Constructor, defined the AndroidViewModel and Repository
    public UnitsViewModel(@NonNull Application application){
        super(application);

        unitsRepository = new UnitsRepository(application);
    }

    // Getter and setter related mutable values
    MutableLiveData<Unit> getSelected() {
        return unitSelected;
    }

    void setSelected(Unit unit) {
        unitSelected.setValue(unit);
    }

    MutableLiveData<Integer> getUnitImage(){return unitImage;}

    void setUnitImage(Integer image) {unitImage.setValue(image);}

    // Extended repository method
    LiveData<List<Unit>> get(){
        return unitsRepository.get();
    }

    void insert(Unit unit){
        unitsRepository.insert(unit);
    }

    void delete(Unit unit){
        unitsRepository.delete(unit);
    }

    void update(Unit unit, int image){
        unitsRepository.update(unit, image);
    }

}
