package org.catalystone.bookurshow.dao;

import org.catalystone.bookurshow.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository  extends JpaRepository<User, Long>{
	public User findByEmail(String email);
}
