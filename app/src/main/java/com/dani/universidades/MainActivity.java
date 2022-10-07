package com.dani.universidades;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

   // TextView uniRes,webRes;
    Context context;
    Button boton;
    EditText pais,uni;
    String country,name;
    List<Universidad> lista;
    JSONArray jsonArray;
    JSONObject jsonObject;
    com.dani.universidades.Request request ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pais=findViewById(R.id.pais);
        uni=findViewById(R.id.uni);
        boton = (Button) findViewById(R.id.boton);

        context = this.getApplicationContext();
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                country = String.valueOf(pais.getText());
                name = String.valueOf(uni.getText());


                new CountDownTimer(800, 800){
                    @Override
                    public void onTick(long l) {
                        try {
                           request = Request.getInstance(country,name,context);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    public  void onFinish(){
                         lista=request.getJsonArray();

                        for (Universidad u:lista) {
                            Log.i("MAIN", u.getWebPage());
                        }
                    }
                }.start();
            }
        });
    }
}