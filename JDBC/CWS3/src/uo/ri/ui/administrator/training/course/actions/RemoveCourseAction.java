package uo.ri.ui.administrator.training.course.actions;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.servicesLayer.CourseCrudService;
import uo.ri.common.BusinessException;
import uo.ri.common.factories.ServicesFactory;

public class RemoveCourseAction implements Action {

	@Override
	public void execute() throws BusinessException {

		// Ask the user for data
		Long cId = Console.readLong("Course id");

		// Invoke the service
		CourseCrudService cs = ServicesFactory.getCourseCrudService();
		cs.deleteCourse( cId );

		// Show result
		Console.println("Course removed");
	}

}
