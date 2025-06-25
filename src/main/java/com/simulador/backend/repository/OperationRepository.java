package com.simulador.backend.repository;

import com.simulador.backend.model.transaction.Operation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRepository extends CrudRepository<Operation, String> {
}