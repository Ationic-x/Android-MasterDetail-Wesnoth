package com.example.u3p2_masterdetail;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.*;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.u3p2_masterdetail.databinding.FragmentNewUnitBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

// Class related to create new units screen
public class NewUnitFragment extends Fragment {

    private FragmentNewUnitBinding binding;
    private UnitsViewModel unitsViewModel;


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
        unitsViewModel = new ViewModelProvider(requireActivity()).get(UnitsViewModel.class);
        // NavController navigate between fragments
        NavController navController = Navigation.findNavController(view);

        getImage().observe(getViewLifecycleOwner(), image -> binding.ivUnit.setImageResource(image));

        // Defined the behavior of the button (on click event)
        binding.btnCreate.setOnClickListener(v -> {
            // Get values in the view
            String name = binding.etName.getText().toString();
            String description = binding.etDescription.getText().toString();
            int cost =  Integer.parseInt(binding.etCost.getText().toString());
            int hp =  Integer.parseInt(binding.etHp.getText().toString());
            int xp =  Integer.parseInt(binding.etXp.getText().toString());
            int mp =  Integer.parseInt(binding.etMp.getText().toString());
            // Insert new view model
            unitsViewModel.insert(new Unit(name, description, unitsViewModel.getUnitImage().getValue(), cost, hp, xp, mp));
            // Return to the last fragment
            navController.popBackStack();
        });

        binding.flUnit.setOnClickListener(v -> showPictureSelector());

        binding.etCost.setOnClickListener(v -> mostrarDialogoConNumberPicker(binding.etCost));
        binding.etHp.setOnClickListener(v -> mostrarDialogoConNumberPicker(binding.etHp));
        binding.etXp.setOnClickListener(v -> mostrarDialogoConNumberPicker(binding.etXp));
        binding.etMp.setOnClickListener(v -> mostrarDialogoConNumberPicker(binding.etMp));
    }

    protected LiveData<Integer> getImage(){
        return unitsViewModel.getUnitImage();
    }

    private void mostrarDialogoConNumberPicker(EditText editText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.number_picker_layout, null);

        final NumberPicker numberPicker = view.findViewById(R.id.number_picker);

        // Configura el rango de valores del NumberPicker
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(300);

        builder.setView(view)
                .setTitle("Selecciona un número")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Obtiene el valor seleccionado
                        editText.setText(String.valueOf(numberPicker.getValue()));
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // Cierra el diálogo
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showPictureSelector() {
        // Crea un BottomSheetDialog
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
        View menuDesplegableView = getLayoutInflater().inflate(R.layout.picture_selector, null);

        GridView gridViewImages = menuDesplegableView.findViewById(R.id.gvImages);
        // Carga las IDs de las imágenes desde /res/drawable/units
        List<Integer> drawablesInUnits = getDrawables("unit");

        // Configura el adaptador para el GridView
        CustomAdapter customAdapter = new CustomAdapter(requireContext(), drawablesInUnits);
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


        gridViewImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bottomSheetDialog.cancel();
                unitsViewModel.setUnitImage(drawablesInUnits.get(position));
            }
        });
    }

    public static List<Integer> getDrawables(String type) {
        List<Integer> drawableList = new ArrayList<>();
        try {
            Field[] fields = R.drawable.class.getFields();
            for (Field field : fields) {
                if (field.getName().startsWith(type + "_")) {
                    Object value =  field.get(R.drawable.class);
                    if (value instanceof Integer) {
                        int resourceId = (int) value;
                        drawableList.add(resourceId);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return drawableList;
    }

    private int obtenerAlturaDeseada() {
        WindowManager windowManager = (WindowManager) requireContext().getSystemService(Context.WINDOW_SERVICE);

        if (windowManager != null) {
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            int screenHeight = displayMetrics.heightPixels;

            // Calcula el 33% de la altura de la pantalla
            return (int) (screenHeight * 0.50);
        }

        // En caso de que no se pueda obtener el WindowManager
        return ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    class CustomAdapter extends BaseAdapter {
        private final Context context;
        private final List<Integer> imageIds; // Debes cargar aquí las IDs de las imágenes desde /res/drawable/units

        public CustomAdapter(Context context, List<Integer> imageIds) {
            this.context = context;
            this.imageIds = imageIds;
        }

        @Override
        public int getCount() {
            return imageIds.size();
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
                imageView = new ImageView(context);
                imageView.setLayoutParams(new GridView.LayoutParams(275, 275));
            } else {
                imageView = (ImageView) convertView;
            }

            Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), imageIds.get(position));
            float aspectRatio = (float) originalBitmap.getWidth() / originalBitmap.getHeight();
            int dstHeight = (int)(200 / aspectRatio);
            int dstWidth = (int)(200 / aspectRatio);
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, dstWidth, dstHeight, false); // Cambia las dimensiones según tus preferencias
            imageView.setImageBitmap(resizedBitmap);
            imageView.setScaleType(ImageView.ScaleType.CENTER);

            imageView.setBackgroundResource(R.drawable.frame);
            return imageView;
        }
    }
}