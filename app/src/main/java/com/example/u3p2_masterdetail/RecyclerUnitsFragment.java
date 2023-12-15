package com.example.u3p2_masterdetail;

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

    // After create the view, set the behavior
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init View Model and the NavController
        unitsViewModel = new ViewModelProvider(requireActivity()).get(UnitsViewModel.class);
        navController = Navigation.findNavController(view);

        // Method floating button -> navigate new unit fragment
        binding.fbtnGoNewUnit.setOnClickListener( v -> navController.navigate(R.id.action_global_newUnitFragment));

        // Create adapter and set on the Recycle View
        unitsAdapter = new UnitsAdapter();
        binding.rvUnits.setAdapter(unitsAdapter);
        // Observe when there are changes on the list of units
        getUnits().observe(getViewLifecycleOwner(), units -> unitsAdapter.establishList(units));


        // When an Item is touched get callbacks (up, down drag) (left, right swipe)
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            // Disable all kind of movement
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            // Capture Swipe
            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                // When Swipe, check the unit swiped and delete
                Unit unit = unitsAdapter.units.get(viewHolder.getAdapterPosition());
                unitsViewModel.delete(unit);
            }
        }).attachToRecyclerView(binding.rvUnits); // Link to Recycler View
    }

    // Get all units from the ViewModel
    protected LiveData<List<Unit>> getUnits(){
        return unitsViewModel.get();
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
            Unit unit = units.get(position); // position

            holder.binding.tvName.setText(unit.name); // set name
            holder.binding.tvValues.setText(getString(R.string.unit_values, String.valueOf(unit.cost), String.valueOf(unit.hp), String.valueOf(unit.xp), String.valueOf(unit.mp)));
            holder.binding.ivUnit.setImageResource(unit.image);

            // Click event, update viewModel selected unit and navigate the unit fragment
            holder.itemView.setOnClickListener(v -> {
                unitsViewModel.setSelected(unit);
                navController.navigate(R.id.action_global_showUnitFragment);
            });
        }

        // Get how many units there is
        @Override
        public int getItemCount() {
            return units != null ? units.size() : 0;
        }

        // Update the list and notify the change
        public void establishList(List<Unit> units) {
            this.units = units;
            notifyDataSetChanged();
        }
    }


    // Class related view holder
    class UnitViewHolder extends RecyclerView.ViewHolder {
        private final ViewholderUnitBinding binding;

        // Constructor that init binding
        public UnitViewHolder(ViewholderUnitBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}