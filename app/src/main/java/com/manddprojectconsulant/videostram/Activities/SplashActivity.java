package com.manddprojectconsulant.videostram.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

import com.manddprojectconsulant.videostram.R;

public class SplashActivity extends AppCompatActivity {

    ImageView logo_image;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setExitTransition(null);
        setContentView(R.layout.activity_splash);

        //ID's
        logo_image = findViewById(R.id.logo_image);
        String transitionName = ViewCompat.getTransitionName(logo_image);
        Intent intent = new Intent(this, LoginActivity.class);
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this, logo_image, transitionName);

        logo_image.postDelayed(() -> {
            startActivity(intent, options.toBundle());
            ActivityCompat.finishAfterTransition(this);
        }, 3000);


    }


}