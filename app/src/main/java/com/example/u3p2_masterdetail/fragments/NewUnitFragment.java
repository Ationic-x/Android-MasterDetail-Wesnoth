package com.example.u3p2_masterdetail.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.u3p2_masterdetail.R;
import com.example.u3p2_masterdetail.units.Unit;
import com.example.u3p2_masterdetail.utils.DialogNumberPicker;
import com.example.u3p2_masterdetail.utils.NameGenerator;
import com.example.u3p2_masterdetail.utils.PictureSelector;
import com.example.u3p2_masterdetail.viewmodel.UnitsViewModel;
import com.example.u3p2_masterdetail.databinding.FragmentNewUnitBinding;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Class related to create new units screen
public class NewUnitFragment extends Fragment {

    private FragmentNewUnitBinding binding;
    private UnitsViewModel unitsViewModel;

    private Handler handler;
    private NameGenerator nameGenerator;


    // Inflate and get the binding
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentNewUnitBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacksAndMessages(null);
    }

    // After create the view, set the behavior
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        handler = new Handler(Looper.getMainLooper());
        super.onViewCreated(view, savedInstanceState); // Values to the father
        boolean modify = NewUnitFragmentArgs.fromBundle(getArguments()).getModify();

        // View model related modify db
        unitsViewModel = new ViewModelProvider(requireActivity()).get(UnitsViewModel.class);
        // NavController navigate between fragments
        NavController navController = Navigation.findNavController(view);

        nameGenerator = new NameGenerator();

        getImage().observe(getViewLifecycleOwner(), image -> binding.ivUnit.setImageResource(image));


        List<Integer> unitDrawables = getDrawables("unit");
        PictureSelector pictureSelector = new PictureSelector(unitDrawables, getContext(), unitsViewModel);
        DialogNumberPicker dialogNumberPicker = new DialogNumberPicker(0, 300, getContext());

        if (modify) {
            binding.btnCreate.setText(R.string.unit_modify);

            Unit selectedUnit = (unitsViewModel.getSelected()).getValue();

            if (selectedUnit == null) {
                navController.popBackStack();
            }

            setUnitValues(selectedUnit);

            // Defined the behavior of the button (on click event)
            binding.btnCreate.setOnClickListener(v -> {
                if(!(binding.etName.getText().toString().isEmpty() || binding.etDescription.getText().toString().isEmpty())){
                    // Insert new view model
                    unitsViewModel.update(selectedUnit, newUnit());
                    // Return to the last fragment
                    navController.popBackStack();
                }
                binding.etName.setError("You need to enter a name");
                binding.etDescription.setError("You need to enter a description");
            });
        } else {

            binding.btnCreate.setText(R.string.nw_unit_create);

            // Defined the behavior of the button (on click event)
            binding.btnCreate.setOnClickListener(v -> {
                if(!(binding.etName.getText().toString().isEmpty() || binding.etDescription.getText().toString().isEmpty())){
                    // Insert new view model
                    unitsViewModel.insert(newUnit());
                    // Return to the last fragment
                    navController.popBackStack();
                }
                binding.etName.setError("You need to enter a name");
                binding.etDescription.setError("You need to enter a description");

            });

        }

        binding.fbtnRandomUnit.setOnClickListener(v -> {

            for (int i = 0; i < 11; i++) {
                handler.postDelayed(() -> randomConfiguration(unitDrawables), (int) Math.pow(1.9, i));
            }
        });

        binding.flUnit.setOnClickListener(v -> pictureSelector.show());

        binding.etCost.setOnClickListener(v -> dialogNumberPicker.show(binding.etCost));
        binding.etHp.setOnClickListener(v -> dialogNumberPicker.show(binding.etHp));
        binding.etXp.setOnClickListener(v -> dialogNumberPicker.show(binding.etXp));
        binding.etMp.setOnClickListener(v -> dialogNumberPicker.show(binding.etMp));
    }

    private void setUnitValues(Unit selectedUnit) {
        binding.etDescription.setText(Objects.requireNonNull(selectedUnit).getDescription());
        binding.etName.setText(selectedUnit.getName());
        binding.etHp.setText(String.valueOf(selectedUnit.getHp()));
        binding.etXp.setText(String.valueOf(selectedUnit.getXp()));
        binding.etMp.setText(String.valueOf(selectedUnit.getMp()));
        binding.etCost.setText(String.valueOf(selectedUnit.getCost()));
    }

    private Unit newUnit() {
        String name = binding.etName.getText().toString();
        String description = binding.etDescription.getText().toString();
        int cost = Integer.parseInt(binding.etCost.getText().toString());
        int hp = Integer.parseInt(binding.etHp.getText().toString());
        int xp = Integer.parseInt(binding.etXp.getText().toString());
        int mp = Integer.parseInt(binding.etMp.getText().toString());
        Integer image = unitsViewModel.getUnitImage().getValue();

        return new Unit(name, description, image == null ? R.drawable.unit_unknown : image, cost, hp, xp, mp);
    }

    private void randomConfiguration(List<Integer> unitDrawables) {
        String name = nameGenerator.generateName();
        binding.etName.setText(name);
        binding.etDescription.setText(getString(R.string.random_description, name));
        binding.etMp.setText(String.valueOf((int) (Math.random() * 301)));
        binding.etHp.setText(String.valueOf((int) (Math.random() * 301)));
        binding.etCost.setText(String.valueOf((int) (Math.random() * 301)));
        binding.etXp.setText(String.valueOf((int) (Math.random() * 301)));
        unitsViewModel.setUnitImage(unitDrawables.get((int) (Math.random() * unitDrawables.size())));
    }

    protected LiveData<Integer> getImage() {
        return unitsViewModel.getUnitImage();
    }

    public static List<Integer> getDrawables(String type) {
        List<Integer> drawableList = new ArrayList<>();
        try {
            Field[] fields = R.drawable.class.getFields();
            for (Field field : fields) {
                if (field.getName().startsWith(type + "_")) {
                    Object value = field.get(R.drawable.class);
                    if (value instanceof Integer) {
                        int resourceId = (int) value;
                        drawableList.add(resourceId);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            Log.d("Get Drawables", e.toString());
        }
        return drawableList;
    }
}