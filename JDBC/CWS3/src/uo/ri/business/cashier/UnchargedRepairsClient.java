package uo.ri.business.cashier;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.BreakdownDto;
import uo.ri.common.BusinessException;
import uo.ri.common.factories.PersistenceFactory;
import uo.ri.persistence.impl.ClientGatewayImpl;
import uo.ri.persistence.impl.WorkOrderGatewayImpl;

public class UnchargedRepairsClient {

	private String dni;
	private Connection con;
	private List<BreakdownDto> list = new ArrayList<>();

	private WorkOrderGatewayImpl woGat = PersistenceFactory
			.getWorkOrderGateway();
	private ClientGatewayImpl cliGat = PersistenceFactory
			.getClientGatewayImpl();

	public UnchargedRepairsClient(String dni) {
		this.dni = dni;
	}

	public List<BreakdownDto> execute() throws BusinessException {
		try {
			con = Jdbc.createThreadConnection();
			con.setAutoCommit(false);
			
			Long id = cliGat.getClientID(dni);

			if(id == null) {
				throw new BusinessException(
						"A client with dni " + dni + " does not exist.");
			}
			
			list = woGat.getUnchargedWorkorders(id);

			return list;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(con);
		}
	}

}
