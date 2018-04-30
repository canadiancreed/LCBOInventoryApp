package com.creed.project.lcboapp.persistence.repository;

import com.creed.project.lcboapp.persistence.model.LCBOProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface LCBOProductJpaRepository extends JpaRepository<LCBOProductEntity, Long> {

//    void deleteAllByTransId(Long transId);
}
