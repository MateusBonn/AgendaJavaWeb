package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DAO;
import model.JavaBeans;

@WebServlet(urlPatterns = {"/Controller", "/main", "/insert"})
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DAO dao = new DAO();
	JavaBeans contato = new JavaBeans();
	
    public Controller() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		
		if(action.equals("/main")) {
			contatos(request, response);
		}
		else if(action.equals("/insert")) {
			newContact(request, response);
		}
	}
	
	private void newContact(HttpServletRequest request, HttpServletResponse response) {
		
		contato.setNome(request.getParameter("name"));
		contato.setFone(request.getParameter("phone"));
		contato.setEmail(request.getParameter("email"));
		
		/**DATA INSERT IN DAO **/
		dao.dataInsert(contato);
		
		/*SEND CLIENT TO MAIN*/
		try {
			response.sendRedirect("main");
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
	}

	//Listar contatos
	protected void contatos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		ArrayList<JavaBeans> list = dao.dataSelect();
		request.setAttribute("contatos", list);
		RequestDispatcher rd = request.getRequestDispatcher("Agenda.jsp");
		rd.forward(request, response);		
	}

}
