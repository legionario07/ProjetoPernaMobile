package br.com.omniatechnology.pernavendas.pernavendas.utils;

import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by PauLinHo on 26/07/2017.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author PauLinHo
 */
public class CalendarDeserializer implements JsonDeserializer<Calendar>, JsonSerializer<Calendar> {


    private SimpleDateFormat JSON_STRING_CALENDAR;

    public CalendarDeserializer(SimpleDateFormat sdf) {
        this.JSON_STRING_CALENDAR = sdf;
    }

    @Override
    public JsonElement serialize(Calendar calendar, Type type, JsonSerializationContext jsonSerializationContext) {
        String dataAsString = JSON_STRING_CALENDAR.format(calendar.getTime());

        return new JsonPrimitive(calendar.getTimeInMillis());
    }


    @Override
    public Calendar deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        Calendar c = Calendar.getInstance();
        String value = jsonElement.getAsString();

        Date date = new Date();
        try {
            date = JSON_STRING_CALENDAR.parse(value);
        }catch(Exception e){
            Log.e("POKER",e.getMessage());
        }

//        Integer year = Integer.valueOf(jsonObject.get("year").getAsString());
//        Integer month = Integer.valueOf(jsonObject.get("month").getAsString());
//        Integer dayOfMonth = Integer.valueOf(jsonObject.get("dayOfMonth").getAsString());
//        Integer hourOfDay = Integer.valueOf(jsonObject.get("hourOfDay").getAsString());
//        Integer minute = Integer.valueOf(jsonObject.get("minute").getAsString());
//        Integer second = Integer.valueOf(jsonObject.get("second").getAsString());

      //  c.set(year, month, dayOfMonth);
        c.setTime(date);

        return c;
    }
}
