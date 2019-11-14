package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicDto;
import uo.ri.cws.application.util.BusinessCheck;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;

public class AddMechanic implements Command<MechanicDto> {

	private MechanicDto dto;
	private MechanicRepository repo = Factory.repository.forMechanic();

	public AddMechanic(MechanicDto mecanico) {
		this.dto = mecanico;
	}

	@Override
	public MechanicDto execute() throws BusinessException {
		// Create the mechanic, but it's not in the graph
		// and we need to make it persistent
		checkDto(dto);
		checkDoesNotExist(dto.dni);
		Mechanic m = new Mechanic(dto.dni, dto.name, dto.surname);
		repo.add(m);
		
		dto.id = m.getId();

		return dto;
	}

	private void checkDto(MechanicDto dto2) throws BusinessException {
		BusinessCheck.isNotEmpty(dto2.dni, "Emtpy dni");
		BusinessCheck.isNotEmpty(dto2.name, "Emtpy name");
		BusinessCheck.isNotEmpty(dto2.surname, "Emtpy surname");
	}

	private void checkDoesNotExist(String dni) throws BusinessException {
		Optional<Mechanic> om = repo.findByDni(dni);
		BusinessCheck.isFalse(om.isPresent(), "The mechanic already exists");
	}

}
