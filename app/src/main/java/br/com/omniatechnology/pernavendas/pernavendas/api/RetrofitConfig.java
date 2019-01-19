package br.com.omniatechnology.pernavendas.pernavendas.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.com.omniatechnology.pernavendas.pernavendas.utils.CalendarDeserializer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitConfig {

    public static final String BASE_URL = "192.168.1.85:8080/";

    public static Retrofit getBuilder(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }


    public static Retrofit getBuilderData(String pattern){

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Calendar.class, new CalendarDeserializer(new SimpleDateFormat(pattern)));

        Gson gson = gsonBuilder.create();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }


}
