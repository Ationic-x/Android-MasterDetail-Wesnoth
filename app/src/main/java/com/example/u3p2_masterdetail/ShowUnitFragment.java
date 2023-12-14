package com.example.u3p2_masterdetail;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProvider;
import com.example.u3p2_masterdetail.databinding.FragmentShowUnitBinding;
import org.jetbrains.annotations.NotNull;

// Class to show specific unit info
public class ShowUnitFragment extends Fragment {
    private FragmentShowUnitBinding binding;
    private UnitsViewModel unitsViewModel;


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
        unitsViewModel = new ViewModelProvider(requireActivity()).get(UnitsViewModel.class);
        // Get from the viewModel the selected Unit, show on Screen the Data
        unitsViewModel.getSelected().observe(getViewLifecycleOwner(), unit -> {
            binding.tvName.setText(unit.name);
            binding.tvDescription.setText(unit.description);
        });
    }
}