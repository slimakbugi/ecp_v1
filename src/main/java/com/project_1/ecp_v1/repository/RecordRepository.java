package com.project_1.ecp_v1.repository;

import com.project_1.ecp_v1.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository extends JpaRepository<Record, Integer> {
}
