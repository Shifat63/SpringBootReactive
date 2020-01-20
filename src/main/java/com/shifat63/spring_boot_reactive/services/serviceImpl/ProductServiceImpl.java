package com.shifat63.spring_boot_reactive.services.serviceImpl;

import com.shifat63.spring_boot_reactive.model.Product;
import com.shifat63.spring_boot_reactive.repositories.ProductRepository;
import com.shifat63.spring_boot_reactive.services.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// Author: Shifat63

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Flux<Product> findAll() throws Exception {
        log.info("start: findAll method of ProductServiceImpl");
        Flux<Product> productFlux = productRepository.findAll();
        log.info("end: findAll method of ProductServiceImpl");
        return productFlux;
    }

    @Override
    public Mono<Product> findById(String productId) throws Exception {
        log.info("start: findById method of ProductServiceImpl");
        Mono<Product> productMono = productRepository.findById(productId);
        log.info("end: findById method of ProductServiceImpl");
        return productMono;
    }

    @Override
    public Mono<Void> saveOrUpdate(Product product) throws Exception {
        log.info("start: saveOrUpdate method of ProductServiceImpl");
        productRepository.save(product).subscribe();
        log.info("end: saveOrUpdate method of ProductServiceImpl");
        return Mono.empty();
    }

    @Override
    public Mono<Void> deleteById(String productId) throws Exception {
        log.info("start: deleteById method of ProductServiceImpl");
        productRepository.deleteById(productId).subscribe();
        log.info("end: deleteById method of ProductServiceImpl");
        return Mono.empty();
    }

    @Override
    public Mono<Void> deleteAll() throws Exception {
        log.info("start: deleteAll method of ProductServiceImpl");
        productRepository.deleteAll().subscribe();
        log.info("end: deleteAll method of ProductServiceImpl");
        return Mono.empty();
    }
}
