package com.example.smhiprojectbluegrass.processor;

import com.example.smhiprojectbluegrass.WeatherData;
import com.example.smhiprojectbluegrass.route.ApiReceiverRouter;
import com.example.smhiprojectbluegrass.smhi.Smhi;
import com.example.smhiprojectbluegrass.utils.DateTimeUtils;
import com.example.smhiprojectbluegrass.utils.RepeatedCodeMethods;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class HandleResponseAndMapToWeatherDataCanonicalProcessor implements Processor {

    private Logger logger = LoggerFactory.getLogger(HandleResponseAndMapToWeatherDataCanonicalProcessor.class);

    @Value("${weatherData.station.name}")
    private String stationName;

    @Value("${weatherData.station.id}")
    private BigInteger stationid;

    @Override
    public void process(Exchange exchange) throws Exception {

        //Fetch all 3 response bodies that we earlier saved as properties
        Smhi tempResponseBody = exchange.getProperty(ApiReceiverRouter.TEMP, Smhi.class);
        Smhi windDirectionResponseBody = exchange.getProperty(ApiReceiverRouter.WIND_DIRECTION, Smhi.class);
        Smhi windSpeedResponseBody = exchange.getProperty(ApiReceiverRouter.WIND_SPEED, Smhi.class);

        WeatherData weatherData = new WeatherData();
        WeatherData.Reading reading = new WeatherData.Reading();
        List<WeatherData.Reading.Parameter> parameters = new ArrayList<>();


        //Handle static fields
        reading.setStationName(stationName);
        reading.setStationId(stationid);
        XMLGregorianCalendar timestampAsXml = DateTimeUtils.asXmlGreGorianCalendar(LocalDateTime.now());
        reading.setTimestamp(timestampAsXml);

        //Handle parameter for temperature
        WeatherData.Reading.Parameter parameterForTemp = new WeatherData.Reading.Parameter();
        parameterForTemp.setName("Temp");
        parameterForTemp.setValue(RepeatedCodeMethods.SetValue(tempResponseBody.getValue()));
        parameters.add(parameterForTemp);

        //Handle parameter for wind direction
        WeatherData.Reading.Parameter parameterForWindDirection = new WeatherData.Reading.Parameter();
        parameterForWindDirection.setName("WindDirection");
        parameterForWindDirection.setValue(RepeatedCodeMethods.SetValue(windDirectionResponseBody.getValue()));
        parameters.add(parameterForWindDirection);

        //Handle parameter for wind speed
        WeatherData.Reading.Parameter parameterForWindSpeed = new WeatherData.Reading.Parameter();
        parameterForWindSpeed.setName("WindSpeed");
        parameterForWindSpeed.setValue(RepeatedCodeMethods.SetValue(windSpeedResponseBody.getValue()));
        parameters.add(parameterForWindSpeed);

        //Add all parameters for reading
        reading.getParameter().addAll(parameters);

        //add created reading object to list of reading objects for weather data
        weatherData.getReading().add(reading);

        //Add newly created weatherData object to exchange body
        exchange.getIn().setBody(weatherData);
    }
}
