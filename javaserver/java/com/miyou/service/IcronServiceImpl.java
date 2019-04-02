package com.miyou.service;


import com.miyou.model.CronVO;
import com.miyou.repository.ICronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IcronServiceImpl {


    @Autowired
    ICronRepository iCronRepository;

    public Optional<CronVO> findById(Long cronId) {
        return iCronRepository.findById(cronId);
    }

    public void save(CronVO cronVO) {
        iCronRepository.save(cronVO);
    }

    public List<CronVO> findAll() {
       return iCronRepository.findAll();
    }
}
