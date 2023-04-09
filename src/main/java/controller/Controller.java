package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;
import model.JavaBeans;

@WebServlet(urlPatterns = { "/Controller", "/main", "/insert", "/select", "/update", "/delete", "/report" })
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DAO dao = new DAO();
	JavaBeans contato = new JavaBeans();

	public Controller() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();

		if (action.equals("/main")) {
			contatos(request, response);
		} else if (action.equals("/insert")) {
			newContact(request, response);
		} else if (action.equals("/select") || action.equals("/update")) {
			editVerificaContact(request, response);
		} else if (action.equals("/delete")) {
			deleteContact(request, response);
		} else if (action.equals("/report")) {
			makeReport(request, response);
		}
	}

	private void newContact(HttpServletRequest request, HttpServletResponse response) {

		contato.setNome(request.getParameter("name"));
		contato.setFone(request.getParameter("phone"));
		contato.setEmail(request.getParameter("email"));

		/** DATA INSERT IN DAO **/
		dao.dataInsert(contato);

		/* SEND CLIENT TO MAIN */
		try {
			response.sendRedirect("main");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Listar contatos
	protected void contatos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<JavaBeans> list = dao.dataSelect();
		request.setAttribute("contatos", list);
		RequestDispatcher rd = request.getRequestDispatcher("Agenda.jsp");
		rd.forward(request, response);
	}

	private void editVerificaContact(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getServletPath();

		if (action.equals("/select")) {
			String idcon = request.getParameter("idcon");

			if (idcon.equals(null)) {
				// Setar a variável JavaBeans
				contato.setNome(request.getParameter("name"));
				contato.setFone(request.getParameter("phone"));
				contato.setEmail(request.getParameter("email"));

				// Executar o método dataEdit(DAO)
				dao.selectVerificaData(contato);

				// Setar os atributos do formulário com o conteúdo JavaBeans
				request.setAttribute("idcon", contato.getIdcon());
				request.setAttribute("msg", contato.getMsg());

				// Encaminhado ao EditContact.jsp
				RequestDispatcher rd = request.getRequestDispatcher("NewContact.jsp");
				rd.forward(request, response);
			} else {
				// Setar a variável JavaBeans
				contato.setIdcon(idcon);

				// Executar o método dataEdit(DAO)
				dao.selectVerificaData(contato);

				// Setar os atributos do formulário com o conteúdo JavaBeans
				request.setAttribute("idcon", contato.getIdcon());
				request.setAttribute("name", contato.getNome());
				request.setAttribute("phone", contato.getFone());
				request.setAttribute("email", contato.getEmail());

				// Encaminhado ao EditContact.jsp
				RequestDispatcher rd = request.getRequestDispatcher("EditContact.jsp");
				rd.forward(request, response);
			}

		}
		if (action.equals("/update")) {
			contato.setIdcon(request.getParameter("idcon"));
			contato.setNome(request.getParameter("name"));
			contato.setFone(request.getParameter("phone"));
			contato.setEmail(request.getParameter("email"));

			dao.editData(contato);

			response.sendRedirect("main");
		}
	}

	private void deleteContact(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String idcon = request.getParameter("idcon");

		contato.setIdcon(idcon);

		dao.deleteData(contato);

		response.sendRedirect("main");
	}
	
	private void makeReport (HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Document documento = new Document();
		
		try {
			
			//tipo de documento 
			response.setContentType("apllication/pdf");
			
			//nome do documento
			response.addHeader("Content-Disposition", "inline; filename=" + "Contatos.pdf");
			
			//criando o documento
			PdfWriter.getInstance(documento, response.getOutputStream());
			
			//abrir o conteúdo
			documento.open();
			documento.add(new Paragraph("Lista de contatos: "));
			documento.add(new Paragraph(" "));
			
			// criar uma tabela
			PdfPTable tabelaContatos = new PdfPTable(3);
			
			//Cabeçalho
			PdfPCell name = new PdfPCell(new Paragraph("Nome"));
			tabelaContatos.addCell(name);
			
			PdfPCell phone = new PdfPCell(new Paragraph("Telefone"));
			tabelaContatos.addCell(phone);
			
			PdfPCell email = new PdfPCell(new Paragraph("E-mail"));
			tabelaContatos.addCell(email);
			
			ArrayList<JavaBeans> list = dao.dataSelect();
			for (int i= 0; i <list.size(); i++) {
				tabelaContatos.addCell(list.get(i).getNome());
				tabelaContatos.addCell(list.get(i).getFone());
				tabelaContatos.addCell(list.get(i).getEmail());
				
			}
			
			documento.add(tabelaContatos);
			
			documento.close();
		} catch (Exception e) {
			System.out.println(e);
			documento.close();
		}
	}
}
