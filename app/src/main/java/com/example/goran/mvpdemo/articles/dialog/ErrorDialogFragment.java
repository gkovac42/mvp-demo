package com.example.goran.mvpdemo.articles.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.goran.mvpdemo.R;

/**
 * Created by Goran on 22.11.2017..
 */

public class ErrorDialogFragment extends DialogFragment {

    public static ErrorDialogFragment newInstance() {

        return new ErrorDialogFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.dialog_error, container, false);

        Button btnDialog = v.findViewById(R.id.btn_dialog);
        btnDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return v;
    }
}
