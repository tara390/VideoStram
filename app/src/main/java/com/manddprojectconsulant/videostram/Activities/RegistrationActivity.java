package com.manddprojectconsulant.videostram.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.manddprojectconsulant.videostram.PublicApi.APi;
import com.manddprojectconsulant.videostram.R;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {


    TextInputEditText fullname_edittext, phone_edittext, email_edittext, pass_edittext;
    ImageView signup_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //ID's
        fullname_edittext = findViewById(R.id.fullname_edittext);
        phone_edittext = findViewById(R.id.phone_edittext);
        email_edittext = findViewById(R.id.email_edittext);
        pass_edittext = findViewById(R.id.pass_edittext);
        signup_button = findViewById(R.id.signup_button);


        //Press the Image
        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringRequest stringRequest = new StringRequest(Request.Method.POST, APi.Registration, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(RegistrationActivity.this, "Success", Toast.LENGTH_SHORT).show();



                        //email_edittext.setText("");


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(RegistrationActivity.this, "Fault in Internet Please Check" + error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }) {

                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        HashMap map = new HashMap();
                        map.put("email", email_edittext.getText().toString());
                        map.put("name", fullname_edittext.getText().toString());
                        map.put("password", pass_edittext.getText().toString());
                        map.put("phone", phone_edittext.getText().toString());


                        return map;
                    }




                };






                RequestQueue queue = Volley.newRequestQueue(RegistrationActivity.this);
                queue.add(stringRequest);

                //Toast.makeText(RegistrationActivity.this, "button click", Toast.LENGTH_SHORT).show();


            }
        });


    }


}