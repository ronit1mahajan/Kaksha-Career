package com.kc1.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kc1.entities.College;
@Repository
public interface CollegeDao extends JpaRepository<College, Integer> {

    List<College> findByNameContainingIgnoreCase(String name);

    List<College> findByLocationContainingIgnoreCase(String location);

	Optional<College> findByName(String collegeName);
	
	List<College> findByType(College.Type type);
	
	@Query("SELECT c FROM College c WHERE UPPER(c.name) LIKE UPPER(CONCAT('%', :name, '%'))")
    List<College> searchCollegesByName(@Param("name") String name);
	
}
