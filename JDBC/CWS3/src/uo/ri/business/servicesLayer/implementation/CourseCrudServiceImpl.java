package uo.ri.business.servicesLayer.implementation;

import java.util.List;

import uo.ri.business.dto.CourseDto;
import uo.ri.business.dto.VehicleTypeDto;
import uo.ri.business.manager.DeleteCourse;
import uo.ri.business.manager.ListCourse;
import uo.ri.business.manager.RegisterCourse;
import uo.ri.business.manager.UpdateCourse;
import uo.ri.business.servicesLayer.CourseCrudService;
import uo.ri.common.BusinessException;

public class CourseCrudServiceImpl implements CourseCrudService {

	@Override
	public CourseDto registerNew(CourseDto dto) throws BusinessException {
		return new RegisterCourse(dto).execute();
	}

	@Override
	public void updateCourse(CourseDto dto) throws BusinessException {
		new UpdateCourse(dto).execute();
	}

	@Override
	public void deleteCourse(Long id) throws BusinessException {
		new DeleteCourse(id).execute();
	}

	@Override
	public List<CourseDto> findAllCourses() throws BusinessException {
		return new ListCourse().findAllCourses();
	}

	@Override
	public List<VehicleTypeDto> findAllVehicleTypes() throws BusinessException {
		return new ListCourse().findAllVehicleTypes();
	}

	@Override
	public CourseDto findCourseById(Long cId) throws BusinessException {
		return new ListCourse().findCourseById(cId);
	}

}
