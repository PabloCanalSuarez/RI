package uo.ri.business.administrator;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.MechanicDto;
import uo.ri.common.BusinessException;
import uo.ri.common.factories.PersistenceFactory;
import uo.ri.persistence.impl.MechanicGatewayImpl;

public class ListMechanic {

	private List<MechanicDto> list = new ArrayList<>();
	MechanicGatewayImpl mg = PersistenceFactory.getMechanicGateway();

	public List<MechanicDto> execute() throws BusinessException {
		Connection c = null;
		List<Object> list1 = new ArrayList<>();
		try {
			c = Jdbc.createThreadConnection();
			c.setAutoCommit(false);

			list1 = mg.findAll();
			if (list1.isEmpty()) {
				throw new BusinessException("List is empty");
			}

			for (Object o : list1) {
				list.add((MechanicDto) o);
			}

			c.commit();
			return list;
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
