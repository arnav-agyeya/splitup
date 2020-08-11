package com.example.splitup.dao;

import com.example.splitup.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGroupDAO extends JpaRepository<Group, Integer> {


}
