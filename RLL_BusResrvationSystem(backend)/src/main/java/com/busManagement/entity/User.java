package com.busManagement.entity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "user",schema = "bus")
public class User {
	
	@Nullable
	@Id
	private Integer userId;
	
	@NotNull(message = "username cannot be null")
	private String userName;
	
	@NotNull(message = "password cannot be null")
	private String password;
	
	@NotNull(message = "phone cannot be null")
	private Long phone;
	
	@NotBlank(message = "email cannot be null")
	private String email;
	
	@OneToMany(cascade = CascadeType.ALL) //One user can make many bookings
	@JoinColumn(name = "user_id") //user_id column will be merged into BookingDetails Entity
	private List<BookingDetails> bookingDetails = new ArrayList<BookingDetails>();
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(Integer userId, String userName, String password, Long phone, String email) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.phone = phone;
		this.email = email;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}
	
	@JsonProperty(access = Access.WRITE_ONLY)
	public void setPassword(String password) {
		this.password = password;
	}

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<BookingDetails> getBookingDetails() {
		return bookingDetails;
	}

	public void setBookingDetails(List<BookingDetails> bookingDetails) {
		this.bookingDetails = bookingDetails;
	}
	

}


