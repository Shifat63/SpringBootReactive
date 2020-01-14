package com.shifat63.spring_boot_reactive.services.serviceImpl;

import com.shifat63.spring_boot_reactive.model.Brand;
import com.shifat63.spring_boot_reactive.model.Product;
import com.shifat63.spring_boot_reactive.model.Showroom;
import com.shifat63.spring_boot_reactive.repositories.BrandRepository;
import com.shifat63.spring_boot_reactive.repositories.ProductRepository;
import com.shifat63.spring_boot_reactive.repositories.ShowroomRepository;
import com.shifat63.spring_boot_reactive.services.service.BrandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// Author: Shifat63

@Slf4j
@Service
public class BrandServiceImpl implements BrandService {
    private BrandRepository brandRepository;
    private ShowroomRepository showroomRepository;
    private ProductRepository productRepository;

    public BrandServiceImpl(BrandRepository brandRepository, ShowroomRepository showroomRepository, ProductRepository productRepository) {
        this.brandRepository = brandRepository;
        this.showroomRepository = showroomRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Flux<Brand> findAll() throws Exception {
        log.info("start: findAll method of BrandServiceImpl");
        Flux<Brand> brandFlux = brandRepository.findAll();
        log.info("end: findAll method of BrandServiceImpl");
        return brandFlux;
    }

    @Override
    public Mono<Brand> findById(String brandId) throws Exception {
        log.info("start: findById method of BrandServiceImpl");
        Mono<Brand> brandMono = brandRepository.findById(brandId);
        log.info("end: findById method of BrandServiceImpl");
        return brandMono;
    }

    @Override
    public Mono<Brand> saveOrUpdate(Brand brand) throws Exception {
        log.info("start: saveOrUpdate method of BrandServiceImpl");
        Mono<Brand> savedBrandMono = brandRepository.save(brand);
        log.info("end: saveOrUpdate method of BrandServiceImpl");
        return savedBrandMono;
    }

    @Override
    public Mono<Void> deleteById(String brandId) throws Exception {
        log.info("start: deleteById method of BrandServiceImpl");
        Brand toBeDeletedBrand = brandRepository.findById(brandId).block();

        //Deleting each product of this brand
        for (Product eachProduct : toBeDeletedBrand.getProductSet())
        {
            //Deleting this product from each showroom ProductSet
            for (Showroom eachShowroom : eachProduct.getShowroomSet())
            {
                eachShowroom.getProductSet().remove(eachProduct);
                showroomRepository.save(eachShowroom);
            }
            productRepository.deleteById(eachProduct.getId());
        }
        brandRepository.deleteById(brandId);
        log.info("end: deleteById method of BrandServiceImpl");
        return Mono.empty();
    }

    @Override
    public Mono<Void> deleteAll() throws Exception {
        log.info("start: deleteAll method of BrandServiceImpl");
        brandRepository.deleteAll();
        log.info("start: deleteAll method of BrandServiceImpl");
        return Mono.empty();
    }
}
