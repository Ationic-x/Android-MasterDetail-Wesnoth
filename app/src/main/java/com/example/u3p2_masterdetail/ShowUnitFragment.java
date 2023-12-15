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
import com.example.u3p2_masterdetail.databinding.FragmentShowUnitBinding;

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
            binding.tvName.setText(unit.name);
            binding.tvDescription.setText(unit.description);
            binding.ivUnit.setImageResource(unit.image);
            binding.tvCost.setText(getString(R.string.cost_value, String.valueOf(unit.cost)));
            binding.tvHp.setText(getString(R.string.hp_value, String.valueOf(unit.hp)));
            binding.tvMp.setText(getString(R.string.mp_value, String.valueOf(unit.mp)));
            binding.tvXp.setText(getString(R.string.xp_value, String.valueOf(unit.xp)));
        });

        NavGraphDirections.ActionGlobalNewUnitFragment action = NavGraphDirections.actionGlobalNewUnitFragment(true);
        binding.fbtnGoModifyUnit.setOnClickListener( v -> {
            unitsViewModel.setUnitImage(unitsViewModel.getSelected().getValue().image);
            navController.navigate(action);
        });
    }
}