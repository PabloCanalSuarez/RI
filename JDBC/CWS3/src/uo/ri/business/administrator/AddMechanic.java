package uo.ri.business.administrator;

import java.sql.Connection;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.MechanicDto;
import uo.ri.common.BusinessException;
import uo.ri.common.factories.PersistenceFactory;
import uo.ri.persistence.impl.MechanicGatewayImpl;

public class AddMechanic {

//	private static String SQL_INSERT = "insert into TMechanics(dni, name, surname) values (?, ?, ?)";
//	private static String SQL_CHECK = "SELECT DNI FROM TMechanics WHERE DNI = ?";

	private MechanicDto mechanic = null;
	MechanicGatewayImpl mg = PersistenceFactory.getMechanicGateway();

	public AddMechanic(MechanicDto m) {
		// DTO --> Data Transfer Object
		this.mechanic = m;
	}

	public void execute() throws BusinessException {
		// Process

		Connection c = null;

		try {
			c = Jdbc.createThreadConnection();
			c.setAutoCommit(false);

			if (mg.findByDNI(mechanic.dni) != null) {
				throw new BusinessException(
						"Mechanic " + mechanic.dni + " already exists");
			}

			mg.add(mechanic);

			c.commit();
			Jdbc.close(c);
		} catch (SQLException e) {
			try {
				c.rollback();
			} catch (SQLException e1) {
			}
			throw new RuntimeException("unrecoverable error reading database");
		}
	}

}
