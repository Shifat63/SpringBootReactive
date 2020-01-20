package com.shifat63.spring_boot_reactive.services.serviceImpl;

import com.shifat63.spring_boot_reactive.model.Showroom;
import com.shifat63.spring_boot_reactive.repositories.ShowroomRepository;
import com.shifat63.spring_boot_reactive.services.service.ShowroomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// Author: Shifat63

@Slf4j
@Service
public class ShowroomServiceImpl implements ShowroomService {
    private ShowroomRepository showroomRepository;

    public ShowroomServiceImpl(ShowroomRepository showroomRepository) {
        this.showroomRepository = showroomRepository;
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
    public Mono<Void> saveOrUpdate(Showroom showroom) throws Exception {
        log.info("start: saveOrUpdate method of ShowroomServiceImpl");
        showroomRepository.save(showroom).subscribe();
        log.info("end: saveOrUpdate method of ShowroomServiceImpl");
        return Mono.empty();
    }

    @Override
    public Mono<Void> deleteById(String showroomId) throws Exception {
        log.info("start: deleteById method of ShowroomServiceImpl");
        showroomRepository.deleteById(showroomId).subscribe();
        log.info("end: deleteById method of ShowroomServiceImpl");
        return Mono.empty();
    }

    @Override
    public Mono<Void> deleteAll() throws Exception {
        log.info("start: deleteAll method of ShowroomServiceImpl");
        showroomRepository.deleteAll().subscribe();
        log.info("end: deleteAll method of ShowroomServiceImpl");
        return Mono.empty();
    }
}
