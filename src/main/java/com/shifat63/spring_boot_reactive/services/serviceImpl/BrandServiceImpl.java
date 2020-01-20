package com.shifat63.spring_boot_reactive.services.serviceImpl;

import com.shifat63.spring_boot_reactive.model.Brand;
import com.shifat63.spring_boot_reactive.repositories.BrandRepository;
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

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
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
    public Mono<Void> saveOrUpdate(Brand brand) throws Exception {
        log.info("start: saveOrUpdate method of BrandServiceImpl");
        brandRepository.save(brand).subscribe();
        log.info("end: saveOrUpdate method of BrandServiceImpl");
        return Mono.empty();
    }

    @Override
    public Mono<Void> deleteById(String brandId) throws Exception {
        log.info("start: deleteById method of BrandServiceImpl");
        brandRepository.deleteById(brandId).subscribe();
        log.info("end: deleteById method of BrandServiceImpl");
        return Mono.empty();
    }

    @Override
    public Mono<Void> deleteAll() throws Exception {
        log.info("start: deleteAll method of BrandServiceImpl");
        brandRepository.deleteAll().subscribe();
        log.info("start: deleteAll method of BrandServiceImpl");
        return Mono.empty();
    }
}
