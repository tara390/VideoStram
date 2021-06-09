package com.manddprojectconsulant.videostram.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.manddprojectconsulant.videostram.Adapter.AdapterforDashboard;
import com.manddprojectconsulant.videostram.Model.ModelforDashboard;
import com.manddprojectconsulant.videostram.PublicApi.APi;
import com.manddprojectconsulant.videostram.R;
import com.manddprojectconsulant.videostram.databinding.ActivityDashboardBinding;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {


    ActivityDashboardBinding activityDashboardBinding;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth firebaseAuth;


    public final static List<ModelforDashboard> list = new ArrayList<>();
    AdapterforDashboard adapterforDashboard;
    SharedPreferences sharedPreferences;
    public static final int PERMISSION_READ = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDashboardBinding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(activityDashboardBinding.getRoot());

        setTitle("VideoStram");
        setTitleColor(R.color.white);
        // using toolbar as ActionBar
        setSupportActionBar(activityDashboardBinding.toolbar);


        //Firebase Auth
        mGoogleSignInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN);
        firebaseAuth = FirebaseAuth.getInstance();

        //-------------------- Real time Permission and Data Pass ----------------------

        getAllData();


    }

    private void getAllData() {

        if (checkPermission()) {
            videoList();
        }




    }

    private boolean checkPermission() {

        int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_READ);
            return false;
        }
        return true;

    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_READ: {
                if (grantResults.length > 0 && permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(getApplicationContext(), "Please allow storage permission", Toast.LENGTH_LONG).show();
                    } else {
                        videoList();
                    }
                }
            }
        }
    }




    private void videoList() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(DashboardActivity.this);
        activityDashboardBinding.recyclerviewforgallery.setLayoutManager(layoutManager);

        getVideoFromGallery();


    }

    private void getVideoFromGallery() {

        ContentResolver contentResolver = getContentResolver();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        int thumb;
        Cursor cursor = contentResolver.query(uri, null, null, null, null);

        //looping through all rows and adding to list
        if (cursor != null && cursor.moveToFirst()) {
            do {

                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE));
                String duration = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
                String data = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                thumb=cursor.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA);
                ModelforDashboard videoModel = new ModelforDashboard();
                videoModel.setTitle(title);
                videoModel.setVideouri(Uri.parse(data));
                videoModel.setDuration(timeConversion(Long.parseLong(duration)));
                videoModel.setThumb(cursor.getString(thumb));
                list.add(videoModel);

            } while (cursor.moveToNext());

        }

        adapterforDashboard=new AdapterforDashboard(DashboardActivity.this,list);
        activityDashboardBinding.recyclerviewforgallery.setAdapter(adapterforDashboard);

        adapterforDashboard.setOnItemClickListener(new AdapterforDashboard.OnItemClickListener() {
            @Override
            public void onItemClick(int pos, View v,String title) {

                Intent videoshow=new Intent(DashboardActivity.this,VideoShowActivity.class);
                videoshow.putExtra("getpostion",pos);
                videoshow.putExtra("getTitle",title);
                startActivity(videoshow);


            }
        });


    }

    private String timeConversion(long parseLong) {

        String videoTime;
        int dur = (int) parseLong;
        int hrs = (dur / 3600000);
        int mns = (dur / 60000) % 60000;
        int scs = dur % 60000 / 1000;

        if (hrs > 0) {
            videoTime = String.format("%02d:%02d:%02d", hrs, mns, scs);
        } else {
            videoTime = String.format("%02d:%02d", mns, scs);
        }
        return videoTime;


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.logout, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_logout) {

            logoutcode();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void logoutcode() {


        firebaseAuth.signOut();

        mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task task) {

                Intent intent=new Intent(DashboardActivity.this,LoginActivity.class);
                startActivity(intent);

            }
        });


    }
}