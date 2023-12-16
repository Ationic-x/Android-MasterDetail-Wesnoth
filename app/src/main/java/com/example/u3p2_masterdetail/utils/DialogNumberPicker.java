package com.example.u3p2_masterdetail.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import com.example.u3p2_masterdetail.R;

public class DialogNumberPicker {

    private final AlertDialog.Builder builder;
    private EditText editText;
    private final NumberPicker numberPicker;
    private AlertDialog dialog;

    public DialogNumberPicker(int minValue, int maxValue, Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.number_picker_layout, null);
        numberPicker = view.findViewById(R.id.number_picker);
        numberPicker.setMinValue(minValue);
        numberPicker.setMaxValue(maxValue);
        builder = new AlertDialog.Builder(context);
        builder.setView(view);
    }

    private void initDialog() {
        numberPicker.setValue(0);
        builder
                .setTitle("Select a number")
                .setPositiveButton("Accept", (dialog, which) ->
                        editText.setText(String.valueOf(numberPicker.getValue())))
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .setCancelable(false);
        if(dialog == null) dialog = builder.create();
    }

    public void show(EditText editText) {
        this.editText = editText;
        initDialog();
        dialog.show();
    }
}