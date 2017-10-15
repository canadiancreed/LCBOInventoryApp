package com.creed.project.lcboapp.persistence.repository;

import com.creed.project.lcboapp.persistence.model.LCBOAppTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface LCBOAppTransactionJpaRepository extends JpaRepository<LCBOAppTransactionEntity, Long> {

    LCBOAppTransactionEntity findFirstByReadyForPickupOrderByTransIdDesc(String status);

    void deleteAllByTransId(Long transId);
}