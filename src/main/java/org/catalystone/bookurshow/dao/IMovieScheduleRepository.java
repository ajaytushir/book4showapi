package org.catalystone.bookurshow.dao;

import java.time.LocalDate;
import java.util.List;

import org.catalystone.bookurshow.domain.MovieSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IMovieScheduleRepository extends JpaRepository<MovieSchedule, Long>{

	@Query("select ms from MovieSchedule ms where ms.from<= :date AND ms.to>= :date")
	public List<MovieSchedule> findByDate(@Param("date") LocalDate date);
}
