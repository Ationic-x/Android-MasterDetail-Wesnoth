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


public class ShowUnitFragment extends Fragment {
    private FragmentShowUnitBinding binding;
    private UnitsViewModel unitsViewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentShowUnitBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unitsViewModel = new ViewModelProvider(requireActivity()).get(UnitsViewModel.class);
        unitsViewModel.getSelected().observe(getViewLifecycleOwner(), unit -> {
            binding.tvName.setText(unit.name);
            binding.tvDescription.setText(unit.description);
        });
    }
}