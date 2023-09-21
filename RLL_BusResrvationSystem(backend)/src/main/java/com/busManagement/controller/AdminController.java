package com.busManagement.controller;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.busManagement.entity.Admin;
import com.busManagement.entity.BookingDetails;
import com.busManagement.entity.BusDetails;
import com.busManagement.entity.Passenger;
import com.busManagement.entity.User;
import com.busManagement.serviceImpl.AdminServiceImpl;
import com.busManagement.utils.AdminAuth;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	AdminServiceImpl service;
	
	@PostConstruct
	public void initAdmin() {
	Admin admin = new Admin();
	admin.setAdminName("admin");	
	admin.setPassword("admin@123");	
	service.addAdmin(admin);
}	
	@PostMapping("/adminLogin")
	public ResponseEntity<Admin> loginAdmin(@RequestBody AdminAuth auth){
		Admin admin = service.adminLogin(auth);
		return ResponseEntity.ok(admin);
	}
	
	@GetMapping("/getAdmin/{id}")
	public ResponseEntity<Admin> getAdmin(@PathVariable Integer id){
		Admin admin = service.getAdmin(id);
		return ResponseEntity.ok(admin);
	}
	
	@DeleteMapping("/deleteAdmin/{id}")
	public ResponseEntity<Void> deleteAdmin(@PathVariable Integer id){
		service.deleteAdmin(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	
	@GetMapping("/getAllBusDetails")
	public ResponseEntity<List<BusDetails>> getAllBusDetails(){
		List<BusDetails> list = service.getAllBusDetails();
		return ResponseEntity.ok().body(list);
	}
	
	@PostMapping("/addBusDetails")
	public ResponseEntity<BusDetails> addBus(@RequestBody BusDetails busDetails){
		BusDetails details = service.addBusDetails(busDetails);
		return ResponseEntity.ok().body(details);
	}
	
	@DeleteMapping("/deleteBusDetails/{busNumber}")
	public void deleteBus(@PathVariable Integer busNumber) {
		service.deleteBus(busNumber);
	}
	
	@DeleteMapping("/deleteUserDetails/{id}")
	public void deleteUser(@PathVariable Integer id) {
		service.deleteUser(id);
	}
	
	@PostMapping("/updateBusDetails")
	public ResponseEntity<BusDetails> updateBus(@RequestBody BusDetails busDetails){
		BusDetails details = service.updateBus(busDetails);
		return ResponseEntity.ok().body(details);
	}
	
	@GetMapping("/getAllPassengers")
	public ResponseEntity<List<Passenger>> getAllPassengers(){
		List<Passenger> passengers = service.getAllPassengers();
		return ResponseEntity.ok().body(passengers);
	}
	
	@DeleteMapping("/deleteBooking/{bookingId}/{userId}")
	public void deleteBooking(@PathVariable Integer bookingId,@PathVariable Integer userId) {
		service.deleteBooking(bookingId, userId);
	}
	
	@GetMapping("/getPassengerByBooking/{id}")
	public ResponseEntity<List<Passenger>> getPassengerByBooking(@PathVariable Integer id){
		List<Passenger> passengers = service.getPassengersByBooking(id);
		return ResponseEntity.ok().body(passengers);
	}
	
	@PostMapping("/updateUser")
	public ResponseEntity<Void> updateUser(@Valid @RequestBody User user) {
		service.updateUser(user);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@GetMapping("/getBookingByUser/{userId}")
	public ResponseEntity<List<BookingDetails>> getBookingByUser(@PathVariable Integer userId){
		List<BookingDetails> list = service.getBookingByUserId(userId);
		return ResponseEntity.ok().body(list);
	}
	
	@PostMapping("/addAdmin")
	public ResponseEntity<Admin> addAdmin(@RequestBody Admin admin){
		Admin addedAdmin = service.addAdmin(admin);
		return ResponseEntity.ok(addedAdmin);
	}
	
	 @GetMapping("/getBusDetails/{busNumber}")
	    public ResponseEntity<BusDetails> getBusDetails(@PathVariable Integer busNumber) {
	        BusDetails busDetails = service.getBusDetails(busNumber);
	        return ResponseEntity.ok(busDetails);
	    }
	
	
	//To view all the user by the Admin
	@GetMapping("/getAllUsers")
	public ResponseEntity<List<User>> getAllUsers(){
		List<User> user = service.getAllUsers();
		return ResponseEntity.ok().body(user);
	}
		}
