package com.dani.universidades;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Request {

    private static Request instancia;  //private static ArrayList<Producto> listaProductos;

    private JSONArray jsonArray;
    private JSONObject jsonObject;
    private List<Universidad> lista;
    private static String url;
    private final Request request =this;
    private Context context;

    public static Request getInstance(Context context) throws IOException {

        if (instancia == null) {
            instancia = new Request(context);
        }
        return instancia;
    }

    private Request(Context context){
        this.context=context;
    }

    public List<Universidad> getListaUniversidades(){

        return lista;
    }


    public void nuevaBusqueda(String country,String name) throws IOException {

        url= "http://universities.hipolabs.com/search?country="+ country+"&name="+name+"";

        StringRequest postRequest =  new StringRequest(com.android.volley.Request.Method.GET, url,new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                    lista= new ArrayList<Universidad>(){};
                try {
                    jsonArray = new JSONArray(response);
                    for (int i = 0; i <jsonArray.length() ; i++) {
                        try {
                            jsonObject = new JSONObject(jsonArray.getString(i));
                            Universidad universidad = new Universidad();
                            universidad.setCountry(jsonObject.getString("country"));
                            universidad.setName(jsonObject.getString("name"));
                            universidad.setCodPais(jsonObject.getString("alpha_two_code"));
                            String[] webArray= {jsonObject.getString("web_pages")};
                            String webUrl= webArray[0].replace("]","").replace("[","") .replace("\\","").replace("\"","");
                            universidad.setWebPage(webUrl);
                            lista.add(universidad);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.getMessage());
                    }
                });
        Volley.newRequestQueue(context).add(postRequest);

    }

}
