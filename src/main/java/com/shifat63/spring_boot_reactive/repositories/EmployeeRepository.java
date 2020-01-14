package com.shifat63.spring_boot_reactive.repositories;

import com.shifat63.spring_boot_reactive.model.Employee;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

// Author: Shifat63

@Repository
public interface EmployeeRepository extends ReactiveMongoRepository<Employee, String> {
}
