package org.sparta.batch.domain.waiting.repository;

import org.sparta.batch.domain.waiting.entity.WaitingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WaitingHistoryRepository extends JpaRepository<WaitingHistory, Long> {
}
