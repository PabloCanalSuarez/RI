package uo.ri.cws.application.repository;

import java.util.List;

import uo.ri.cws.domain.VehicleType;

public interface VehicleTypeRepository extends Repository<VehicleType> {

	List<VehicleType> findAll();

	/**
	 * @return A list including all the vehicleTypes that are already registered
	 *         with a course through dedications.
	 */
	List<VehicleType> findVehicleTypeDedicated();

}
