package uo.ri.ui.administrator.training.attendance.actions;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.dto.EnrollmentDto;
import uo.ri.business.servicesLayer.CourseAttendanceService;
import uo.ri.common.BusinessException;
import uo.ri.common.factories.ServicesFactory;

public class RegisterAttendanceAction implements Action {

	private AttendanceUserInteractor user = new AttendanceUserInteractor();

	@Override
	public void execute() throws BusinessException {

		// Ask the user for data
		EnrollmentDto att = new EnrollmentDto();
		user.fill( att );

		// Invoke the service
		CourseAttendanceService cs = ServicesFactory.getCourseAttendanceService();
		cs.registerNew( att );

		// Show result
		Console.println("Attendance registered:" + att.id);
	}

}
