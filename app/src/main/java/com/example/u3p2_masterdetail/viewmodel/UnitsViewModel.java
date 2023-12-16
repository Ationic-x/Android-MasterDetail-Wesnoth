package com.example.u3p2_masterdetail.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.u3p2_masterdetail.R;
import com.example.u3p2_masterdetail.units.Unit;
import com.example.u3p2_masterdetail.units.UnitsRepository;

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
    public MutableLiveData<Unit> getSelected() {
        return unitSelected;
    }

    public void setSelected(Unit unit) {
        unitSelected.setValue(unit);
    }

    public MutableLiveData<Integer> getUnitImage() {
        return unitImage;
    }

    public void setUnitImage(Integer image) {
        unitImage.setValue(image);
    }

    // Extended repository method
    public LiveData<List<Unit>> get(){
        return unitsRepository.get();
    }

    public void insert(Unit unit){
        unitsRepository.insert(unit);
    }

    public void delete(Unit unit){
        unitsRepository.delete(unit);
    }

    public void update(Unit oldUnit, Unit newUnit){
        unitsRepository.update(oldUnit, newUnit);
    }

}
