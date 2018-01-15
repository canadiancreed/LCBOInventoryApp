package com.creed.project.lcboapp.persistence.repository;

import com.creed.project.lcboapp.persistence.model.LCBOStoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface LCBOStoreJpaRepository extends JpaRepository<LCBOStoreEntity, Long> {

//    void deleteAllByTransId(Long transId);
}
