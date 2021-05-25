package com.codingbjs.voicenotice.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.codingbjs.voicenotice.MainActivity;
import com.codingbjs.voicenotice.R;
import com.codingbjs.voicenotice.databinding.ActivityCarMoveBinding;
import com.codingbjs.voicenotice.util.ArgParamManager;

public class CarMoveActivity extends AppCompatActivity {

    ActivityCarMoveBinding carMoveBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        carMoveBinding = ActivityCarMoveBinding.inflate(getLayoutInflater());
        setContentView(carMoveBinding.getRoot());

        carMoveBinding.confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String carNum = carMoveBinding.carNumEdit.getText().toString();
                String carColor = carMoveBinding.carColorEdit.getText().toString();
                String carModel = carMoveBinding.carModelEdit.getText().toString();
                String carInfoText = getCarInfo(carNum, carColor, carModel);

                startActivity(ArgParamManager.getAllFlagsIntent(getApplicationContext(),
                            MainActivity.class, ArgParamManager.GET_VOICE_ITEM, carInfoText));

                finish();
            }
        });
    }

    private String getCarInfo (String carNum, String carColor, String carModel) {
        if(carNum == null){
            carNum = "";
        }else if(carNum.length() > 1){
            carNum += ". ";
        }

        if(carColor == null){
            carColor = "";
        }else if(carColor.length() > 1){
            carColor += ", ";
        }

        if(carModel == null){
            carModel = "";
        }else if(carModel.length() > 1){
            carModel += ", ";
        }

        String startText =  "관리실에서 안내드립니다.\n";
        String carInfoText =  "차량번호 " + carNum + carColor + carModel +
                              "차주분께서는,\n" + "다른 장소로 이동 주차 하여 주십시요.\n";
        String restartText = "다시한번 안내드립니다.\n";
        String endText = "감사합니다.\n";

        return startText + carInfoText + restartText + carInfoText + endText;
    }
}