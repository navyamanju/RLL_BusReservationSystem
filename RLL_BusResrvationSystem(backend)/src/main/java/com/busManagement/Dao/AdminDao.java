package com.busManagement.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.busManagement.entity.Admin;

@Repository
public interface AdminDao extends JpaRepository<Admin, Integer> {

}
