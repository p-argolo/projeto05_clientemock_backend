package com.simulador.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.simulador.backend.model.transaction.Operation;

@Repository
public interface OperationRepository extends CrudRepository<Operation, String> {

}