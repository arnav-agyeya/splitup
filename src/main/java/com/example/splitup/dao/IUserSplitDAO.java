package com.example.splitup.dao;

import com.example.splitup.entities.UserSplit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserSplitDAO extends JpaRepository<UserSplit, Integer> {
}
