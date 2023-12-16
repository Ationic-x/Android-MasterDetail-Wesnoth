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

    // Constructor that initializes the dialog with a NumberPicker
    public DialogNumberPicker(int minValue, int maxValue, Context context) {
        // Inflate the layout for the dialog
        View view = LayoutInflater.from(context).inflate(R.layout.number_picker_layout, null);

        // Initialize the NumberPicker with min and max values
        numberPicker = view.findViewById(R.id.number_picker);
        numberPicker.setMinValue(minValue);
        numberPicker.setMaxValue(maxValue);

        // Initialize the AlertDialog.Builder with the inflated view
        builder = new AlertDialog.Builder(context);
        builder.setView(view);
    }

    // Initialize the dialog with title, positive/negative buttons, and event handlers
    private void initDialog() {
        // Set the initial value of the NumberPicker to the value in the EditText
        numberPicker.setValue(Integer.parseInt(editText.getText().toString()));

        builder
                .setTitle("Select a number")
                .setPositiveButton("Accept", (dialog, which) ->
                        // Set the EditText value to the selected value in the NumberPicker
                        editText.setText(String.valueOf(numberPicker.getValue())))
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .setCancelable(false);

        // Create the dialog if it hasn't been created yet
        if (dialog == null) dialog = builder.create();
    }

    // Show the dialog with the associated EditText
    public void show(EditText editText) {
        this.editText = editText;
        initDialog();
        dialog.show();
    }
}