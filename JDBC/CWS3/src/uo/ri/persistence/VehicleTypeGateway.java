package uo.ri.persistence;

import uo.ri.business.dto.VehicleTypeDto;

public interface VehicleTypeGateway {
	
	public VehicleTypeDto findVehicleType(Long id);

}
