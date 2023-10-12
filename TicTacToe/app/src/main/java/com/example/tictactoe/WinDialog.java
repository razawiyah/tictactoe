package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WinDialog extends Dialog {

    String message;
    MainActivity mainActivity;

    public WinDialog(@NonNull Context context) {
        super(context);
    }

    public WinDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected WinDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public WinDialog(@NonNull Context context, String message, MainActivity mainActivity){
        super(context);
        this.message = message;
        this.mainActivity = mainActivity;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win_dialog);

        TextView messageTxt = findViewById(R.id.messageTxt);
        Button playBtn = findViewById(R.id.playBtn);

        messageTxt.setText(message);

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.restartGame();
                dismiss();
            }
        });
    }
}