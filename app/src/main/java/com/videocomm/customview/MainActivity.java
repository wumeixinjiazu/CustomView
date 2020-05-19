package com.videocomm.customview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.videocomm.customview.view.WaterFallLayout;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static int IMG_COUNT = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final WaterFallLayout waterFallLayout = findViewById(R.id.waterfallLayout);
        findViewById(R.id.add_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addView(waterFallLayout);
            }
        });
    }

    private void addView(WaterFallLayout waterFallLayout) {
        Random random = new Random();
        Integer num = Math.abs(random.nextInt());
        WaterFallLayout.LayoutParams layoutParams = new WaterFallLayout.LayoutParams(WaterFallLayout.LayoutParams.WRAP_CONTENT,
                WaterFallLayout.LayoutParams.WRAP_CONTENT);
        ImageView imageView = new ImageView(MainActivity.this);
        if (num % IMG_COUNT == 0) {
            imageView.setImageResource(R.drawable.bg_risk_report);
        } else if (num % IMG_COUNT == 1) {
            imageView.setImageResource(R.drawable.ic_file_upload);
        } else if (num % IMG_COUNT == 2) {
            imageView.setImageResource(R.drawable.ic_red_choose);
        } else if (num % IMG_COUNT == 3) {
            imageView.setImageResource(R.mipmap.ic_launcher);
        } else if (num % IMG_COUNT == 4) {
            imageView.setImageResource(R.mipmap.ic_launcher);
        }
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        waterFallLayout.addView(imageView, layoutParams);

    }
}
