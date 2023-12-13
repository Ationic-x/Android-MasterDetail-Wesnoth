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
import com.example.u3p2_masterdetail.databinding.FragmentNewUnitBinding;


public class NewUnitFragment extends Fragment {

    private FragmentNewUnitBinding binding;
    private NavController navController;
    private UnitsViewModel unitsViewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentNewUnitBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unitsViewModel = new ViewModelProvider(requireActivity()).get(UnitsViewModel.class);
        navController = Navigation.findNavController(view);

        binding.btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.tvName.getText().toString();
                String description = binding.etDescription.getText().toString();

                unitsViewModel.insert(new Unit(name, description));
                navController.popBackStack();
            }
        });
    }
}