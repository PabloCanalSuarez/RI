package uo.ri.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.MechanicDto;
import uo.ri.conf.Conf;
import uo.ri.persistence.Gateway;

public class MechanicGatewayImpl implements Gateway {

	@Override
	public void add(Object o) {
		MechanicDto mechanic = (MechanicDto) o;
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(
					Conf.getInstance().getProperty("SQL_INSERT"));
			pst.setString(1, mechanic.dni);
			pst.setString(2, mechanic.name);
			pst.setString(3, mechanic.surname);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public void delete(Long id) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(
					Conf.getInstance().getProperty("SQL_DELETE_MECHANIC"));
			pst.setLong(1, id);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public void update(Object o) {
		MechanicDto mechanic = (MechanicDto) o;

		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(
					Conf.getInstance().getProperty("SQL_UPDATE_MECHANIC"));
			pst.setString(1, mechanic.name);
			pst.setString(2, mechanic.surname);
			pst.setLong(3, mechanic.id);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public List<Object> findAll() {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<Object> list = new ArrayList<>();

		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(
					Conf.getInstance().getProperty("SQL_FIND_ALL_MECHANICS"));

			rs = pst.executeQuery();
			while (rs.next()) {
				MechanicDto dtoAux = new MechanicDto();
				dtoAux.id = rs.getLong(1);
				dtoAux.dni = rs.getString(2);
				dtoAux.name = rs.getString(3);
				dtoAux.surname = rs.getString(4);
				list.add(dtoAux);
			}

			return list;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public Object findById(Long id) {
		Connection c = null;
		ResultSet rsCheck = null;
		PreparedStatement pst = null;
		try {
			MechanicDto m = null;
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(
					Conf.getInstance().getProperty("SQL_FIND_MECHANIC_BY_ID"));
			pst.setLong(1, id);
			rsCheck = pst.executeQuery();

			if (rsCheck.next()) {
				m = new MechanicDto();
				m.id = rsCheck.getLong("id");
				m.name = rsCheck.getString("name");
				m.surname = rsCheck.getString("surname");
				m.dni = rsCheck.getString("dni");
			}

			return m;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rsCheck, pst);
		}
	}

	public MechanicDto findByDNI(String dni) {
		Connection c = null;
		ResultSet rsCheck = null;
		PreparedStatement pst = null;
		try {
			MechanicDto m = null;
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(
					Conf.getInstance().getProperty("SQL_CHECK"));
			pst.setString(1, dni);
			rsCheck = pst.executeQuery();

			if (rsCheck.next()) {
				m = new MechanicDto();
				m.id = rsCheck.getLong("id");
				m.name = rsCheck.getString("name");
				m.surname = rsCheck.getString("surname");
				m.dni = rsCheck.getString("dni");
			}
			c.close();
			return m;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rsCheck, pst);
		}
	}
}
