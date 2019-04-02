package com.miyou.repository;

import com.miyou.model.CronVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


@Component
public interface ICronRepository extends JpaRepository<CronVO, Long> {
}
