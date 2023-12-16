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

// ViewModel class for handling Unit-related data
public class UnitsViewModel extends AndroidViewModel {
    // Repository responsible for storing and retrieving Unit data
    private final UnitsRepository unitsRepository;
    // MutableLiveData to observe the selected Unit in the view
    private final MutableLiveData<Unit> unitSelected = new MutableLiveData<>();
    // MutableLiveData to observe the selected Unit's image resource ID in the view
    private final MutableLiveData<Integer> unitImage = new MutableLiveData<>(R.drawable.unit_unknown);

    // Constructor for the ViewModel, initializes the AndroidViewModel and the UnitsRepository
    public UnitsViewModel(@NonNull Application application) {
        super(application);
        unitsRepository = new UnitsRepository(application);
    }

    // Getter and setter methods for the MutableLiveData related to the selected Unit
    public MutableLiveData<Unit> getSelected() {
        return unitSelected;
    }

    public void setSelected(Unit unit) {
        unitSelected.setValue(unit);
    }

    // Getter and setter methods for the MutableLiveData related to the selected Unit's image resource ID
    public MutableLiveData<Integer> getUnitImage() {
        return unitImage;
    }

    public void setUnitImage(Integer image) {
        unitImage.setValue(image);
    }

    // Extended repository methods for accessing Unit data
    public LiveData<List<Unit>> get() {
        return unitsRepository.get();
    }

    public void insert(Unit unit) {
        unitsRepository.insert(unit);
    }

    public void delete(Unit unit) {
        unitsRepository.delete(unit);
    }

    public void update(Unit oldUnit, Unit newUnit) {
        unitsRepository.update(oldUnit, newUnit);
    }
}