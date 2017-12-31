package com.creed.project.lcboapp.persistence.repository;

import com.creed.project.lcboapp.persistence.model.LCBOInventoryEntity;
import com.creed.project.lcboapp.persistence.model.ModelLCBODataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface LCBOInventoryJpaRepository extends JpaRepository<LCBOInventoryEntity, Long> {
}
