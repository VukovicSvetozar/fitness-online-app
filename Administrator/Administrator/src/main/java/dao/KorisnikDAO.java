package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import dto.Korisnik;
import oodb.ConnectionPool;

public class KorisnikDAO {

	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();

	private static final String SQL_SELECT_ALL = "SELECT * FROM korisnik";
	private static final String SQL_SELECT_ONE_ID = "SELECT * FROM korisnik WHERE id=?";
	private static final String SQL_SELECT_ONE_USER_NAME = "SELECT * FROM korisnik WHERE korisnicko_ime=?";
	private static final String SQL_SELECT_ONE_EMAIL = "SELECT * FROM korisnik WHERE email=?";
	private static final String SQL_INSERT = "INSERT INTO korisnik (ime, prezime, grad, email, broj_telefona, korisnicko_ime, lozinka, avatar_url, datum_pristupa, kljuc, uloga, pretplata ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_UPDATE = "UPDATE korisnik SET ime=?, prezime=?, grad=?, email=?, broj_telefona=?, korisnicko_ime=?, lozinka=?, avatar_url=?, datum_pristupa=?, kljuc=?, uloga=?, pretplata=? WHERE id=?";
	private static final String SQL_DELETE = "DELETE FROM korisnik WHERE id=?";

	public static Set<Korisnik> odaberiSve() {
		Set<Korisnik> retVal = new HashSet<Korisnik>();
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = {};
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_SELECT_ALL, false, values);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retVal.add(new Korisnik(rs.getInt("id"), rs.getString("ime"), rs.getString("prezime"),
						rs.getString("grad"), rs.getString("email"), rs.getString("broj_telefona"),
						rs.getString("korisnicko_ime"), rs.getString("lozinka"), rs.getString("avatar_url"),
						rs.getString("datum_pristupa"), rs.getString("kljuc"), rs.getString("uloga"),
						rs.getInt("pretplata")));
			}
			pstmt.close();
		} catch (SQLException exp) {
			retVal = null;
		} finally {
			connectionPool.checkIn(connection);
		}
		return retVal;
	}

	public static Korisnik vratiPoId(Integer id) {
		Korisnik retVal = null;
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = { id };
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_SELECT_ONE_ID, false, values);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retVal = new Korisnik(rs.getInt("id"), rs.getString("ime"), rs.getString("prezime"),
						rs.getString("grad"), rs.getString("email"), rs.getString("broj_telefona"),
						rs.getString("korisnicko_ime"), rs.getString("lozinka"), rs.getString("avatar_url"),
						rs.getString("datum_pristupa"), rs.getString("kljuc"), rs.getString("uloga"),
						rs.getInt("pretplata"));
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

	public static boolean provjeriDostupnostEmaila(String email) {
		boolean retVal = false;
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = { email };
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_SELECT_ONE_EMAIL, false, values);
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

	public static boolean dodaj(Korisnik korisnik) {
		boolean retVal = false;
		Connection connection = null;
		try {

			ResultSet generatedKeys = null;
			Object values[] = { korisnik.getIme(), korisnik.getPrezime(), korisnik.getGrad(), korisnik.getEmail(),
					korisnik.getBrojTelefona(), korisnik.getKorisnickoIme(), korisnik.getLozinka(),
					korisnik.getAvatarUrl(), korisnik.getDatumPristupa(), korisnik.getKljuc(),
					korisnik.getUloga().toString(), korisnik.getPretplata() };
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_INSERT, true, values);
			int affectedRows = pstmt.executeUpdate();
			if (affectedRows == 0)
				retVal = false;
			else
				retVal = true;
			generatedKeys = pstmt.getGeneratedKeys();
			if (generatedKeys.next())
				korisnik.setId(generatedKeys.getInt(1));
			pstmt.close();
		} catch (SQLException e) {
			retVal = false;
		} finally {
			connectionPool.checkIn(connection);
		}
		return retVal;
	}

	public static boolean uredi(Korisnik korisnik) {
		boolean retVal = false;
		Connection connection = null;
		Object values[] = { korisnik.getIme(), korisnik.getPrezime(), korisnik.getGrad(), korisnik.getEmail(),
				korisnik.getBrojTelefona(), korisnik.getKorisnickoIme(), korisnik.getLozinka(), korisnik.getAvatarUrl(),
				korisnik.getDatumPristupa(), korisnik.getKljuc(), korisnik.getUloga(), korisnik.getPretplata(),
				korisnik.getId() };
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

	public static boolean obrisi(Korisnik korisnik) {
		boolean retVal = false;
		Connection connection = null;
		Object values[] = { korisnik.getId() };
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
