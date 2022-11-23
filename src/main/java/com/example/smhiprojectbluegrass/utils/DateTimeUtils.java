package com.example.smhiprojectbluegrass.utils;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.GregorianCalendar;
import java.util.Objects;

public class DateTimeUtils {

    public static XMLGregorianCalendar asXmlGreGorianCalendar(final LocalDateTime localDateTime) {
        try {
            return Objects.nonNull(localDateTime) ?
                    DatatypeFactory.newInstance().newXMLGregorianCalendar(
                            GregorianCalendar.from(localDateTime.atZone(ZoneId.systemDefault()))) :
                    null;
        } catch (DatatypeConfigurationException e) {
            throw new IllegalStateException("Failed to instantiate datatype factory.");
        }
    }







}
