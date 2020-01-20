package com.shifat63.spring_boot_reactive.services.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// Author: Shifat63

public interface CrudService<T, Id>
{
    Flux<T> findAll() throws Exception;
    Mono<T> findById(Id id) throws Exception;
    Mono<Void> saveOrUpdate(T object) throws Exception;
    Mono<Void> deleteById(Id id) throws Exception;
    Mono<Void> deleteAll() throws Exception;
}
