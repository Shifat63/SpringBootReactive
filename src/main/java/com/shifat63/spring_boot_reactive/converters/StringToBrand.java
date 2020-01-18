package com.shifat63.spring_boot_reactive.converters;

import com.shifat63.spring_boot_reactive.model.Brand;
import com.shifat63.spring_boot_reactive.services.service.BrandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

// Author: Shifat63

@Component
@Slf4j
public class StringToBrand implements Converter<String, Brand> {
    @Override
    public Brand convert(String source) {
        Brand brand;
        if(source == null || source.equalsIgnoreCase(""))
        {
            brand = null;
        }
        else
        {
            brand = new Brand();
            brand.setId(source);
        }
        return brand;
    }
}
