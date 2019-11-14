package uo.ri.business.manager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.CourseDto;
import uo.ri.business.dto.VehicleTypeDto;
import uo.ri.common.BusinessException;
import uo.ri.common.factories.PersistenceFactory;
import uo.ri.persistence.impl.CourseGatewayImpl;

public class ListCourse {

	private Connection con;
	private CourseGatewayImpl coGat = PersistenceFactory.getCourseGatewayImpl();

	public List<CourseDto> findAllCourses() throws BusinessException {
		List<CourseDto> list = new ArrayList<>();
		
		try {
			con = Jdbc.createThreadConnection();
			con.setAutoCommit(false);
			
			list = coGat.findAllCourses();
			
			if(list.isEmpty()) {
				throw new BusinessException("There are no courses to list");
			}
			
			con.commit();
			return list;
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<VehicleTypeDto> findAllVehicleTypes() throws BusinessException {
		return null;
	}

	public CourseDto findCourseById(Long cId) throws BusinessException {
		try {
			con = Jdbc.createThreadConnection();
			con.setAutoCommit(false);

			CourseDto dto = coGat.findCourseById(cId);

			if (dto == null) {
				throw new BusinessException(
						"The course with id " + cId + " could not be found");
			}

			con.commit();
			return dto;
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
}
