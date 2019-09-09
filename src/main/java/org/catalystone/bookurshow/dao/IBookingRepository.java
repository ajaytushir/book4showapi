package org.catalystone.bookurshow.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.catalystone.bookurshow.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookingRepository extends JpaRepository<Booking, Long> {
	public List<Booking> findByUserId(Long userId);

	@Query("select sum(b.seatCount) from Booking b where b.movieSchedule.id=:movieScheduleId AND b.bookingDate=:bookingDate")
	public Integer getTotalSeatCount(@Param("movieScheduleId") Long movieScheduleId,
			@Param("bookingDate") LocalDate bookingDate);

	@Query("select b from Booking b") 
	public Stream<Booking> streamList();
}
