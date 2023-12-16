package com.example.u3p2_masterdetail.fragments;

import android.os.Bundle;
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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.example.u3p2_masterdetail.NavGraphDirections;
import com.example.u3p2_masterdetail.R;
import com.example.u3p2_masterdetail.units.Unit;
import com.example.u3p2_masterdetail.viewmodel.UnitsViewModel;
import com.example.u3p2_masterdetail.databinding.FragmentRecyclerUnitsBinding;
import com.example.u3p2_masterdetail.databinding.ViewholderUnitBinding;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RecyclerUnitsFragment extends Fragment {

    private FragmentRecyclerUnitsBinding binding;
    private NavController navController;
    private UnitsViewModel unitsViewModel;
    private UnitsAdapter unitsAdapter;

    // Inflate and get the binding
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentRecyclerUnitsBinding.inflate(inflater, container, false)).getRoot();
    }

    // After creating the view, set the behavior
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize ViewModel and NavController
        unitsViewModel = new ViewModelProvider(requireActivity()).get(UnitsViewModel.class);
        navController = Navigation.findNavController(view);

        // Method floating button -> navigate to new unit fragment
        NavGraphDirections.ActionGlobalNewUnitFragment action = NavGraphDirections.actionGlobalNewUnitFragment(false);
        binding.fbtnGoNewUnit.setOnClickListener(v -> {
            unitsViewModel.setUnitImage(R.drawable.unit_unknown);
            navController.navigate(action);
        });

        // Create adapter and set it on the RecyclerView
        unitsAdapter = new UnitsAdapter();
        binding.rvUnits.setAdapter(unitsAdapter);

        // Observe changes in the list of units
        getUnits().observe(getViewLifecycleOwner(), updatedUnits -> {
            List<Unit> originalUnits = unitsAdapter.units;
            int option;
            if (originalUnits == null) option = 0;
            else option = 1;

            int changedPosition = findChangedPosition(originalUnits, updatedUnits);
            unitsAdapter.establishList(updatedUnits, changedPosition, option);
        });

        // When an Item is touched get callbacks (up, down drag) (left, right swipe)
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            // Disable all kind of movement
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            // Capture Swipe
            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                // When swiped, check the unit swiped and delete
                Unit unit = unitsAdapter.units.get(viewHolder.getAdapterPosition());
                unitsViewModel.delete(unit);
            }
        }).attachToRecyclerView(binding.rvUnits); // Link to RecyclerView
    }

    // Get all units from the ViewModel
    private LiveData<List<Unit>> getUnits() {
        return unitsViewModel.get();
    }

    // Find the position of the changed unit in the list
    private int findChangedPosition(List<Unit> originalUnits, List<Unit> updateUnits) {
        if (originalUnits == null) {
            return updateUnits.size();
        }
        for (int i = 0; i < updateUnits.size(); ++i) {
            if (originalUnits.get(i).getId() != updateUnits.get(i).getId()) {
                return i;
            }
        }
        return updateUnits.size();
    }

    // Adapter for the RecyclerView
    class UnitsAdapter extends RecyclerView.Adapter<UnitViewHolder> {

        List<Unit> units;

        // Inflate ViewHolder (using class UnitViewHolder)
        @NonNull
        @Override
        public UnitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new UnitViewHolder(ViewholderUnitBinding.inflate(getLayoutInflater(), parent, false));
        }

        // Bind element View Holder
        @Override
        public void onBindViewHolder(@NonNull UnitViewHolder holder, int position) {
            Unit unit = units.get(position);

            holder.binding.tvName.setText(unit.getName()); // set name
            holder.binding.tvValues.setText(getString(R.string.unit_values, String.valueOf(unit.getCost()), String.valueOf(unit.getHp()), String.valueOf(unit.getXp()), String.valueOf(unit.getMp())));
            holder.binding.ivUnit.setImageResource(unit.getImage());

            // Click event, update viewModel selected unit and navigate to the unit fragment
            holder.itemView.setOnClickListener(v -> {
                unitsViewModel.setSelected(unit);
                navController.navigate(R.id.action_global_showUnitFragment);
            });
        }

        // Get how many units there are
        @Override
        public int getItemCount() {
            return units != null ? units.size() : 0;
        }

        // Update the list and notify the change
        private void establishList(List<Unit> units, int changedPosition, int option) {
            this.units = units;
            switch (option) {
                case 0:
                    notifyItemChanged(changedPosition);
                case 1:
                    notifyItemRemoved(changedPosition);
            }
        }
    }

    // Class related view holder
    static class UnitViewHolder extends RecyclerView.ViewHolder {
        private final ViewholderUnitBinding binding;

        // Constructor that initializes binding
        public UnitViewHolder(ViewholderUnitBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}