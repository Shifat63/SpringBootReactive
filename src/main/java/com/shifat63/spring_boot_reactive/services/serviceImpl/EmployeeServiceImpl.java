package com.shifat63.spring_boot_reactive.services.serviceImpl;

import com.shifat63.spring_boot_reactive.model.Employee;
import com.shifat63.spring_boot_reactive.model.Showroom;
import com.shifat63.spring_boot_reactive.repositories.EmployeeRepository;
import com.shifat63.spring_boot_reactive.repositories.ShowroomRepository;
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
    private ShowroomRepository showroomRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ShowroomRepository showroomRepository) {
        this.employeeRepository = employeeRepository;
        this.showroomRepository = showroomRepository;
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
    public Mono<Employee> saveOrUpdate(Employee employee) throws Exception {
        log.info("start: saveOrUpdate method of EmployeeServiceImpl");
        Mono<Employee> savedEmployeeMono = null;
        if(employee.getId() != null)
        {
            Employee previousInstanceOfEmployee = employeeRepository.findById(employee.getId()).block();
            if(previousInstanceOfEmployee.getShowroom().getId() != employee.getShowroom().getId())
            {
                Showroom previousShowroom = previousInstanceOfEmployee.getShowroom();
                previousShowroom.getEmployeeSet().remove(employee);
                showroomRepository.save(previousShowroom);

                Showroom newShowroom = employee.getShowroom();
                newShowroom.getEmployeeSet().add(employee);
                showroomRepository.save(newShowroom);
            }
            savedEmployeeMono = employeeRepository.save(employee);
        }
        else
        {
            savedEmployeeMono = employeeRepository.save(employee);
            Employee savedEmployee = savedEmployeeMono.block();
            Showroom newShowroom = savedEmployee.getShowroom();
            newShowroom.getEmployeeSet().add(savedEmployee);
            showroomRepository.save(newShowroom);
        }

        log.info("end: saveOrUpdate method of EmployeeServiceImpl");
        return savedEmployeeMono;
    }

    @Override
    public Mono<Void> deleteById(String employeeId) throws Exception {
        log.info("start: deleteById method of EmployeeServiceImpl");
        Employee toBeDeletedEmployee = employeeRepository.findById(employeeId).block();

        //Removing this employee from his showroom
        Showroom showroom = toBeDeletedEmployee.getShowroom();
        showroom.getEmployeeSet().remove(toBeDeletedEmployee);
        showroomRepository.save(showroom);

        employeeRepository.deleteById(employeeId);
        log.info("end: deleteById method of EmployeeServiceImpl");
        return Mono.empty();
    }

    @Override
    public  Mono<Void> deleteAll() throws Exception {
        log.info("start: deleteAll method of EmployeeServiceImpl");
        employeeRepository.deleteAll();
        log.info("end: deleteAll method of EmployeeServiceImpl");
        return Mono.empty();
    }
}
