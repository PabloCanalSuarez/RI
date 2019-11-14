package uo.ri.cws.application.repository;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.domain.Course;

public interface CourseRepository extends Repository<Course>{

	/**
	 * @param code
	 * @return the course identified by the code or null if none
	 */
	Optional<Course> findByCode(String code);

	/**
	 * @return a list with all courses (might be empty)
	 */
	List<Course> findAll();
}
