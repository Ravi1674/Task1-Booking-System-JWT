package com.bookingsystem.api.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bookingsystem.api.entities.Booking;

public interface BookingDao extends MongoRepository<Booking, String> {

}
