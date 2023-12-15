package com.example.u3p2_masterdetail;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.u3p2_masterdetail.databinding.FragmentModifyUnitBinding;

public class modifyUnitFragment extends Fragment {

    private FragmentModifyUnitBinding binding;


    // Inflate and get the binding
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentModifyUnitBinding.inflate(inflater, container, false)).getRoot();
    }

    // After create the view, set the behavior
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get the viewModel
        UnitsViewModel unitsViewModel = new ViewModelProvider(requireActivity()).get(UnitsViewModel.class);
        NavController navController = Navigation.findNavController(view);
        // Get from the viewModel the selected Unit, show on Screen the Data
        unitsViewModel.getSelected().observe(getViewLifecycleOwner(), unit -> {
            binding.etName.setText(unit.name);
            binding.etDescription.setText(unit.description);
            binding.ivUnit.setImageResource(unit.image);
            binding.etCost.setText(String.valueOf(unit.cost));
            binding.etHp.setText(String.valueOf(unit.hp));
            binding.etMp.setText(String.valueOf(unit.mp));
            binding.etXp.setText(String.valueOf(unit.xp));
        });

        binding.btnModify.setOnClickListener( v -> navController.navigate(R.id.showUnitFragment));
    }
}