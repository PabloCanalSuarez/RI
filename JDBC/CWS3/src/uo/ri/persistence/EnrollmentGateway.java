package uo.ri.persistence;

import java.util.List;

import uo.ri.business.dto.MechanicDto;

public interface EnrollmentGateway {
	
	public int getEnrollmentTime(Long idMechanic, Long idVehicleType);

	public List<MechanicDto> findMechanicEnrolled();

}
