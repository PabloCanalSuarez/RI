package uo.ri.ui.foreman;

import alb.util.menu.BaseMenu;
import alb.util.menu.NotYetImplementedAction;
import uo.ri.ui.foreman.action.UpdateBreakdownAction;
import uo.ri.ui.foreman.action.AddBreakdownAction;

public class ClientReceptionsMenu extends BaseMenu {

	public ClientReceptionsMenu() {
		menuOptions = new Object[][] { { "Foreman > Check in menu", null },

				{ "Add breakdown", AddBreakdownAction.class },
				{ "Update breakdown", UpdateBreakdownAction.class },
				{ "Delete breakdown", NotYetImplementedAction.class },
				{ "", null },
				{ "List breakdown", NotYetImplementedAction.class },
				{ "Check a breakdown", NotYetImplementedAction.class },
				{ "", null },
				{ "List mechanics", NotYetImplementedAction.class },
				{ "Assign a breakdown", NotYetImplementedAction.class }, };
	}

}
