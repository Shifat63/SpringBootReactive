package com.shifat63.spring_boot_reactive.converters;

import com.shifat63.spring_boot_reactive.model.Showroom;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

// Author: Shifat63

@Component
public class StringToShowroom implements Converter<String, Showroom> {
    @Override
    public Showroom convert(String source) {
        Showroom showroom;
        if(source == null || source.equalsIgnoreCase(""))
        {
            showroom = null;
        }
        else
        {
            showroom = new Showroom();
            showroom.setId(source);
        }
        return showroom;
    }
}
