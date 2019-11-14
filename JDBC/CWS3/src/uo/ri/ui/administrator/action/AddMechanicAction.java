package uo.ri.ui.administrator.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.dto.MechanicDto;
import uo.ri.common.BusinessException;
import uo.ri.common.factories.ServicesFactory;

public class AddMechanicAction implements Action {

	@Override
	public void execute() throws BusinessException {

		// Get info
		String dni = Console.readString("Dni");
		String name = Console.readString("Name");
		String surname = Console.readString("Surname");

		// Data validation
		MechanicDto m = new MechanicDto();
		m.dni = dni;
		m.name = name;
		m.surname = surname;
		
		ServicesFactory.getMechanicCrudService().addMechanic(m);

		// Print result
		Console.println("Mechanic added");
	}

}
