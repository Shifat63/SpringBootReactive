package com.shifat63.spring_boot_reactive.services.serviceImpl;

import com.shifat63.spring_boot_reactive.model.Employee;
import com.shifat63.spring_boot_reactive.model.Product;
import com.shifat63.spring_boot_reactive.model.Showroom;
import com.shifat63.spring_boot_reactive.repositories.EmployeeRepository;
import com.shifat63.spring_boot_reactive.repositories.ProductRepository;
import com.shifat63.spring_boot_reactive.repositories.ShowroomRepository;
import com.shifat63.spring_boot_reactive.services.service.ShowroomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

// Author: Shifat63

@Slf4j
@Service
public class ShowroomServiceImpl implements ShowroomService {
    private ShowroomRepository showroomRepository;
    private ProductRepository productRepository;
    private EmployeeRepository employeeRepository;

    public ShowroomServiceImpl(ShowroomRepository showroomRepository, ProductRepository productRepository, EmployeeRepository employeeRepository) {
        this.showroomRepository = showroomRepository;
        this.productRepository = productRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Flux<Showroom> findAll() throws Exception {
        log.info("start: findAll method of ShowroomServiceImpl");
        Flux<Showroom> showroomFlux = showroomRepository.findAll();
        log.info("end: findAll method of ShowroomServiceImpl");
        return showroomFlux;
    }

    @Override
    public Mono<Showroom> findById(String showroomId) throws Exception {
        log.info("start: findById method of ShowroomServiceImpl");
        Mono<Showroom> showroomMono = showroomRepository.findById(showroomId);
        log.info("end: findById method of ShowroomServiceImpl");
        return showroomMono;
    }

    @Override
    public Mono<Showroom> saveOrUpdate(Showroom showroom) throws Exception {
        log.info("start: saveOrUpdate method of ShowroomServiceImpl");
        Mono<Showroom> savedShowroomMono = showroomRepository.save(showroom);
        log.info("end: saveOrUpdate method of ShowroomServiceImpl");
        return savedShowroomMono;
    }

    @Override
    public Mono<Void> deleteById(String showroomId) throws Exception {
        log.info("start: deleteById method of ShowroomServiceImpl");
        Showroom toBeDeletedShowroom = showroomRepository.findById(showroomId).block();

        //Removing this showroom for each product ShowroomSet
        for (Product eachProduct : toBeDeletedShowroom.getProductSet())
        {
            eachProduct.getShowroomSet().remove(toBeDeletedShowroom);
            productRepository.save(eachProduct);
        }

        //Deleting each employee of this showroom
        for (Employee eachEmployee : toBeDeletedShowroom.getEmployeeSet())
        {
            employeeRepository.deleteById(eachEmployee.getId());
        }
        showroomRepository.deleteById(showroomId);
        log.info("end: deleteById method of ShowroomServiceImpl");
        return Mono.empty();
    }

    @Override
    public Mono<Void> deleteAll() throws Exception {
        log.info("start: deleteAll method of ShowroomServiceImpl");
        showroomRepository.deleteAll();
        log.info("end: deleteAll method of ShowroomServiceImpl");
        return Mono.empty();
    }
}
