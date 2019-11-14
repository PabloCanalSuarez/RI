package uo.ri.business.servicesLayer.implementation;

import java.util.List;

import uo.ri.business.dto.CertificateDto;
import uo.ri.business.dto.CourseForRow;
import uo.ri.business.dto.TrainingForMechanicRow;
import uo.ri.business.dto.TrainingHoursRow;
import uo.ri.business.manager.CourseReport;
import uo.ri.business.servicesLayer.CourseReportService;
import uo.ri.common.BusinessException;

public class CourseReportServiceImpl implements CourseReportService {

	@Override
	public List<TrainingForMechanicRow> findTrainigByMechanicId(Long id)
			throws BusinessException {
		return null;
	}

	@Override
	public List<TrainingHoursRow> findTrainingByVehicleTypeAndMechanic()
			throws BusinessException {
		return null;
	}

	@Override
	public List<CertificateDto> findCertificatedByVehicleType()
			throws BusinessException {
		return new CourseReport().findCertificatedByVehicleType();
	}

	@Override
	public List<CourseForRow> findCoursesWithAssistants() {
		return new CourseReport().findCoursesWithAssistants();
	}

}
