package com.shifat63.spring_boot_reactive.services.serviceImpl;

import com.shifat63.spring_boot_reactive.model.Employee;
import com.shifat63.spring_boot_reactive.repositories.EmployeeRepository;
import com.shifat63.spring_boot_reactive.services.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// Author: Shifat63

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Flux<Employee> findAll() throws Exception {
        log.info("start: findAll method of EmployeeServiceImpl");
        Flux<Employee> employeeFlux = employeeRepository.findAll();
        log.info("end: findAll method of EmployeeServiceImpl");
        return employeeFlux;
    }

    @Override
    public Mono<Employee> findById(String employeeId) throws Exception {
        log.info("start: findById method of EmployeeServiceImpl");
        Mono<Employee> employeeMono = employeeRepository.findById(employeeId);
        log.info("end: findById method of EmployeeServiceImpl");
        return employeeMono;
    }

    @Override
    public Mono<Void> saveOrUpdate(Employee employee) throws Exception {
        log.info("start: saveOrUpdate method of EmployeeServiceImpl");
        employeeRepository.save(employee).subscribe();
        log.info("end: saveOrUpdate method of EmployeeServiceImpl");
        return Mono.empty();
    }

    @Override
    public Mono<Void> deleteById(String employeeId) throws Exception {
        log.info("start: deleteById method of EmployeeServiceImpl");
        employeeRepository.deleteById(employeeId).subscribe();
        log.info("end: deleteById method of EmployeeServiceImpl");
        return Mono.empty();
    }

    @Override
    public  Mono<Void> deleteAll() throws Exception {
        log.info("start: deleteAll method of EmployeeServiceImpl");
        employeeRepository.deleteAll().subscribe();
        log.info("end: deleteAll method of EmployeeServiceImpl");
        return Mono.empty();
    }
}
