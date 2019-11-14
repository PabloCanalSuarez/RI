package uo.ri.business.administrator;

import java.sql.*;

import alb.util.jdbc.Jdbc;
import uo.ri.common.BusinessException;
import uo.ri.common.factories.PersistenceFactory;
import uo.ri.persistence.Gateway;

public class DeleteMechanic {

	private Long mechanicID = null;
	private Gateway mg = PersistenceFactory.getMechanicGateway();

	public DeleteMechanic(Long id) {
		// DTO --> Data Transfer Object
		this.mechanicID = id;
	}

	public void execute() throws BusinessException {
		Connection c = null;

		try {
			c = Jdbc.createThreadConnection();

			if (mg.findById(mechanicID) == null) {
				throw new BusinessException("The mechanic with id " + mechanicID
						+ " does not exist");
			}

			mg.delete(mechanicID);
			c.commit();
		} catch (SQLException e) {
			try {
				c.rollback();
			} catch (SQLException e1) {
			}
			throw new RuntimeException("unrecoverable error reading database");
		} finally {
			Jdbc.close(c);
		}
	}

}
