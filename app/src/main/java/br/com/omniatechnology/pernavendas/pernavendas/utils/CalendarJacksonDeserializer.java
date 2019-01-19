package br.com.omniatechnology.pernavendas.pernavendas.utils;

import android.util.Log;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
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
public class CalendarJacksonDeserializer extends JsonSerializer<Calendar> {


    private SimpleDateFormat JSON_STRING_CALENDAR;

    public CalendarJacksonDeserializer(SimpleDateFormat sdf) {
        this.JSON_STRING_CALENDAR = sdf;
    }

    @Override
    public void serialize(Calendar value, JsonGenerator gen, SerializerProvider serializers) throws IOException {

    }
}
