package uo.ri.ui.administrator.training.attendance.actions;

import java.util.List;

import alb.util.menu.Action;
import uo.ri.business.dto.EnrollmentDto;
import uo.ri.business.servicesLayer.CourseAttendanceService;
import uo.ri.common.factories.ServicesFactory;
import uo.ri.ui.administrator.Printer;

public class ListAttendanceToCourseAction implements Action {

	private AttendanceUserInteractor user = new AttendanceUserInteractor();

	@Override
	public void execute() throws Exception {
		Long cId = user.askForCourseId();

		CourseAttendanceService s = ServicesFactory.getCourseAttendanceService();
		List<EnrollmentDto> attendance = s.findAttendanceByCourseId( cId );

		attendance.forEach( att -> Printer.printAttendingMechanic(att) );
	}

}
