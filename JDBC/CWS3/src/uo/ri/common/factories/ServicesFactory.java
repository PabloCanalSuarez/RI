package uo.ri.common.factories;

import uo.ri.business.servicesLayer.CourseAttendanceService;
import uo.ri.business.servicesLayer.CourseReportService;
import uo.ri.business.servicesLayer.implementation.CertificateServiceImpl;
import uo.ri.business.servicesLayer.implementation.CourseCrudServiceImpl;
import uo.ri.business.servicesLayer.implementation.CourseReportServiceImpl;
import uo.ri.business.servicesLayer.implementation.InvoiceServiceImpl;
import uo.ri.business.servicesLayer.implementation.MechanicCrudServiceImpl;

public class ServicesFactory {

	public static MechanicCrudServiceImpl getMechanicCrudService() {
		return new MechanicCrudServiceImpl();
	}
	
	public static CourseCrudServiceImpl getCourseCrudService() {
		return new CourseCrudServiceImpl();
	}
	
	public static InvoiceServiceImpl getInvoiceCrudService() {
		return new InvoiceServiceImpl();
	}
	
	public static CertificateServiceImpl getCertificateService() {
		return new CertificateServiceImpl();
	}

	public static CourseAttendanceService getCourseAttendanceService() {
		return null;
	}

	public static CourseReportService getCourseReportService() {
		return new CourseReportServiceImpl();
	}
}
