package uo.ri.persistence.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.CourseDto;
import uo.ri.business.dto.VehicleTypeDto;
import uo.ri.conf.Conf;
import uo.ri.persistence.CourseGateway;

public class CourseGatewayImpl implements CourseGateway {

	private Connection con;

	@Override
	public CourseDto registerNew(CourseDto dto) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		CourseDto course = new CourseDto();

		try {
			con = Jdbc.getCurrentConnection();
			con.setAutoCommit(false);

			pst = con.prepareStatement(
					Conf.getInstance().getProperty("SQL_REGISTER_COURSE"));
			pst.setString(1, dto.name);
			pst.setString(2, dto.description);
			pst.setDate(3, new Date(dto.startDate.getTime()));
			pst.setInt(4, dto.hours);

			pst.executeUpdate();

			Long id = retrieveGeneratedKey();

			course = dto;
			course.id = id;

			return course;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	private Long retrieveGeneratedKey() {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			con = Jdbc.getCurrentConnection();
			con.setAutoCommit(false);

			pst = con.prepareStatement(
					Conf.getInstance().getProperty("SQL_GET_KEY_COURSE"));
			rs = pst.executeQuery();

			return rs.getLong("id");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public void updateCourse(CourseDto dto) {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			con = Jdbc.getCurrentConnection();
			con.setAutoCommit(false);

			pst = con.prepareStatement(
					Conf.getInstance().getProperty("SQL_UPDATE_COURSE"));
			pst.setString(1, dto.name);
			pst.setString(2, dto.description);
			pst.setDate(3, new Date(dto.startDate.getTime()));
			pst.setDate(3, new Date(dto.endDate.getTime()));

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public void deleteCourse(Long id) {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			con = Jdbc.getCurrentConnection();
			con.setAutoCommit(false);

			pst = con.prepareStatement(
					Conf.getInstance().getProperty("SQL_DELETE_COURSE"));
			pst.setLong(1, id);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public List<CourseDto> findAllCourses() {
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<CourseDto> list = new ArrayList<>();

		try {
			con = Jdbc.getCurrentConnection();

			pst = con.prepareStatement(
					Conf.getInstance().getProperty("SQL_FIND_ALL_COURSES"));

			rs = pst.executeQuery();
			while (rs.next()) {
				CourseDto dtoAux = new CourseDto();
				dtoAux.id = rs.getLong("id");
				dtoAux.name = rs.getString("name");
				dtoAux.description = rs.getString("description");
				dtoAux.hours = rs.getInt("hours");
				dtoAux.startDate = rs.getDate("startdate");
				dtoAux.endDate = rs.getDate("enddate");
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
	public List<VehicleTypeDto> findAllVehicleTypes() {
		return null;
	}

	@Override
	public CourseDto findCourseById(Long cId) {
		CourseDto dto = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			con = Jdbc.getCurrentConnection();
			con.setAutoCommit(false);

			pst = con.prepareStatement(Conf.getInstance()
					.getProperty("SQL_CHECK_EXISTING_COURSE"));
			pst.setLong(1, cId);
			rs = pst.executeQuery();

			if (rs.next()) {
				dto = new CourseDto();
				dto.id = rs.getLong("id");
				dto.name = rs.getString("name");
				dto.description = rs.getString("description");
				dto.startDate = rs.getDate("startDate");
				dto.endDate = rs.getDate("endDate");
				dto.hours = rs.getInt("hours");
			}

			return dto;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}

	}

}
