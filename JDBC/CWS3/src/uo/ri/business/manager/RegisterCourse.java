package uo.ri.business.manager;

import java.sql.Connection;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.CourseDto;
import uo.ri.common.factories.PersistenceFactory;
import uo.ri.persistence.impl.CourseGatewayImpl;

public class RegisterCourse {

	private Connection con;
	private CourseGatewayImpl coGat = PersistenceFactory.getCourseGatewayImpl();
	private CourseDto dto = null;

	public RegisterCourse(CourseDto dto) {
		this.dto = dto;
	}

	public CourseDto execute() {
		try {
			con = Jdbc.createThreadConnection();
			con.setAutoCommit(false);

			return coGat.registerNew(dto);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
