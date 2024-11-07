package com.jorge.encrypt_api.repositories;

import com.jorge.encrypt_api.domain.operation.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, Long> {
}
