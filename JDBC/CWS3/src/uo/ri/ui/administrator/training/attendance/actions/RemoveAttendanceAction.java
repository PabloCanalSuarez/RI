package uo.ri.ui.administrator.training.attendance.actions;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.servicesLayer.CourseAttendanceService;
import uo.ri.common.factories.ServicesFactory;

public class RemoveAttendanceAction implements Action {

	@Override
	public void execute() throws Exception {
		// Ask the user for data
		Long attId = Console.readLong("Attendance id");

		// Invoke the service
		CourseAttendanceService cs = ServicesFactory.getCourseAttendanceService();
		cs.deleteAttendace( attId );

		// Show result
		Console.println("Course attendance removed");
	}

}
