package com.johnkagga.jkconverter;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ConversionDialogFragment extends DialogFragment {

    public static ConversionDialogFragment newInstance() {
        return new ConversionDialogFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.currency_dialog_title);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.content_add_conversion, null);

        setUpSpinner(rootView);

        builder.setView(rootView)
                .setPositiveButton(R.string.currency_dialog_positive_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton(R.string.currency_dialog_negative_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ConversionDialogFragment.this.getDialog().dismiss();
                    }
                });
        return builder.create();
    }

    /**
     * Set up the currency type spinner
     *
     * @param rootView View
     */
    private void setUpSpinner(View rootView) {
        Spinner spinner = rootView.findViewById(R.id.currency_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.currency_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
