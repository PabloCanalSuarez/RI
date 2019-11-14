package uo.ri.cws.application.repository;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.domain.Enrollment;
import uo.ri.cws.domain.Mechanic;

public interface EnrollmentRepository extends Repository<Enrollment> {

	/**
	 * @return A list including all the mechanics involved in the enrollments
	 */
	List<Mechanic> findMechanicEnrolled();

	/**
	 * 
	 * @param idMechanic    - Id of the mechanic
	 * @param idVehicleType - Id of the vehicleType
	 * @return int - The number of hours that a specific mechanic has attended
	 *         to a course of a specific vehicletype
	 */
	Optional<Long> getEnrollmentTime(String idMechanic, String idVehicleType);

}
