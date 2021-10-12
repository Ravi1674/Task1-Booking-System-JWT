package com.bookingsystem.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookingsystem.api.dao.BookingDao;
import com.bookingsystem.api.entities.Booking;

@Service
public class BookingService {

	@Autowired
	public BookingDao bookingDao;

//	add the booking details..
	public Booking addCustomerBookingDetails(Booking booking) {
		return bookingDao.save(booking);
	}

//	view all the bookings done...
	public List<Booking> viewBookings() {
		return bookingDao.findAll();
	}
}
