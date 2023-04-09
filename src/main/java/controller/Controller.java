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

// TODO: Auto-generated Javadoc
/**
 * The Class Controller.
 */
@WebServlet(urlPatterns = { "/Controller", "/main", "/insert", "/select", "/update", "/delete", "/report" })
public class Controller extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The dao. */
	DAO dao = new DAO();
	
	/** The contato. */
	JavaBeans contato = new JavaBeans();

	/**
	 * Instantiates a new controller.
	 */
	public Controller() {
		super();
	}

	/**
	 * Do get.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();

		if (action.equals("/main")) {
			contacts(request, response);
		} else if (action.equals("/insert")) {
			newContact(request, response);
		} else if (action.equals("/select") || action.equals("/update")) {
			viewContact(request, response);
		} else if (action.equals("/update")) {
			editContact(request, response);
		} else if (action.equals("/delete")) {
			deleteContact(request, response);
		} else if (action.equals("/report")) {
			makeReport(request, response);
		}
	}

	/**
	 * New contact.
	 *
	 * @param request the request
	 * @param response the response
	 */
	private void newContact(HttpServletRequest request, HttpServletResponse response) {

		contato.setNome(request.getParameter("name"));
		contato.setFone(request.getParameter("phone"));
		contato.setEmail(request.getParameter("email"));

		dao.dataInsert(contato);

		try {
			response.sendRedirect("main");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Contacts.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void contacts(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<JavaBeans> list = dao.selectAllData();
		request.setAttribute("contatos", list);
		RequestDispatcher rd = request.getRequestDispatcher("Agenda.jsp");
		rd.forward(request, response);
	}

	/**
	 * View contact.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void viewContact(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String idcon = request.getParameter("idcon");

		contato.setIdcon(idcon);

		dao.selectData(contato);

		request.setAttribute("idcon", contato.getIdcon());
		request.setAttribute("name", contato.getNome());
		request.setAttribute("phone", contato.getFone());
		request.setAttribute("email", contato.getEmail());

		RequestDispatcher rd = request.getRequestDispatcher("EditContact.jsp");
		rd.forward(request, response);

	}

	/**
	 * Edits the contact.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void editContact(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		contato.setIdcon(request.getParameter("idcon"));
		contato.setNome(request.getParameter("name"));
		contato.setFone(request.getParameter("phone"));
		contato.setEmail(request.getParameter("email"));

		dao.editData(contato);

		response.sendRedirect("main");
	}

	/**
	 * Delete contact.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void deleteContact(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		contato.setIdcon(request.getParameter("idcon"));

		dao.deleteData(contato);

		response.sendRedirect("main");
	}

	/**
	 * Make report.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void makeReport(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Document documento = new Document();

		try {

			response.setContentType("apllication/pdf");

			response.addHeader("Content-Disposition", "inline; filename=" + "Contatos.pdf");

			PdfWriter.getInstance(documento, response.getOutputStream());

			documento.open();
			documento.add(new Paragraph("Lista de contatos: "));
			documento.add(new Paragraph(" "));

			PdfPTable tabelaContatos = new PdfPTable(3);

			PdfPCell name = new PdfPCell(new Paragraph("Nome"));
			tabelaContatos.addCell(name);

			PdfPCell phone = new PdfPCell(new Paragraph("Telefone"));
			tabelaContatos.addCell(phone);

			PdfPCell email = new PdfPCell(new Paragraph("E-mail"));
			tabelaContatos.addCell(email);

			ArrayList<JavaBeans> list = dao.selectAllData();
			for (int i = 0; i < list.size(); i++) {
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

