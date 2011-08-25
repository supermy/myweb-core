package org.supermy.core.web;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

/**
 * JSON 序列化日期格式采用标准 ISO格式, like 2010-07-01T15:30:00
 */
public class ExtDateSerializer extends JsonSerializer<Date> {

    // the date format to use
	
    //private final static String FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final static String FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    //private final static String FORMAT = "yyyy-MM-dd";

    @Override
    public void serialize(Date value, JsonGenerator gen, SerializerProvider provider) throws IOException {

        DateFormat formatter = new SimpleDateFormat(FORMAT);
        String formattedDate = formatter.format(value);

        gen.writeString(formattedDate);
    }
}
