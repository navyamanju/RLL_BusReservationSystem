package com.busManagement;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.busManagement.entity.Admin;
import com.busManagement.serviceImpl.AdminServiceImpl;

@SpringBootApplication
public class BusManagementApplication {
	
//	@Autowired
//	AdminServiceImpl adminService;
//	
//	@PostConstruct
//	public void initAdmin() {
//		Admin admin = new Admin();
//		admin.setAdminName("admin");
//		admin.setPassword("admin12345");
//		adminService.addAdmin(admin);
//	}

	public static void main(String[] args) {
		SpringApplication.run(BusManagementApplication.class, args);
	}

}
