package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import dto.Savjetnik;
import oodb.ConnectionPool;

public class SavjetnikDAO {

	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();

	private static final String SQL_SELECT_ALL = "SELECT * FROM savjetnik";
	private static final String SQL_SELECT_ONE_ID = "SELECT * FROM savjetnik WHERE id=?";
	private static final String SQL_SELECT_ONE_USER_NAME = "SELECT * FROM savjetnik WHERE korisnicko_ime=?";
	private static final String SQL_INSERT = "INSERT INTO savjetnik (ime, prezime, korisnicko_ime, lozinka) VALUES (?, ?, ?, ?)";
	private static final String SQL_UPDATE = "UPDATE savjetnik SET ime=?, prezime=?, korisnicko_ime=?, lozinka=? WHERE id=?";
	private static final String SQL_DELETE = "DELETE FROM savjetnik WHERE id=?";

	public static Set<Savjetnik> odaberiSve() {
		Set<Savjetnik> retVal = new HashSet<Savjetnik>();
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = {};
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_SELECT_ALL, false, values);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retVal.add(new Savjetnik(rs.getInt("id"), rs.getString("ime"), rs.getString("prezime"),
						rs.getString("korisnicko_ime"), rs.getString("lozinka")));
			}
			pstmt.close();
		} catch (SQLException exp) {
			retVal = null;
		} finally {
			connectionPool.checkIn(connection);
		}
		return retVal;
	}

	public static Savjetnik vratiPoId(Integer id) {
		Savjetnik retVal = null;
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = { id };
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_SELECT_ONE_ID, false, values);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retVal = new Savjetnik(rs.getInt("id"), rs.getString("ime"), rs.getString("prezime"),
						rs.getString("korisnicko_ime"), rs.getString("lozinka"));
			}
			pstmt.close();
		} catch (SQLException exp) {
			retVal = null;
		} finally {
			connectionPool.checkIn(connection);
		}
		return retVal;
	}

	public static boolean provjeriDostupnostKorisnickogImena(String korisnickoIme) {
		boolean retVal = false;
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = { korisnickoIme };
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_SELECT_ONE_USER_NAME, false, values);
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

	public static boolean dodaj(Savjetnik savjetnik) {
		boolean retVal = false;
		Connection connection = null;
		try {

			ResultSet generatedKeys = null;
			Object values[] = { savjetnik.getIme(), savjetnik.getPrezime(), savjetnik.getKorisnickoIme(),
					savjetnik.getLozinka() };
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_INSERT, true, values);
			int affectedRows = pstmt.executeUpdate();
			if (affectedRows == 0)
				retVal = false;
			else
				retVal = true;
			generatedKeys = pstmt.getGeneratedKeys();
			if (generatedKeys.next())
				savjetnik.setId(generatedKeys.getInt(1));
			pstmt.close();
		} catch (SQLException e) {
			retVal = false;
		} finally {
			connectionPool.checkIn(connection);
		}
		return retVal;
	}

	public static boolean uredi(Savjetnik savjetnik) {
		boolean retVal = false;
		Connection connection = null;
		Object values[] = { savjetnik.getIme(), savjetnik.getPrezime(), savjetnik.getKorisnickoIme(),
				savjetnik.getLozinka(), savjetnik.getId() };
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

	public static boolean obrisi(Savjetnik savjetnik) {
		boolean retVal = false;
		Connection connection = null;
		Object values[] = { savjetnik.getId() };
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
