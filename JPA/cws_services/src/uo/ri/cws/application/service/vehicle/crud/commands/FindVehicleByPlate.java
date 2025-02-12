package uo.ri.cws.application.service.vehicle.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.VehicleRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.vehicle.VehicleDto;
import uo.ri.cws.application.util.BusinessCheck;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Vehicle;

public class FindVehicleByPlate implements Command<Optional<VehicleDto>> {

	private String plate;
	private VehicleRepository repo = Factory.repository.forVehicle();
	
	public FindVehicleByPlate(String plate) {
		this.plate = plate;
	}

	@Override
	public Optional<VehicleDto> execute() throws BusinessException {
		BusinessCheck.isNotEmpty(plate, "Plate is empty");
		
		Optional<Vehicle> m = repo.findByPlate( plate );
		return m.isPresent()
				? Optional.of( DtoAssembler.toDto( m.get() ))
				: Optional.empty();
	}

}
