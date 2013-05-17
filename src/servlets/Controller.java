package servlets;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mybeans.dao.DAOException;

/**
 * Servlet implementation class DisplayApps
 */
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	public void init() throws ServletException {
		Action.add(new BrowseApps());
		Action.add(new AppXternalPckgs());
		Action.add(new PermissionsList());
		Action.add(new Statistics());
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		String	servletPath = request.getServletPath();
		System.out.println("servletPath = " + servletPath);
		
		if (servletPath.equals("/appSelected.do")){
			try {
				Action.perform("appXternalPckgs", request, response);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (servletPath.equals("/appSelectedPerm.do")) {
			try {
				Action.perform("permissionsList", request, response);
			}catch (DAOException e){
				e.printStackTrace();
			}
		} else if (servletPath.equals("/showStatistics.do")) {
			try {
				Action.perform("statistics", request, response);
			}catch (DAOException e){
				e.printStackTrace();
			}			
		}
		else {
			try {
				Action.perform("browseApps", request, response);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
