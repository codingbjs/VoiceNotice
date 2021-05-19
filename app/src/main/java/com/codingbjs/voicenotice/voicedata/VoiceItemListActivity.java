package com.codingbjs.voicenotice.voicedata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.codingbjs.voicenotice.MainActivity;
import com.codingbjs.voicenotice.R;
import com.codingbjs.voicenotice.databinding.ActivityVoiceItemListBinding;
import com.codingbjs.voicenotice.util.ArgParamManager;

public class VoiceItemListActivity extends AppCompatActivity {

    private ActivityVoiceItemListBinding voiceItemListBinding;

    private VoiceItemList voiceItemList;
    private VoiceItemListAdapter voiceItemListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        voiceItemListBinding = ActivityVoiceItemListBinding.inflate(getLayoutInflater());
        setContentView(voiceItemListBinding.getRoot());

        voiceItemList = VoiceItemList.getInstance(getApplicationContext());

        voiceItemListBinding.voiceItemRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        voiceItemListBinding.voiceItemRecyclerView.setLayoutManager(layoutManager);

        voiceItemListAdapter = new VoiceItemListAdapter(voiceItemList.getVoiceItemList());

        voiceItemListBinding.voiceItemRecyclerView.setAdapter(voiceItemListAdapter);

        voiceItemListAdapter.setOnItemClickListener(new VoiceItemListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                VoiceItem voiceItem = voiceItemListAdapter.getVoiceItem(position);
                startActivity(ArgParamManager.getAllFlagsIntent(getApplicationContext(),
                        MainActivity.class,
                        ArgParamManager.GET_VOICE_ITEM,
                        voiceItem.getVoice()));
                finish();
            }
        });

        voiceItemListAdapter.setOnItemLongClickListener(new VoiceItemListAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View v, int position) {
                startActivity(ArgParamManager.getAllFlagsIntent(getApplicationContext(),
                        VoiceItemViewPagerActivity.class,
                        ArgParamManager.MOVE_VIEW_PAGER,
                        String.valueOf(position)));
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
            case ArgParamManager.MODIFY_VOICE_ITEM:
            case ArgParamManager.DELETE_VOICE_ITEM:
                voiceItemListAdapter.setVoiceItems(voiceItemList.getVoiceItemList());
                break;
        }
    }

}