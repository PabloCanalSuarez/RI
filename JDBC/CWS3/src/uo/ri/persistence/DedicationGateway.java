package uo.ri.persistence;

import java.util.List;

import uo.ri.business.dto.VehicleTypeDto;

public interface DedicationGateway {
	
	public List<VehicleTypeDto> findVehicleTypeDedicated();

}
