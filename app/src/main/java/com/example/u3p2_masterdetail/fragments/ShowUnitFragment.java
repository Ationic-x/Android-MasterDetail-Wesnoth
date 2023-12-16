package com.example.u3p2_masterdetail.fragments;

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
import com.example.u3p2_masterdetail.NavGraphDirections;
import com.example.u3p2_masterdetail.R;
import com.example.u3p2_masterdetail.viewmodel.UnitsViewModel;
import com.example.u3p2_masterdetail.databinding.FragmentShowUnitBinding;

import java.util.Objects;

// Class to show specific unit info
public class ShowUnitFragment extends Fragment {
    private FragmentShowUnitBinding binding;


    // Inflate and get the binding
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentShowUnitBinding.inflate(inflater, container, false)).getRoot();
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
            binding.tvName.setText(unit.getName());
            binding.tvDescription.setText(unit.getDescription());
            binding.ivUnit.setImageResource(unit.getImage());
            binding.tvCost.setText(getString(R.string.cost_value, String.valueOf(unit.getCost())));
            binding.tvHp.setText(getString(R.string.hp_value, String.valueOf(unit.getHp())));
            binding.tvMp.setText(getString(R.string.mp_value, String.valueOf(unit.getMp())));
            binding.tvXp.setText(getString(R.string.xp_value, String.valueOf(unit.getXp())));
        });

        NavGraphDirections.ActionGlobalNewUnitFragment action = NavGraphDirections.actionGlobalNewUnitFragment(true);
        binding.fbtnGoModifyUnit.setOnClickListener( v -> {
            unitsViewModel.setUnitImage(Objects.requireNonNull(unitsViewModel.getSelected().getValue()).getImage());
            navController.navigate(action);
        });
    }
}