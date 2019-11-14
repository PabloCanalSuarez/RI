package uo.ri.ui.administrator.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.dto.MechanicDto;
import uo.ri.business.servicesLayer.implementation.MechanicCrudServiceImpl;
import uo.ri.common.BusinessException;

public class UpdateMechanicAction implements Action {

	@Override
	public void execute() throws BusinessException {

		// Get info
		Long id = Console.readLong("Type mechahic id to update");
		String name = Console.readString("Name");
		String surname = Console.readString("Surname");

		MechanicDto m = new MechanicDto();
		m.id = id;
		m.name = name;
		m.surname = surname;

		// new UpdateMechanic(m).execute();
		new MechanicCrudServiceImpl().updateMechanic(m);

		// Print result
		Console.println("Mechanic updated");
	}

}
