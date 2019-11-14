package uo.ri.business.manager;

import java.sql.Connection;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.common.BusinessException;
import uo.ri.common.factories.PersistenceFactory;
import uo.ri.persistence.impl.CourseGatewayImpl;

public class DeleteCourse {

	private Long id;
	private Connection con;
	private CourseGatewayImpl coGat = PersistenceFactory.getCourseGatewayImpl();
	
	public DeleteCourse(Long id) {
		this.id = id;
	}
	
	public void execute() throws BusinessException {
		try {
			con = Jdbc.createThreadConnection();
			con.setAutoCommit(false);

			if (coGat.findCourseById(id) == null) {
				throw new BusinessException(
						"The course with id " + id + " does not exist");
			}

			coGat.deleteCourse(id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
