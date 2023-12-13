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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentRecyclerUnitsBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unitsViewModel = new ViewModelProvider(requireActivity()).get(UnitsViewModel.class);
        navController = Navigation.findNavController(view);

        binding.fbtnGoNewUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_global_newUnitFragment);
            }
        });

        unitsAdapter = new UnitsAdapter();
        binding.rvUnits.setAdapter(unitsAdapter);
        getUnits().observe(getViewLifecycleOwner(), units -> unitsAdapter.establishList(units));

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                Unit unit = unitsAdapter.units.get(viewHolder.getAdapterPosition());
                unitsViewModel.delete(unit);
            }
        }).attachToRecyclerView(binding.rvUnits);
    }

    protected LiveData<List<Unit>> getUnits(){
        return unitsViewModel.get();
    }

    class UnitsAdapter extends RecyclerView.Adapter<UnitViewHolder> {

        List<Unit> units;

        @NonNull
        @Override
        public UnitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new UnitViewHolder(ViewholderUnitBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull UnitViewHolder holder, int position) {
            Unit unit = units.get(position);

            holder.binding.tvName.setText(unit.name);

            holder.itemView.setOnClickListener(v -> {
                unitsViewModel.setSelected(unit);
                navController.navigate(R.id.action_global_showUnitFragment);
            });
        }

        @Override
        public int getItemCount() {
            return units != null ? units.size() : 0;
        }

        public void establishList(List<Unit> units) {
            this.units = units;
            notifyDataSetChanged();
        }
    }


    class UnitViewHolder extends RecyclerView.ViewHolder {

        private final ViewholderUnitBinding binding;

        public UnitViewHolder(ViewholderUnitBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}