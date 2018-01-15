package com.creed.project.lcboapp.persistence.repository;

import com.creed.project.lcboapp.persistence.model.LCBOInventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface LCBOInventoryJpaRepository extends JpaRepository<LCBOInventoryEntity, Long> {

//    void deleteAllByTransId(Long transId);
}
