package com.example.smhiprojectbluegrass.utils;


import com.example.smhiprojectbluegrass.WeatherData;
import com.example.smhiprojectbluegrass.smhi.Value;

import java.util.List;

public class RepeatedCodeMethods {


    public static String SetValue(List<Value> SmhiValueList) {

        com.example.smhiprojectbluegrass.smhi.Value tempValue = null;
        WeatherData.Reading.Parameter parameterForTemp = new WeatherData.Reading.Parameter();

        if(!SmhiValueList.isEmpty()){
            tempValue = SmhiValueList.get(0);
        }

        return tempValue != null ? tempValue.getValue() : "Unknown";


    }

}
