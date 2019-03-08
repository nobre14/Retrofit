package com.example.retrofit.api;

import com.example.retrofit.model.Foto;
import com.example.retrofit.model.Postagem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DataService {

    @GET("/photos")
    Call<List<Foto>> recuperarFotos();

    @GET("/posts")
    Call<List<Postagem>> recuperarPostagem();
}
