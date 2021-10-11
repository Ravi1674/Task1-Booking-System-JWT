package com.bookingsystem.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bookingsystem.api.entities.Booking;
import com.bookingsystem.api.service.BookingService;

@RestController
public class BookingController {
	@Autowired
	public BookingService bookingService;
	
	@PostMapping({"/addBookingDetails"})
	@PreAuthorize("hasRole('User')")
	public Booking addBookingDetails(@RequestBody Booking booking) {
		System.out.println(booking);
		return bookingService.addCustomerBookingDetails(booking);
	}
	
	@GetMapping({"/viewBookingdetails"})
	@PreAuthorize("hasRole('Admin')")
	public List<Booking> viewBookings() {
		return bookingService.viewBookings();
	}
}
