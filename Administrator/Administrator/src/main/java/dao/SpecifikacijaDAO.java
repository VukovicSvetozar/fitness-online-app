package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import dto.Specifikacija;
import oodb.ConnectionPool;

public class SpecifikacijaDAO {

	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();

	private static final String SQL_SELECT_ALL_ID = "SELECT * FROM specifikacija WHERE kategorija_id=?";
	private static final String SQL_SELECT_ONE_ID = "SELECT * FROM specifikacija WHERE id=?";
	private static final String SQL_INSERT = "INSERT INTO specifikacija (naziv, kategorija_id) VALUES (?, ?)";
	private static final String SQL_DELETE = "DELETE FROM specifikacija WHERE id=?";

	public static Set<Specifikacija> odaberiSveIzKategorije(Specifikacija specifikacija) {
		Set<Specifikacija> retVal = new HashSet<Specifikacija>();
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = { specifikacija.getIdKategorija() };
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_SELECT_ALL_ID, false, values);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retVal.add(new Specifikacija(rs.getInt("id"), rs.getString("naziv"), rs.getInt("kategorija_id")));
			}
			pstmt.close();
		} catch (SQLException exp) {
			retVal = null;
		} finally {
			connectionPool.checkIn(connection);
		}
		return retVal;
	}

	public static Specifikacija vratiPoId(Integer id) {
		Specifikacija retVal = null;
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = { id };
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_SELECT_ONE_ID, false, values);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retVal = new Specifikacija(rs.getInt("id"), rs.getString("naziv"), rs.getInt("kategorija_id"));
			}
			pstmt.close();
		} catch (SQLException exp) {
			retVal = null;
		} finally {
			connectionPool.checkIn(connection);
		}
		return retVal;
	}

	public static boolean dodaj(Specifikacija specifikacija) {
		boolean retVal = false;
		Connection connection = null;
		ResultSet generatedKeys = null;
		Object values[] = { specifikacija.getNaziv(), specifikacija.getIdKategorija() };
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_INSERT, true, values);
			int affectedRows = pstmt.executeUpdate();
			if (affectedRows == 0)
				retVal = false;
			else
				retVal = true;
			generatedKeys = pstmt.getGeneratedKeys();
			if (generatedKeys.next())
				specifikacija.setId(generatedKeys.getInt(1));
			pstmt.close();
		} catch (SQLException e) {
			retVal = false;
		} finally {
			connectionPool.checkIn(connection);
		}
		return retVal;
	}

	public static boolean obrisi(Specifikacija specifikacija) {
		boolean retVal = false;
		Connection connection = null;
		Object values[] = { specifikacija.getId() };
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_DELETE, false, values);
			int affectedRows = pstmt.executeUpdate();
			if (affectedRows == 0)
				retVal = false;
			else
				retVal = true;
			pstmt.close();
		} catch (SQLException e) {
			retVal = false;
		} finally {
			connectionPool.checkIn(connection);
		}
		return retVal;
	}

}
