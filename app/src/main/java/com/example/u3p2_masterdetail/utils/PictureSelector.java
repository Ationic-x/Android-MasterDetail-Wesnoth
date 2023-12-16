package com.example.u3p2_masterdetail.utils;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.example.u3p2_masterdetail.R;
import com.example.u3p2_masterdetail.fragments.NewUnitFragment;
import com.example.u3p2_masterdetail.viewmodel.UnitsViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

import static androidx.core.content.ContentProviderCompat.requireContext;


public class PictureSelector {
    private final List<Integer> pictures;
    private final Context context;
    private final CalculatorWindow calculatorWindow;
    private BottomSheetDialog bottomSheetDialog;
    private final View pictureSelectorView;
    private final GridView gridViewImages;
    private FrameLayout bottomSheet;

    private UnitsViewModel unitsViewModel;

    public PictureSelector(List<Integer> pictures, Context context, UnitsViewModel unitsViewModel){
        this.pictures = pictures;
        this.context = context;
        this.calculatorWindow = new CalculatorWindow(context);
        this.unitsViewModel = unitsViewModel;
        pictureSelectorView = LayoutInflater.from(context).inflate(R.layout.picture_selector, null);
        gridViewImages = pictureSelectorView.findViewById(R.id.gvImages);
        initBottomSheet();
    }

    private void initBottomSheet(){
        bottomSheetDialog = new BottomSheetDialog(context);
        PictureSelectorAdapter psAdaprter = new PictureSelectorAdapter(context, pictures);
        gridViewImages.setAdapter(psAdaprter);
        bottomSheetDialog.setContentView(pictureSelectorView);
        bottomSheet = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
        if(bottomSheet != null) {
            setBottomSheetBehavior();
            setGridViewBehavior();
        }
    }

    private void setBottomSheetBehavior() {
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomSheet.getLayoutParams();
        layoutParams.height = calculatorWindow.calculateHeight(0.50);
        bottomSheet.setLayoutParams(layoutParams);

        BottomSheetBehavior.from(bottomSheet).addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
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

    private void setGridViewBehavior(){
        gridViewImages.setOnItemClickListener((parent, view, position, id) -> {
            bottomSheetDialog.cancel();
            unitsViewModel.setUnitImage(pictures.get(position));
        });
    }

    public void show(){
        bottomSheetDialog.show();
    }

    static class PictureSelectorAdapter extends BaseAdapter {
        private final Context context;
        private final List<Integer> imageIds;

        public PictureSelectorAdapter(Context context, List<Integer> imageIds) {
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

            Bitmap originalBitmap = BitmapFactory.decodeResource(context.getResources(), imageIds.get(position));
            float aspectRatio = (float) originalBitmap.getWidth() / originalBitmap.getHeight();
            int dstHeight = (int) (200 / aspectRatio);
            int dstWidth = (int) (200 / aspectRatio);
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, dstWidth, dstHeight, false);
            imageView.setImageBitmap(resizedBitmap);
            imageView.setScaleType(ImageView.ScaleType.CENTER);

            imageView.setBackgroundResource(R.drawable.frame);
            return imageView;
        }
    }
}
