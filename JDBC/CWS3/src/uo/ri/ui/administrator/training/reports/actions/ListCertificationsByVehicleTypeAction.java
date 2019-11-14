package uo.ri.ui.administrator.training.reports.actions;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.dto.CertificateDto;
import uo.ri.business.servicesLayer.CourseReportService;
import uo.ri.common.factories.ServicesFactory;
import uo.ri.ui.administrator.Printer;

public class ListCertificationsByVehicleTypeAction implements Action {

	@Override
	public void execute() throws Exception {

		CourseReportService rs = ServicesFactory.getCourseReportService();
		List<CertificateDto> rows = rs.findCertificatedByVehicleType();

		Console.println("Certificates by vehicle type");
		rows.forEach( r ->
			Printer.printCertificateRow( r )
		);
	}

}
