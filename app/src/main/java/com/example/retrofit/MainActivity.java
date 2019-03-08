package com.example.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.retrofit.api.CEPService;
import com.example.retrofit.api.DataService;
import com.example.retrofit.model.CEP;
import com.example.retrofit.model.Foto;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView txtRecuperado;
    private Button btnRecuperar;
    private Retrofit retrofit;
    private List<Foto> listaFotos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtRecuperado = findViewById(R.id.txtResultado);
        btnRecuperar = findViewById(R.id.btnRecuperar);

        retrofit = new Retrofit.Builder()
                //.baseUrl("https://viacep.com.br/ws/")
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        btnRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //recuperarCepRetrofit();
                recuperarListaRetrofit();
            }
        });
    }

    private void recuperarCepRetrofit() {
        CEPService cepService = retrofit.create(CEPService.class);
        Call<CEP> call = cepService.recuperarCep("92030490");
        call.enqueue(new Callback<CEP>() {
            @Override
            public void onResponse(Call<CEP> call, Response<CEP> response) {
                if (response.isSuccessful()) {
                    CEP cep = response.body();
                    txtRecuperado.setText(cep.getLogradouro() + "-" + cep.getBairro());
                }
            }

            @Override
            public void onFailure(Call<CEP> call, Throwable t) {

            }
        });
    }

    private void recuperarListaRetrofit(){
        DataService service = retrofit.create(DataService.class);
        Call<List<Foto>> callFoto = service.recuperarFotos();

        callFoto.enqueue(new Callback<List<Foto>>() {
            @Override
            public void onResponse(Call<List<Foto>> call, Response<List<Foto>> response) {
                if(response.isSuccessful()){
                    listaFotos = response.body();

                    for(int x = 0; x < listaFotos.size(); x++){
                        Log.i("fotos", "foto = " + x + " - " + listaFotos.get(x).getTitle());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Foto>> call, Throwable t) {

            }
        });
    }
}
