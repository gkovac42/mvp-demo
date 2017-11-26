package com.example.goran.mvpdemo.articles.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by Goran on 22.11.2017..
 */

public class ErrorDialogFragment extends DialogFragment {

    public static ErrorDialogFragment newInstance() {

        return new ErrorDialogFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new AlertDialog.Builder(getActivity())
                .setTitle("Greška")
                .setMessage("Ups, došlo je do pogreške.")
                .setPositiveButton("U redu", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                })

                .create();
    }
}
