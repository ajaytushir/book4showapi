package org.catalystone.bookurshow.dao;

import org.catalystone.bookurshow.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMovieRepository extends JpaRepository<Movie, Long>{

}
