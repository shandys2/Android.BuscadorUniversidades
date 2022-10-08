package com.dani.universidades;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    Context context;
    Button boton,atras;
    EditText pais,uni;
    String country,name;
    List<Universidad> lista;
    Request request ;
    RecyclerView recyclerView;
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView= findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new AdaptadorUniversidades());
        recyclerView.setVisibility(View.GONE);
        context = this.getApplicationContext();
        try {
            request = Request.getInstance(context);
        } catch (IOException e) {
            e.printStackTrace();
        }

        pais=findViewById(R.id.pais);
        uni=findViewById(R.id.uni);
        boton =findViewById(R.id.boton);
        atras =findViewById(R.id.atras);
        webview= findViewById(R.id.webView);
        atras.setVisibility(View.GONE);
        webview.setVisibility(View.GONE);

        webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return false;
            }
        });
        webview.getSettings().setJavaScriptEnabled(true);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                country = String.valueOf(pais.getText());
                name = String.valueOf(uni.getText());

                //ponemos un contador a falta de hacerla sincrona la peticion
                new CountDownTimer(800, 800){
                    @Override
                    public void onTick(long l) {
                        try {
                           recyclerView.setVisibility(View.GONE);
                           request.nuevaBusqueda(country,name);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    public  void onFinish(){

                        lista=request.getListaUniversidades();
                        if(lista.size()==0){
                            lista.add(new Universidad("No hay resultados"));
                        }
                        recyclerView.setAdapter(new AdaptadorUniversidades());
                        for (Universidad u:lista) {
                            Log.i("MAIN", u.getName());
                        }
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                }.start();
            }
        });

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atras.setVisibility(View.GONE);
                boton.setVisibility(View.VISIBLE);
                webview.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });

    }

    //Clases necesarias para que el RecyclerView funcione
    private class AdaptadorUniversidades extends RecyclerView.Adapter<AdaptadorUniversidades.AdaptadorUniversidadesHolder>{

        @NonNull
        @Override
        public AdaptadorUniversidadesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new AdaptadorUniversidadesHolder(getLayoutInflater().inflate(R.layout.itemuniversidad,parent,false));
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onBindViewHolder(@NonNull AdaptadorUniversidadesHolder holder, int position) {
                holder.imprimir(position);
        }

        @Override
        public int getItemCount() {
            return lista.size();
        }

        private class AdaptadorUniversidadesHolder extends RecyclerView.ViewHolder{
            TextView itemTxt;
            public AdaptadorUniversidadesHolder(@NonNull View itemView) {
                super(itemView);
                itemTxt=itemView.findViewById(R.id.itemTxt);
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            public void imprimir(int position) {
                    itemTxt.setText(lista.get(position).getName());
                    itemTxt.setTooltipText(lista.get(position).getWebPage());
                    itemTxt.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        webview.loadUrl((String) v.getTooltipText());
                        webview.setVisibility(View.VISIBLE);
                        atras.setVisibility(View.VISIBLE);
                        boton.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                    }
                });
            }

        }
    }
}