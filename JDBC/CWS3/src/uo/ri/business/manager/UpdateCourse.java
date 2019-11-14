package uo.ri.business.manager;

import java.sql.Connection;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.CourseDto;
import uo.ri.common.BusinessException;
import uo.ri.common.factories.PersistenceFactory;
import uo.ri.persistence.impl.CourseGatewayImpl;

public class UpdateCourse {

	private Connection con;
	private CourseDto dto;
	private CourseGatewayImpl coGat = PersistenceFactory.getCourseGatewayImpl();

	public UpdateCourse(CourseDto dto) {
		this.dto = dto;
	}

	public void execute() throws BusinessException {
		try {
			con = Jdbc.createThreadConnection();
			con.setAutoCommit(false);

			if (coGat.findCourseById(dto.id) == null) {
				throw new BusinessException(
						"The course with id " + dto.id + " does not exist");
			}

			coGat.updateCourse(dto);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
