package com.razawiyahdev.exerciseprescription;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.razawiyahdev.exerciseprescription.R;
import com.google.android.material.button.MaterialButton;

public class SafetyPrecaution extends Dialog implements
        View.OnClickListener {

    public Context c;
    public Button doneBtn;

    public SafetyPrecaution(@NonNull Context context) {
        super(context);
        this.c = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.activity_safety_precaution);
        doneBtn = findViewById(R.id.doneBtn);

        doneBtn.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}