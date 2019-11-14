package uo.ri.business.administrator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.MechanicDto;
import uo.ri.common.BusinessException;
import uo.ri.common.factories.PersistenceFactory;
import uo.ri.persistence.impl.MechanicGatewayImpl;

public class UpdateMechanic {

	private MechanicDto mechanic;
	MechanicGatewayImpl mg = PersistenceFactory.getMechanicGateway();

	public UpdateMechanic(MechanicDto m) {
		this.mechanic = m;
	}

	public void execute() throws BusinessException {
		// Process
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.createThreadConnection();
			c.setAutoCommit(false);

			if (mg.findById(mechanic.id) == null) {
				throw new BusinessException("The mechanic with id "
						+ mechanic.id + " does not exist");
			}

			mg.update(mechanic);

			c.commit();

		} catch (SQLException e) {
			try {
				c.rollback();
			} catch (SQLException e1) {
			}
			throw new RuntimeException("unrecoverable error reading database");
		} finally {
			Jdbc.close(rs, pst, c);
		}
	}

}
