package com.example.smhiprojectbluegrass.route;

import com.example.smhiprojectbluegrass.processor.HandleResponseAndMapToWeatherDataCanonicalProcessor;
import com.example.smhiprojectbluegrass.smhi.Smhi;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApiReceiverRouter extends RouteBuilder {

    public static final String TEMP = "temp";
    public static final String WIND_DIRECTION = "windDirection";
    public static final String WIND_SPEED = "windSpeed";
    private Logger logger = LoggerFactory.getLogger(ApiReceiverRouter.class);

    @Autowired
    private HandleResponseAndMapToWeatherDataCanonicalProcessor handleResponseAndMapToWeatherDataCanonicalProcessor;

    @Value("${weatherData.timer.interval}")
    private String timerInterval;



    @Override
    public void configure() throws Exception {

        restConfiguration().host("https://opendata-download-metobs.smhi.se/api");

        from("timer:rest-api-consumer?period=" + timerInterval)
                .log("${body}")
                .to("rest:get:/version/1.0/parameter/1/station/{{weatherData.station.id}}/period/{{time.period}}/data.json")
                .log("${body}")
                .unmarshal().json(JsonLibrary.Jackson, Smhi.class)
                .setProperty(TEMP, body())
                .to("rest:get:/version/1.0/parameter/3/station/{{weatherData.station.id}}/period/{{time.period}}/data.json")
                .log("${body}")
                .unmarshal().json(JsonLibrary.Jackson, Smhi.class)
                .setProperty(WIND_DIRECTION, body())
                .to("rest:get:/version/1.0/parameter/4/station/{{weatherData.station.id}}/period/{{time.period}}/data.json")
                .log("${body}")
                .unmarshal().json(JsonLibrary.Jackson, Smhi.class)
                .setProperty(WIND_SPEED, body())
                .process(handleResponseAndMapToWeatherDataCanonicalProcessor)
                .marshal().jaxb()
                .log("Body after marshal: ${body}");


    }
}
