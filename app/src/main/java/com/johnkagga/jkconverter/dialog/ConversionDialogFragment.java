package com.johnkagga.jkconverter.dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.johnkagga.jkconverter.R;


public class ConversionDialogFragment extends DialogFragment {
    private RadioGroup mRadioGroup;
    private String mBaseCurrency;
    private OnCurrencyConversion mListener;

    /**
     * Interface to pass on the chosen coin and currency to the activity.
     */
    public interface OnCurrencyConversion {
        void onCurrencyAdded(String coin, String currency);
    }

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

        mRadioGroup = rootView.findViewById(R.id.coin_group);

        setUpSpinner(rootView);

        builder.setView(rootView)
                .setPositiveButton(R.string.currency_dialog_positive_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onCurrencyAdded(getChosenCoin(), mBaseCurrency);
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
     * Get the chosen coin from the Radio Group.
     */
    private String getChosenCoin() {
        int checkedRadioButtonId = mRadioGroup.getCheckedRadioButtonId();
        RadioButton checkedRadioButton = mRadioGroup.findViewById(checkedRadioButtonId);
        return (String) checkedRadioButton.getText();
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
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mBaseCurrency = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Nothing selected
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnCurrencyConversion) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnCurrencyConversion Interface");
        }
    }
}
