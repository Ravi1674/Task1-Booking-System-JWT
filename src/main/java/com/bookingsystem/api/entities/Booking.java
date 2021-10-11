package com.bookingsystem.api.entities;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "Bookings")
public class Booking {

	@Id
	private String customerName;
	private String source;
	private String destination;
	private String date;
	private String time;
	private int noOfSeat;
	private long contactNo;
	
}