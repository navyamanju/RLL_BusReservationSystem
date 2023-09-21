package com.busManagement.entity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "passengers",schema = "bus")
@Data @NoArgsConstructor @AllArgsConstructor
public class Passenger {
	
	@Id
	@GeneratedValue
	private Integer passengerId;
	
	private String name;
	private Integer age;
	private Double luggage;


}
