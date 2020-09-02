package AlopeciaCollege.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import AlopeciaCollege.servicios.Conexion;

/**
 * Servlet implementation class UsuarioDAO
 */

public class UsuarioDAO {
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	
	//QUERY LOGIN USUARIO
	public Usuario login(String email, String pass) throws SQLException {
		
		Usuario u = null;
		String query = "Select nomUsu, email, pass, rol FROM alopeciacollege.usuario WHERE email = ? AND pass = ?";
		con = Conexion.getInstance().getConnection();
		pst = con.prepareStatement(query);
		pst.setString(1, email);
		pst.setString(2, pass);
		
		rs = pst.executeQuery();
		if(rs.next()) {
			u = new Usuario();
			u.setNomUsu(rs.getNString("nomUsu"));
			u.setEmail(rs.getString("email"));
			u.setPass(rs.getString("pass"));
			u.setRol(rs.getString("rol"));
			
		}
		return u;
	}
	
	//QUERY ALTA USUARIO - REGISTER
	public void altaUsuario(Usuario u) throws SQLException {

		String sql = "INSERT INTO alopeciacollege.usuario (DNI, nomUsu, apellidosUsu, sexUsu, fecNac, localidad, telefono, email, pass, nick) VALUES (?,?,?,?,?,?,?,?,?,?)";
		con = Conexion.getInstance().getConnection();
		pst = con.prepareStatement(sql);
		pst.setString(1, u.getDNI());
		pst.setString(2, u.getNomUsu());
		pst.setString(3, u.getApellidosUsu());
		pst.setString(4, u.getSexUsu());
		pst.setString(5, u.getFecNac());
		pst.setString(6, u.getLocalidad());
		pst.setString(7, u.getTelefono());
		pst.setString(8, u.getEmail());
		pst.setString(9, u.getPass());
		pst.setString(10, u.getNick());

		pst.executeUpdate();

	}

	//QUERY ACTUALIZACION NICK Y ROL A PARTIR DE EMAIL - ESTO SER� DESDE MODO ADMIN
	public void modificarUsuario(Usuario u) throws SQLException {

		String sql = "UPDATE alopeciacollege.usuario SET nick = ?, rol = ? WHERE email = ?";
		con = Conexion.getInstance().getConnection();
		pst = con.prepareStatement(sql);
		pst.setString(1, u.getNick());
		pst.setString(2, u.getRol());
		pst.setString(3, u.getEmail());

		pst.executeUpdate();

	}

	//BORRAR USUARIO A PARTIR DEL EMAIL
	public void borrarUsuario(String email) throws SQLException {

		String sql = "DELETE FROM alopeciacollege.usuario WHERE email = ?";
		con = Conexion.getInstance().getConnection();
		pst = con.prepareStatement(sql);
		pst.setString(1, email);

		pst.executeUpdate();

	}
	
	//RECOGER INFO DEL USUARIO
	public Usuario getUsuario(String email) throws SQLException {

		Usuario u = null;
		String sql = "SELECT * FROM alopeciacollege.usuario WHERE email = ?";
		con = Conexion.getInstance().getConnection();
		pst = con.prepareStatement(sql);
		pst.setString(1, email);

		rs = pst.executeQuery();

		if (rs.next()) {
			u = new Usuario();
			u.setEmail(rs.getString("email"));
			u.setNick(rs.getString("nick"));
			u.setRol(rs.getString("rol"));
			u.setLocalidad(rs.getNString("localidad"));
		}

		return u;
	}
}