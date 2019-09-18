package org.catalystone.bookurshow.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import javax.persistence.QueryHint;

import org.catalystone.bookurshow.domain.MovieSchedule;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import static org.hibernate.jpa.QueryHints.*;

@Repository
public interface IMovieScheduleRepository extends CrudRepository<MovieSchedule, Long>{

	@Query("select ms from MovieSchedule ms where ms.from<= :date AND ms.to>= :date")
	public List<MovieSchedule> findByDate(@Param("date") LocalDate date);
	

	@Query("select ms from MovieSchedule ms where ms.movieTheatre.id= :movieTheatreId AND ms.to>= :date")
	public List<MovieSchedule> findTheatreScheduleByDate(@Param("date") LocalDate date, @Param("movieTheatreId") Long movieTheatreId);
	
	 @QueryHints(value = {
	            @QueryHint(name = HINT_FETCH_SIZE, value = "" + 15000),
	            @QueryHint(name = HINT_CACHEABLE, value = "false"),
	            @QueryHint(name = HINT_READONLY, value = "true"),
	    })
	@Query("select ms from MovieSchedule ms order by id desc") 
	public Stream<MovieSchedule> streamList();
}
