package uo.ri.business.servicesLayer.implementation;

import java.util.List;

import uo.ri.business.administrator.*;
import uo.ri.business.dto.MechanicDto;
import uo.ri.common.BusinessException;
import uo.ri.business.servicesLayer.MechanicCrudService;

public class MechanicCrudServiceImpl implements MechanicCrudService {

	@Override
	public void addMechanic(MechanicDto mecanico) throws BusinessException {
		new AddMechanic(mecanico).execute();
	}

	@Override
	public void deleteMechanic(Long idMecanico) throws BusinessException {
		new DeleteMechanic(idMecanico).execute();
	}

	@Override
	public void updateMechanic(MechanicDto mecanico) throws BusinessException {
		new UpdateMechanic(mecanico).execute();
	}

	@Override
	public MechanicDto findMechanicById(Long id) throws BusinessException {
		return null;
	}

	@Override
	public List<MechanicDto> findAllMechanics() throws BusinessException {
		return new ListMechanic().execute();
	}
}
