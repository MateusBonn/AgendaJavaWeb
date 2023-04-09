package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DAO {

	/** Módulo de conexão **/
	// Parâmetros de conexão
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://127.0.0.1:3306/" + "dbagenda?useTimezone=true&serverTimezone=UTC";
	private String user = "root";
	private String password = "mateusophia0508";

	// Médodo de conexão

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

	/** CRUD INSERT **/

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

	/* CRUD READ */
	public ArrayList<JavaBeans> dataSelect() {

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

				contatos.add(new JavaBeans(idcon, name, phone, email, null));
			}
			con.close();

			return contatos;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}

	}

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

	public void selectVerificaData(JavaBeans contato) {

		String select = "SELECT * FROM CONTATOS WHERE;";

		try {

			Connection con = conectar();

			if (contato.getIdcon() != null) {

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
			} else {
				String sqlNome = "SELECT * FROM contatos WHERE nome = ?";
				String sqlTelefone = "SELECT * FROM contatos WHERE telefone = ?";
				String sqlEmail = "SELECT * FROM contatos WHERE email = ?";

				// 3. Crie os PreparedStatements com as consultas SQL
				PreparedStatement stmtNome = con.prepareStatement(sqlNome);
				PreparedStatement stmtTelefone = con.prepareStatement(sqlTelefone);
				PreparedStatement stmtEmail = con.prepareStatement(sqlEmail);

				// 4. Preencha os parâmetros das consultas com os valores a serem pesquisados
				stmtNome.setString(1, contato.getNome());
				stmtTelefone.setString(1, contato.getFone());
				stmtEmail.setString(1, contato.getEmail());

				// 5. Execute as consultas
				ResultSet rsNome = stmtNome.executeQuery();
				
				if (rsNome != null) {
					contato.setIdcon(rsNome.getString(1));
					contato.setNome(rsNome.getString(2));
					contato.setFone(rsNome.getString(3));
					contato.setEmail(rsNome.getString(4));
				}
				
				ResultSet rsTelefone = stmtTelefone.executeQuery();
				
				if (rsTelefone != null) {
					contato.setIdcon(rsTelefone.getString(1));
					contato.setNome(rsTelefone.getString(2));
					contato.setFone(rsTelefone.getString(3));
					contato.setEmail(rsTelefone.getString(4));
				}
				
				ResultSet rsEmail = stmtEmail.executeQuery();
				if (rsEmail != null) {
					contato.setIdcon(rsEmail.getString(1));
					contato.setNome(rsEmail.getString(2));
					contato.setFone(rsEmail.getString(3));
					contato.setEmail(rsEmail.getString(4));
				}
				
				String msg = null;
				
				if (rsNome.next() || rsTelefone.next() || rsEmail.next()) {
				  msg = "O contato já existe no banco de dados.";
				} else {
				    msg = null;
				}
				
				contato.setMsg(msg);
			}

			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
