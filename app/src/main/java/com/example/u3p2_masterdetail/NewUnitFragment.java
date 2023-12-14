package com.example.u3p2_masterdetail;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.u3p2_masterdetail.databinding.FragmentNewUnitBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

// Class related to create new units screen
public class NewUnitFragment extends Fragment {

    private FragmentNewUnitBinding binding;


    // Inflate and get the binding
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentNewUnitBinding.inflate(inflater, container, false)).getRoot();
    }

    // After create the view, set the behavior
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState); // Values to the father

        // View model related modify db
        UnitsViewModel unitsViewModel = new ViewModelProvider(requireActivity()).get(UnitsViewModel.class);
        // NavController navigate between fragments
        NavController navController = Navigation.findNavController(view);

        // Defined the behavior of the button (on click event)
        binding.btnCreate.setOnClickListener(v -> {
            // Get values in the view
            String name = binding.tvName.getText().toString();
            String description = binding.etDescription.getText().toString();
            // Insert new view model
            unitsViewModel.insert(new Unit(name, description));
            // Return to the last fragment
            navController.popBackStack();
        });

        binding.btnPicture.setOnClickListener(v -> showPictureSelector());
    }

    private void showPictureSelector() {
        // Crea un BottomSheetDialog
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
        View menuDesplegableView = getLayoutInflater().inflate(R.layout.picture_selector, null);

        GridView gridViewImages = menuDesplegableView.findViewById(R.id.gvImages);
        // Carga las IDs de las imágenes desde /res/drawable/units
        int[] imageIds = {
                R.drawable.adept,
        };

        // Configura el adaptador para el GridView
        CustomAdapter customAdapter = new CustomAdapter(requireContext(), imageIds);
        gridViewImages.setAdapter(customAdapter);

        bottomSheetDialog.setContentView(menuDesplegableView);

        FrameLayout bottomSheet = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);

        if (bottomSheet != null) {
            BottomSheetBehavior<FrameLayout> behavior = BottomSheetBehavior.from(bottomSheet);
            // Establece el tamaño deseado del BottomSheet
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomSheet.getLayoutParams();
            layoutParams.height = obtenerAlturaDeseada();
            bottomSheet.setLayoutParams(layoutParams);

            behavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                        // Impide que el BottomSheet se cierre por deslizamiento
                        bottomSheetDialog.setCancelable(false);
                    } else {
                        bottomSheetDialog.setCanceledOnTouchOutside(true);
                    }
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                }
            });
        }
        bottomSheetDialog.show();
    }

    private int obtenerAlturaDeseada() {
        WindowManager windowManager = (WindowManager) requireContext().getSystemService(Context.WINDOW_SERVICE);

        if (windowManager != null) {
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            int screenHeight = displayMetrics.heightPixels;

            // Calcula el 33% de la altura de la pantalla
            return screenHeight * 50 / 100;
        }

        // En caso de que no se pueda obtener el WindowManager
        return ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    static class CustomAdapter extends BaseAdapter {
        private final Context mContext;
        private final int[] imageIds; // Debes cargar aquí las IDs de las imágenes desde /res/drawable/units

        public CustomAdapter(Context context, int[] imageIds) {
            this.mContext = context;
            this.imageIds = imageIds;
        }

        @Override
        public int getCount() {
            return imageIds.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;

            if (convertView == null) {
                // Si no hay una vista reutilizable, crea una nueva ImageView
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(200, 200)); // Ajusta el tamaño según tus necesidades
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            } else {
                imageView = (ImageView) convertView;
            }

            // Asigna la imagen a la ImageView
            imageView.setImageResource(imageIds[position]);

            return imageView;
        }
    }
}