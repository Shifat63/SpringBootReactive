package com.shifat63.spring_boot_reactive.services.serviceImpl;

import com.shifat63.spring_boot_reactive.model.Brand;
import com.shifat63.spring_boot_reactive.model.Product;
import com.shifat63.spring_boot_reactive.model.Showroom;
import com.shifat63.spring_boot_reactive.repositories.BrandRepository;
import com.shifat63.spring_boot_reactive.repositories.ProductRepository;
import com.shifat63.spring_boot_reactive.repositories.ShowroomRepository;
import com.shifat63.spring_boot_reactive.services.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

// Author: Shifat63

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private BrandRepository brandRepository;
    private ShowroomRepository showroomRepository;

    public ProductServiceImpl(ProductRepository productRepository, BrandRepository brandRepository, ShowroomRepository showroomRepository) {
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
        this.showroomRepository = showroomRepository;
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
    public Mono<Product> saveOrUpdate(Product product) throws Exception {
        log.info("start: saveOrUpdate method of ProductServiceImpl");
        Mono<Product> savedProductMono = null;
        Brand oldBrandOfProduct = new Brand();
        Brand newBrandOfProduct = new Brand();

        if(product.getId() != null) //Update existing product
        {
            Product oldProduct = productRepository.findById(product.getId()).block();
            if(product.getBrand() != null) //Updated product has brand
            {
                if(oldProduct.getBrand() != null) //previous product had brand
                {
                    if (product.getBrand().getId() != oldProduct.getBrand().getId()) // Updated product bandId and previous product bandId do not match
                    {
                        //Updating previous brand reference
                        oldBrandOfProduct = oldProduct.getBrand();
                        oldBrandOfProduct.getProductSet().remove(product);
                        brandRepository.save(oldBrandOfProduct);

                        //Updating new brand reference
                        newBrandOfProduct = product.getBrand();
                        newBrandOfProduct.getProductSet().add(product);
                        brandRepository.save(newBrandOfProduct);
                    }
                }
                else //previous product does not have brand
                {
                    //Updating new brand reference
                    newBrandOfProduct = product.getBrand();
                    newBrandOfProduct.getProductSet().add(product);
                    brandRepository.save(newBrandOfProduct);
                }
            }
            else //Updated product does not have brand
            {
                if(oldProduct.getBrand() != null) //previous product had brand
                {
                    //Updating previous brand reference
                    oldBrandOfProduct = oldProduct.getBrand();
                    oldBrandOfProduct.getProductSet().remove(product);
                    brandRepository.save(oldBrandOfProduct);
                }
            }

            if(product.getShowroomSet()!=null && product.getShowroomSet().size()!=0) //updated product has showroom
            {
                if(oldProduct.getShowroomSet()!=null && oldProduct.getShowroomSet().size()!=0) //previous product had showroom
                {
                    for (Showroom eachShowroomOld : oldProduct.getShowroomSet())
                    {
                        if(!product.getShowroomSet().contains(eachShowroomOld))
                        {
                            eachShowroomOld.getProductSet().remove(product);
                            showroomRepository.save(eachShowroomOld);
                        }
                    }
                    for (Showroom eachShowroomNew : product.getShowroomSet())
                    {
                        if(!oldProduct.getShowroomSet().contains(eachShowroomNew))
                        {
                            eachShowroomNew.getProductSet().add(product);
                            showroomRepository.save(eachShowroomNew);
                        }
                    }
                }
                else //previous product does not have showroom
                {
                    for (Showroom eachShowroomNew : product.getShowroomSet())
                    {
                        eachShowroomNew.getProductSet().add(product);
                        showroomRepository.save(eachShowroomNew);
                    }
                }
            }
            else //updated product does not have showroom
            {
                for (Showroom eachShowroomOld : oldProduct.getShowroomSet())
                {
                    eachShowroomOld.getProductSet().remove(product);
                    showroomRepository.save(eachShowroomOld);
                }
            }

            savedProductMono = productRepository.save(product);
        }
        else //Adding new product
        {
            savedProductMono = productRepository.save(product);
            Product savedProduct = savedProductMono.block();

            //If new product has brand
            if(savedProduct.getBrand() != null) {
                //Updating new brand reference
                newBrandOfProduct = savedProduct.getBrand();
                newBrandOfProduct.getProductSet().add(savedProduct);
                brandRepository.save(newBrandOfProduct);
            }

            //If new product has showrooms
            if(savedProduct.getShowroomSet()!=null && savedProduct.getShowroomSet().size()!=0)
            {
                //Updating new showroom reference
                for (Showroom eachShowroom : savedProduct.getShowroomSet()) {
                    eachShowroom.getProductSet().add(savedProduct);
                    showroomRepository.save(eachShowroom);
                }
            }
        }

        log.info("end: saveOrUpdate method of ProductServiceImpl");
        return savedProductMono;
    }

    @Override
    public Mono<Void> deleteById(String productId) throws Exception {
        log.info("start: deleteById method of ProductServiceImpl");
        Product toBeDeletedProduct = productRepository.findById(productId).block();

        //Removing this product from it's brand's productSet
        Brand brandOfProduct = toBeDeletedProduct.getBrand();
        brandOfProduct.getProductSet().remove(toBeDeletedProduct);
        brandRepository.save(brandOfProduct);

        //Removing this product from each showroom's productSet
        for (Showroom eachShowroom : toBeDeletedProduct.getShowroomSet())
        {
            eachShowroom.getProductSet().remove(toBeDeletedProduct);
            showroomRepository.save(eachShowroom);
        }

        productRepository.deleteById(productId);
        log.info("end: deleteById method of ProductServiceImpl");
        return Mono.empty();
    }

    @Override
    public Mono<Void> deleteAll() throws Exception {
        log.info("start: deleteAll method of ProductServiceImpl");
        productRepository.deleteAll();
        log.info("end: deleteAll method of ProductServiceImpl");
        return Mono.empty();
    }
}
