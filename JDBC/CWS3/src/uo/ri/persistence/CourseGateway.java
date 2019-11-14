package uo.ri.persistence;

import java.util.List;

import uo.ri.business.dto.CourseDto;
import uo.ri.business.dto.VehicleTypeDto;
import uo.ri.common.BusinessException;

public interface CourseGateway {

	public CourseDto registerNew(CourseDto dto) throws BusinessException;

	public void updateCourse(CourseDto dto) throws BusinessException;

	public void deleteCourse(Long id) throws BusinessException;

	public List<CourseDto> findAllCourses() throws BusinessException;

	public List<VehicleTypeDto> findAllVehicleTypes() throws BusinessException;

	public CourseDto findCourseById(Long cId) throws BusinessException;
}
