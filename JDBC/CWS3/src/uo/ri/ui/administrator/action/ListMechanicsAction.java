package uo.ri.ui.administrator.action;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.administrator.ListMechanic;
import uo.ri.business.dto.MechanicDto;
import uo.ri.common.BusinessException;

public class ListMechanicsAction implements Action {

	@Override
	public void execute() throws BusinessException {

		Console.println("\nList of mechanics \n");

		List<MechanicDto> list = new ListMechanic().execute();

		for (MechanicDto m : list) {
			Console.printf("\t%d %s %s %s\n", m.id, m.dni, m.name, m.surname);
		}
	}
}
