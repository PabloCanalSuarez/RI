package uo.ri.ui.administrator.training.reports;

import alb.util.menu.BaseMenu;
import alb.util.menu.NotYetImplementedAction;
import uo.ri.ui.administrator.training.reports.actions.ListCertificationsByVehicleTypeAction;
import uo.ri.ui.administrator.training.reports.actions.ListCoursesWithAssistants;

public class ReportsMenu extends BaseMenu {

	public ReportsMenu() {
		menuOptions = new Object[][] {
				{ "Manager > Training management > Reports", null },

				{ "Training of a mechanic", NotYetImplementedAction.class },
				{ "Training by vehicle types", NotYetImplementedAction.class },
				{ "Certifications by vehicle type",
						ListCertificationsByVehicleTypeAction.class }, 
				{ "Name of courses and number of assistants", ListCoursesWithAssistants.class}};
	}

}
