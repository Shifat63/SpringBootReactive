package com.shifat63.spring_boot_reactive.converters;

import com.shifat63.spring_boot_reactive.model.Brand;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

// Author: Shifat63

@Component
public class BrandToString implements Converter<Brand, String> {
    @Override
    public String convert(Brand source) {
        if(source != null)
        {
            return source.getId();
        }
        return null;
    }
}
