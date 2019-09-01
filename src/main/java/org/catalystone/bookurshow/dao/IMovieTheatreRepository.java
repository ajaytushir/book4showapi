package org.catalystone.bookurshow.dao;

import org.catalystone.bookurshow.domain.MovieTheatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMovieTheatreRepository extends JpaRepository<MovieTheatre, Long>{

}
