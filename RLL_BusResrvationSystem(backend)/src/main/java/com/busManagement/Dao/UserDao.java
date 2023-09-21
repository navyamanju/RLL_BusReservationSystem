package com.busManagement.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.busManagement.entity.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

}
