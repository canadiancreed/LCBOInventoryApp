package com.creed.project.lcboapp.persistence.repository;

import com.creed.project.lcboapp.persistence.model.LCBOAppFeedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface LCBOAppFeedJpaRepository extends JpaRepository<LCBOAppFeedEntity, Long> {

//    @Query("SELECT COUNT(feedId) " +
//            " FROM CostFeedEntity " +
//            "WHERE transId IN " +
//            "      (SELECT transId " +
//            "         FROM CostTransactionEntity " +
//            "         WHERE dptReady = 'Y') " +
//            "  AND feedFileName = :feedFileName")
//    long countByFeedFileName(@Param("feedFileName") String feedFileName);

    List<LCBOAppFeedEntity> findByTransId(Long transId);

    void deleteAllByTransId(Long transId);
}