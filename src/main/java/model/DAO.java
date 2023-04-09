package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class DAO.
 */
public class DAO {

	/** The driver. */
	private String driver = "com.mysql.cj.jdbc.Driver";

	/** The url. */
	private String url = "jdbc:mysql://127.0.0.1:3306/" + "dbagenda?useTimezone=true&serverTimezone=UTC";

	/** The user. */
	private String user = "root";

	/** The password. */
	private String password = "mateusophia0508";

	/**
	 * Conectar.
	 *
	 * @return the connection
	 */
	private Connection conectar() {
		Connection con = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;

		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	/**
	 * Data insert.
	 *
	 * @param contato the contato
	 */
	public void dataInsert(JavaBeans contato) {

		String create = "INSERT INTO CONTATOS (NOME, TELEFONE, EMAIL)" + "VALUES (?, ?, ?)";

		try {

			Connection con = conectar();

			PreparedStatement pst = con.prepareStatement(create);

			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());

			pst.executeUpdate();

			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	/**
	 * Select all data.
	 *
	 * @return the array list
	 */
	public ArrayList<JavaBeans> selectAllData() {

		ArrayList<JavaBeans> contatos = new ArrayList<>();
		String read = "SELECT * FROM CONTATOS ORDER BY nome";

		try {

			Connection con = conectar();

			PreparedStatement pst = con.prepareStatement(read);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				String idcon = rs.getString(1);
				String name = rs.getString(2);
				String phone = rs.getString(3);
				String email = rs.getString(4);

				contatos.add(new JavaBeans(idcon, name, phone, email));
			}
			con.close();

			return contatos;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}

	}

	/**
	 * Edits the data.
	 *
	 * @param contato the contato
	 */
	public void editData(JavaBeans contato) {
		String update = "UPDATE CONTATOS SET NOME=?, TELEFONE=?, EMAIL=? WHERE IDCON = ?";

		try {

			Connection con = conectar();

			PreparedStatement pst = con.prepareStatement(update);
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			pst.setString(4, contato.getIdcon());
			pst.executeUpdate();
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Delete data.
	 *
	 * @param contato the contato
	 */
	public void deleteData(JavaBeans contato) {

		String delete = "DELETE FROM CONTATOS WHERE IDCON =?";

		try {

			Connection con = conectar();

			PreparedStatement pst = con.prepareStatement(delete);

			pst.setString(1, contato.getIdcon());
			pst.executeUpdate();
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Select data.
	 *
	 * @param contato the contato
	 */
	public void selectData(JavaBeans contato) {

		String select = "SELECT * FROM CONTATOS WHERE;";

		try {

			Connection con = conectar();

			String selectIdcon = select + "idcon = ?";
			PreparedStatement pst = con.prepareStatement(selectIdcon);
			pst.setString(1, contato.getIdcon());
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				contato.setIdcon(rs.getString(1));
				contato.setNome(rs.getString(2));
				contato.setFone(rs.getString(3));
				contato.setEmail(rs.getString(4));
			}

			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
