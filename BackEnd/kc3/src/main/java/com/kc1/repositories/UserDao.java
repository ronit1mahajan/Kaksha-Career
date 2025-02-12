package com.kc1.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kc1.entities.User;
import com.kc1.entities.User.Role;
@Repository
public interface UserDao extends JpaRepository<User, Integer> {
	
	Optional<User> findByEmail(String email);
	
	Optional<User> findByEmailAndPassword(String email,String password);

	Optional<User> findFirstByRole(Role admin);
}
