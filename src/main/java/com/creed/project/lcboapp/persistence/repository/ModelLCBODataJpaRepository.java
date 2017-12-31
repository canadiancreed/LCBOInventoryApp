package com.creed.project.lcboapp.persistence.repository;

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
public interface ModelLCBODataJpaRepository extends JpaRepository<ModelLCBODataEntity, Long> {

    @Modifying
    @Query(value = "INSERT /*+ append */ " +
            "INTO STG_MODEL_LCBO_DATA (TRANS_ID, FEED_ID, LCBO_DATA_NAME, LCBO_DATA, LCBO_DATA_DOC, CREATED_DATE, UPDATED_DATE) " +
            "SELECT :transId TRANS_ID, FEED_ID, LCBO_DATA_NAME, LCBO_DATA, LCBO_DATA_DOC, CREATED_DATE, UPDATED_DATE " +
            "FROM STG_MODEL_LCBO_DATA " +
            "WHERE TRANS_ID = (SELECT MAX(TRANS_ID) FROM LCBO_APP_TRANS WHERE READY_FOR_PICKUP = 'Y')",
            nativeQuery = true)
    void insertFromLastSuccessfulTransaction(@Param("transId") Long transId);

    List<ModelLCBODataEntity> findByTransId(Long transId);

    void deleteAllByTransId(Long transId);
}

