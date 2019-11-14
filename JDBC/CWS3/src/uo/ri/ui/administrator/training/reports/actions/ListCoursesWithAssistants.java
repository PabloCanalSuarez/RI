package uo.ri.ui.administrator.training.reports.actions;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.dto.CourseForRow;
import uo.ri.business.servicesLayer.CourseReportService;
import uo.ri.common.factories.ServicesFactory;

public class ListCoursesWithAssistants implements Action {

	@Override
	public void execute() throws Exception {
		// PRUEBAS -------------------------
		CourseReportService rs = ServicesFactory.getCourseReportService();
		List<CourseForRow> list = rs.findCoursesWithAssistants();
		
		Console.println("Courses with name and number of assistants:");
//		for(CourseForRow c : list) {
//			Console.println(c.name + "\t" + c.numberAssistants);
//		}
		list.forEach(c -> Console.println(c.name + "\t" + c.numberAssistants));
	}

}
