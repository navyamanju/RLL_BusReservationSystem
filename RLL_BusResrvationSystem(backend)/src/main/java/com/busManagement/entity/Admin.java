package com.busManagement.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "admins",schema = "bus")
@Data @NoArgsConstructor @AllArgsConstructor
public class Admin {

	@Id
	private int adminId;
	
	@JsonIgnore
	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(nullable = false) //not-null
	private String password;
	
	@Column(nullable = false)
	private String adminName;
	
}
