package com.shifat63.spring_boot_reactive.converters;

import com.shifat63.spring_boot_reactive.model.Brand;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

// Author: Shifat63

@Component
public class StringToBrand implements Converter<String, Brand>{
    @Override
    public Brand convert(String source) {
        Brand brand = null;
        if(source != null && !source.equalsIgnoreCase(""))
        {
            brand = new Brand();
            brand.setId(source);
        }
        return brand;
    }
}
