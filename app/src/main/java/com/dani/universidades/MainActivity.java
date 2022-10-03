package com.dani.universidades;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;

public class MainActivity extends AppCompatActivity {

    TextView uniRes,webRes;
    Button boton;
    EditText pais,uni;
    String country,name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pais=findViewById(R.id.pais);
        uni=findViewById(R.id.uni);
        uniRes=findViewById(R.id.uniRes);
        webRes=findViewById(R.id.webRes);
        boton = (Button) findViewById(R.id.boton);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                country = String.valueOf(pais.getText());
                name = String.valueOf(uni.getText());

                LeerApi(country,name);
            }
        });
    }

    private void LeerApi(String country, String name) {




       // String url = "https://jsonplaceholder.typicode.com/posts/11";
        String url = "http://universities.hipolabs.com/search?country="+ country+"&name="+name+"";

        StringRequest postRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = new JSONObject(jsonArray.getString(0));
                    System.out.println(jsonObject);
                    System.out.println(jsonObject.getString("name"));
                    System.out.println(jsonObject.getString("country"));
                    System.out.println(jsonObject.getString("web_pages"));
                    String [] a= {jsonObject.getString("web_pages")};
                    String webUrl= a[0].replace("]","").replace("[","") .replace("\\","").replace("\"","");
                    System.out.println(webUrl);
                    String nom= jsonObject.getString("name");
                    uniRes.setText(nom);
                    webRes.setText(webUrl);


                } catch (JSONException e) {
                    System.out.println("NO HAY RESULTADOS!!!!!!!");
                   //e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
             //   System.out.println("NO HAY RESULTADOS!!!!!!!");
                Log.e("Error", error.getMessage());
            }
        });
        Volley.newRequestQueue(this).add(postRequest);
    }
}