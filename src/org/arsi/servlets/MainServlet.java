package org.arsi.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.arssi.bo.Client;
import org.arssi.db.DAOClients;

@WebServlet(urlPatterns = { "/home", "/deleteClient", "/rechercherClient", "/ajouterClient", "/modifyClient" })
public class MainServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private DAOClients daoClients;

	public MainServlet() {
		daoClients = new DAOClients();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getRequestURI().substring(request.getContextPath().length());
		String destination = "/index.jsp";
		if (path.equals("/deleteClient")) {
			// DOING DELETE
			String idRaw = request.getParameter("id");
			try {
				daoClients.deleteClient(Integer.parseInt(idRaw));
				request.setAttribute("clients", daoClients.getAllClients());

			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else if (path.equals("/modifyClient")) {
			// DOING MODIFY
			Integer clientID = Integer.parseInt(request.getParameter("id"));
			try {
				request.setAttribute("client", daoClients.getClientByID(clientID));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			destination = "/edit-client.jsp";

		} else if (path.equals("/rechercherClient")) {
			// DOING Search
			String nomClient = request.getParameter("nomClient");
			ArrayList<Client> clients = null;
			try {
				clients = daoClients.findClientByNom(nomClient);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			request.setAttribute("clients", clients);
		} else if (path.equals("/ajouterClient")) {
			// DOING ADD
			destination = "/add-client.jsp";
		} else {
			try {
				request.setAttribute("clients", daoClients.getAllClients());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher(destination);
		if (dispatcher != null) {
			dispatcher.forward(request, response);
		}

	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getRequestURI().substring(request.getContextPath().length());
		String destination = "/index.jsp";
		if (path.equals("/modifyClient")) {
			// DOING the update
			Client clientToUpdate=new Client();
			
		
			clientToUpdate.setId(Integer.parseInt(request.getParameter("id")));
			clientToUpdate.setAdresse(request.getParameter("adresse"));
			clientToUpdate.setEmail(request.getParameter("email"));
			clientToUpdate.setFax(request.getParameter("fax"));
			clientToUpdate.setNom(request.getParameter("nom"));
			clientToUpdate.setPays(request.getParameter("pays"));
			clientToUpdate.setSiteWeb(request.getParameter("siteWeb"));
			clientToUpdate.setTelephone(request.getParameter("telephone"));
			clientToUpdate.setVille(request.getParameter("ville"));
			
			try {
				daoClients.updateClient(clientToUpdate);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}else if (path.equals("/ajouterClient")) {

			Client clientToAdd=new Client();

			clientToAdd.setAdresse(request.getParameter("adresse"));
			clientToAdd.setEmail(request.getParameter("email"));
			clientToAdd.setFax(request.getParameter("fax"));
			clientToAdd.setNom(request.getParameter("nom"));
			clientToAdd.setPays(request.getParameter("pays"));
			clientToAdd.setSiteWeb(request.getParameter("siteWeb"));
			clientToAdd.setTelephone(request.getParameter("telephone"));
			clientToAdd.setVille(request.getParameter("ville"));
			
			try {
				daoClients.addClient(clientToAdd);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} 
		try {
			request.setAttribute("clients", daoClients.getAllClients());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(destination);
		if (dispatcher != null) {
			dispatcher.forward(request, response);
		}

	}

}
