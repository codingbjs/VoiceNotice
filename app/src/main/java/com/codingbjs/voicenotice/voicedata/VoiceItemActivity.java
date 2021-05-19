package com.codingbjs.voicenotice.voicedata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.codingbjs.voicenotice.R;
import com.codingbjs.voicenotice.databinding.ActivityVoiceItemBinding;
import com.codingbjs.voicenotice.util.ArgParamManager;

public class VoiceItemActivity extends AppCompatActivity {

    private ActivityVoiceItemBinding voiceItemBinding;

    private VoiceItemList voiceItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        voiceItemBinding = ActivityVoiceItemBinding.inflate(getLayoutInflater());
        setContentView(voiceItemBinding.getRoot());

        voiceItemList = VoiceItemList.getInstance(getApplicationContext());

        onNewIntent(getIntent());

        voiceItemBinding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        voiceItemBinding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveButtonClick();
            }
        });

    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            String param1 = intent.getStringExtra(ArgParamManager.ARG_PARAM1);
            String param2 = intent.getStringExtra(ArgParamManager.ARG_PARAM2);

            if (param1 != null) resultParamCheck(param1, param2);
        }
    }

    private void resultParamCheck(String param1, String param2) {
        switch (param1) {
            case ArgParamManager.NEW_VOICE_ITEM:
                if (param2 != null) {
                    voiceItemBinding.voiceTextEditText.setText(param2);
                }

                break;

            default:
                break;
        }
    }


    private void saveButtonClick() {
        if (voiceItemBinding.titleEditText.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), R.string.title_hint, Toast.LENGTH_SHORT).show();
            return;
        }
        if (voiceItemBinding.voiceTextEditText.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), R.string.voice_text_hint, Toast.LENGTH_SHORT).show();
            return;
        }

        String title = voiceItemBinding.titleEditText.getText().toString();
        String voice = voiceItemBinding.voiceTextEditText.getText().toString();
        long date = System.currentTimeMillis();

        VoiceItem voiceItem = new VoiceItem(date, title, voice);
        voiceItemList.addVoiceItem(voiceItem);
        Toast.makeText(getApplicationContext(), R.string.voice_save_success, Toast.LENGTH_SHORT).show();

        finish();
    }
}