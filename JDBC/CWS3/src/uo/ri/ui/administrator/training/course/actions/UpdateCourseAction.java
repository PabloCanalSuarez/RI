package uo.ri.ui.administrator.training.course.actions;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.dto.CourseDto;
import uo.ri.business.servicesLayer.CourseCrudService;
import uo.ri.common.BusinessException;
import uo.ri.common.factories.ServicesFactory;

public class UpdateCourseAction implements Action {

	private CourseUserInteractor user = new CourseUserInteractor();

	@Override
	public void execute() throws BusinessException {

		// Ask the user for data
		Long cId = Console.readLong("Course id");
		CourseCrudService cs = ServicesFactory.getCourseCrudService();
		CourseDto c = cs.findCourseById(cId);

		user.fill(c);

		// Invoke the service
		cs.updateCourse(c);

		// Show result
		Console.println("Course updated");
	}
}
