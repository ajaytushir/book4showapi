package org.catalystone.bookurshow.dao;

import java.util.List;

import org.catalystone.bookurshow.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookingRepository extends JpaRepository<Booking, Long>{
	public List<Booking> findByUserId(Long userId);
}
