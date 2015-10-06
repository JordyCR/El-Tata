package com.pid2.tata.rest;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by JordyCuan on 05/10/15.
 */
public interface TataRestApiInterface {
//
//	@GET("/app/login")
//	Call<User> getUser(@Query("username") String username, @Query("password") String password);
//
//
//	@PUT("/app/report/confirm/{id}")
//	Call<List<User>> groupList(@Path("id") int groupId, @Query("sort") String sort);
//
//
//	@DELETE("/app/login")
//	Call<User> createUser(@Body User user); //TODO: Enviar las cookies
}


/**
 * SERÁN Enviados con FormUrlEncoded
 *
 * (GET)
 * <dominio_tata>/app/login
 *
 * (PUT)
 * <dominio_tata>/app/report/confirm/{id}
 *
 * (DELETE)
 * <dominio_tata>/app/login
 */