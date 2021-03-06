package com.example.splitup.dao;

import com.example.splitup.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Interface DAO class for Transaction POJO
 */
@Repository
public interface ITransactionDAO extends JpaRepository<Transaction, Integer> {

    List<Transaction> findAll();
}
