package com.nee.spring.boot.mvc.rest.http.message;

import com.nee.spring.boot.mvc.rest.domain.Person;
import io.netty.util.CharsetUtil;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Properties;

public class PropertiesPersonHttpMessageConverter extends
        AbstractHttpMessageConverter<Person> {

    public PropertiesPersonHttpMessageConverter() {

        super(MediaType.valueOf("application/properties+person"));
        setDefaultCharset(CharsetUtil.UTF_8);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(Person.class);
    }

    @Override
    protected Person readInternal(Class<? extends Person> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {

        InputStream inputStream = inputMessage.getBody();
        Properties properties = new Properties();

        properties.load(new InputStreamReader(inputStream, getDefaultCharset()));

        Person person = new Person();
        person.setId(Long.valueOf(properties.getProperty("properties.id")));
        person.setName(properties.getProperty("properties.name"));

        return person;
    }

    @Override
    protected void writeInternal(Person person, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {

        OutputStream outputStream = outputMessage.getBody();

        Properties properties = new Properties();
        properties.setProperty("person.id", String.valueOf(person.getId()));
        properties.setProperty("person.name", String.valueOf(person.getName()));

        properties.store(new OutputStreamWriter(outputStream, getDefaultCharset()), "");

    }
}
