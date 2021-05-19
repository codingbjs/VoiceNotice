package com.codingbjs.voicenotice.voicedata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.codingbjs.voicenotice.R;
import com.codingbjs.voicenotice.databinding.ActivityVoiceItemViewPagerBinding;
import com.codingbjs.voicenotice.databinding.VoiceItemViewPagerItemBinding;
import com.codingbjs.voicenotice.util.ArgParamManager;

public class VoiceItemViewPagerActivity extends AppCompatActivity {

    ActivityVoiceItemViewPagerBinding viewPagerBinding;

    VoiceItemList voiceItemList;
    VoiceItemViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewPagerBinding = ActivityVoiceItemViewPagerBinding.inflate(getLayoutInflater());
        setContentView(viewPagerBinding.getRoot());

        voiceItemList = VoiceItemList.getInstance(getApplicationContext());
        viewPagerAdapter = new VoiceItemViewPagerAdapter(voiceItemList.getVoiceItemList());

        viewPagerBinding.voiceItemViewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPagerBinding.voiceItemViewPager.setAdapter(viewPagerAdapter);

        viewPagerAdapter.setDeleteButtonClickListener(new VoiceItemViewPagerAdapter.DeleteButtonClickListener() {
            @Override
            public void deleteButtonClick(int position) {
                VoiceItem voiceItem = viewPagerAdapter.getVoiceItem(position);
                voiceItemList.deleteVoiceItem(voiceItem);
                startActivity(ArgParamManager.getAllFlagsIntent(getApplicationContext(),
                        VoiceItemListActivity.class,
                        ArgParamManager.DELETE_VOICE_ITEM,
                        String.valueOf(position)));
                finish();
            }
        });

        viewPagerAdapter.setModifyButtonClickListener(new VoiceItemViewPagerAdapter.ModifyButtonClickListener() {
            @Override
            public void modifyButtonClick(VoiceItemViewPagerItemBinding viewPagerItemBinding, int position) {
                if (viewPagerItemBinding.titleEditText.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), R.string.title_hint, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (viewPagerItemBinding.voiceTextEditText.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), R.string.voice_text_hint, Toast.LENGTH_SHORT).show();
                    return;
                }

                VoiceItem voiceItem = viewPagerAdapter.getVoiceItem(position);
                voiceItem.setTitle(viewPagerItemBinding.titleEditText.getText().toString());
                voiceItem.setVoice(viewPagerItemBinding.voiceTextEditText.getText().toString());

                voiceItemList.updateVoiceItem(voiceItem);
                startActivity(ArgParamManager.getAllFlagsIntent(getApplicationContext(),
                        VoiceItemListActivity.class,
                        ArgParamManager.MODIFY_VOICE_ITEM,
                        String.valueOf(position)));
                finish();
            }
        });


        if(getIntent() != null){
            onNewIntent(getIntent());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent != null){
            String param1 = intent.getStringExtra(ArgParamManager.ARG_PARAM1);
            String param2 = intent.getStringExtra(ArgParamManager.ARG_PARAM2);
            if (param1 != null) resultParamCheck(param1, param2);
        }
    }

    private void resultParamCheck(String param1, String param2) {
        switch (param1){
            case ArgParamManager.MOVE_VIEW_PAGER:
                viewPagerBinding.voiceItemViewPager.setCurrentItem(Integer.parseInt(param2));
                break;
        }
    }
}