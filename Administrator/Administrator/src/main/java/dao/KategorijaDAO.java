package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import dto.Kategorija;
import oodb.ConnectionPool;

public class KategorijaDAO {

	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();

	private static final String SQL_SELECT_ALL = "SELECT * FROM kategorija";
	private static final String SQL_SELECT_ONE_ID = "SELECT * FROM kategorija WHERE id=?";
	private static final String SQL_SELECT_ONE_NAME = "SELECT * FROM kategorija WHERE naziv=?";
	private static final String SQL_INSERT = "INSERT INTO kategorija (naziv) VALUES (?)";
	private static final String SQL_UPDATE = "UPDATE kategorija SET naziv=? WHERE id=?";
	private static final String SQL_DELETE = "DELETE FROM kategorija WHERE id=?";

	public static Set<Kategorija> odaberiSve() {
		Set<Kategorija> retVal = new HashSet<Kategorija>();
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = {};
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_SELECT_ALL, false, values);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retVal.add(new Kategorija(rs.getInt("id"), rs.getString("naziv")));
			}
			pstmt.close();
		} catch (SQLException exp) {
			retVal = null;
		} finally {
			connectionPool.checkIn(connection);
		}
		return retVal;
	}

	public static Kategorija vratiPoId(Integer id) {
		Kategorija retVal = null;
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = { id };
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_SELECT_ONE_ID, false, values);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retVal = new Kategorija(rs.getInt("id"), rs.getString("naziv"));
			}
			pstmt.close();
		} catch (SQLException exp) {
			retVal = null;
		} finally {
			connectionPool.checkIn(connection);
		}
		return retVal;
	}

	public static boolean provjeriNaziv(String nazivKategorije) {
		boolean retVal = false;
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = { nazivKategorije };
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_SELECT_ONE_NAME, false, values);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retVal = true;
			}
			pstmt.close();
		} catch (SQLException exp) {
			retVal = false;
		} finally {
			connectionPool.checkIn(connection);
		}
		return retVal;
	}

	public static boolean dodaj(Kategorija kategorija) {
		boolean retVal = false;
		Connection connection = null;
		ResultSet generatedKeys = null;
		Object values[] = { kategorija.getNaziv() };
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
				kategorija.setId(generatedKeys.getInt(1));
			pstmt.close();
		} catch (SQLException e) {
			retVal = false;
		} finally {
			connectionPool.checkIn(connection);
		}
		return retVal;
	}

	public static boolean uredi(Kategorija kategorija) {
		boolean retVal = false;
		Connection connection = null;
		Object values[] = { kategorija.getNaziv(), kategorija.getId() };
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_UPDATE, false, values);
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

	public static boolean obrisi(Kategorija kategorija) {
		boolean retVal = false;
		Connection connection = null;
		Object values[] = { kategorija.getId() };
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
