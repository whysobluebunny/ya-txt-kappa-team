package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatSpinner;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Класс представляющий Spinner с множественным выбором.
 */
public class MultiSelectionSpinner extends AppCompatSpinner implements DialogInterface.OnMultiChoiceClickListener {

    private String[] items = null;
    private boolean[] selection = null;
    private ArrayAdapter adapter;

    public MultiSelectionSpinner(Context context) {
        super(context);
        adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item);
        super.setAdapter(adapter);
    }

    public MultiSelectionSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);

        adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item);
        super.setAdapter(adapter);
    }

    @Override
    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        if (selection != null && which < selection.length) {
            selection[which] = isChecked;

            adapter.clear();
            adapter.add(buildSelectedItemString());
        } else {
            throw new IllegalArgumentException("Argument 'which' is out of bounds.");
        }
    }

    @Override
    public boolean performClick() {
        super.performClick();
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMultiChoiceItems(items, selection, this);
        builder.setPositiveButton("OK", (arg0, arg1) -> {
            // Do nothing
        });
        builder.show();
        return true;
    }

    @Override
    public void setAdapter(SpinnerAdapter adapter) {
    }

    public void setItems(String[] items) {
        this.items = items;
        selection = new boolean[this.items.length];
        adapter.clear();
        adapter.add("");
        Arrays.fill(selection, false);
    }

    @Override
    public void setSelection(int position) {
        super.setSelection(position);
        selection[position] = true;
        adapter.clear();
        adapter.add(buildSelectedItemString());
    }

    /**
     *
     * @return строку с выбранными элементами.
     */
    private String buildSelectedItemString() {
        StringBuilder sb = new StringBuilder();
        boolean foundOne = false;

        for (int i = 0; i < items.length; ++i) {
            if (selection[i]) {
                if (foundOne) {
                    sb.append(", ");
                }

                foundOne = true;

                sb.append(items[i]);
            }
        }
        return sb.toString();
    }

    /**
     *
     * @return список выбранных элементов.
     */
    public ArrayList<String> getSelectedItemsList() {
        ArrayList<String> selectedItems = new ArrayList<>();
        for (int i = 0; i < items.length; ++i) {
            if (selection[i]) {
                selectedItems.add(items[i]);
            }
        }
        return selectedItems;
    }

    /**
     *
     * @return массив выбранных элементов.
     */
    public String[] getSelectedItemsArray() {
        ArrayList<String> selectedItemsList = new ArrayList<>();
        for (int i = 0; i < items.length; ++i) {
            if (selection[i]) {
                selectedItemsList.add(items[i]);
            }
        }
        String[] selectedItems = new String[selectedItemsList.size()];
        for (int i = 0; i < selectedItems.length; i++) {
            selectedItems[i] = selectedItemsList.get(i);
        }
        return selectedItems;
    }
}