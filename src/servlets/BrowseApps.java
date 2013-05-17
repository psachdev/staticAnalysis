package servlets;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.dao.DAOException;

import beans.AppInfoBean;
import beans.CategoryBean;

import dao.AnalyzedDb;
import dao.AppinfoTableReader;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



public class BrowseApps extends Action {
	AppinfoTableReader appinfoTableReaderDao;
	AnalyzedDb analyzedDb;

	public String getName() { return "browseApps"; }
	public BrowseApps() {
		try{
			appinfoTableReaderDao = AppinfoTableReader.getInstance();
			analyzedDb = AnalyzedDb.getInstance();
		}catch(DAOException e){
			appinfoTableReaderDao = null;
			analyzedDb = null;
		}

	}


	public void perform(HttpServletRequest request, HttpServletResponse response) throws DAOException {
		HttpSession session  = request.getSession(true);
		Integer oldnumAlreadyDisplayed = 0;
		Integer newnumAlreadyDisplayed = 0;
		Integer NumOfAllowedApps = 20;
		String categoryDisplayed = null;

		String	servletPath = request.getServletPath();

		List<AppInfoBean> resultList = new ArrayList<AppInfoBean> ();
		List<CategoryBean> categories = null;
		categoryDisplayed = (String) request.getParameter("category");
		String current = (String) request.getParameter("Current");
		String next = (String) request.getParameter("Next");
		String prev = (String) request.getParameter("Prev");
		if (categoryDisplayed == null) categoryDisplayed = "all";

		System.out.println ("Current " + current + " next " + next + " prev " + prev );
		try{
			if (categoryDisplayed.equals(session.getAttribute("category"))) {
				try{
					oldnumAlreadyDisplayed = (Integer) session.getAttribute("numAlreadyDisplayed");				
				}catch(NumberFormatException e){
					oldnumAlreadyDisplayed = 0;
				}
			}else{
				oldnumAlreadyDisplayed = 0;
				session.setAttribute("category", categoryDisplayed);
			}}catch(NullPointerException e){
				oldnumAlreadyDisplayed = 0;
				session.setAttribute("category", categoryDisplayed);
			}
		if ((current != null) && (current.equals("1"))){
			oldnumAlreadyDisplayed = 0;
			newnumAlreadyDisplayed =  oldnumAlreadyDisplayed;
		}
		else
			if ((prev != null) && (prev.equals("1"))) {
				oldnumAlreadyDisplayed -= NumOfAllowedApps;
				newnumAlreadyDisplayed =  oldnumAlreadyDisplayed;
			}
			else if ((next != null) && (next.equals("1"))){
				newnumAlreadyDisplayed =  oldnumAlreadyDisplayed + NumOfAllowedApps;
			}
			else {
				oldnumAlreadyDisplayed = 0;
				newnumAlreadyDisplayed =  oldnumAlreadyDisplayed + NumOfAllowedApps;
			}
		
		session.setAttribute("numAlreadyDisplayed", newnumAlreadyDisplayed);

		System.out.println("Category : " + categoryDisplayed + " Range " + oldnumAlreadyDisplayed + " : " + newnumAlreadyDisplayed);
		try{
			ArrayList<AppInfoBean> nameList = appinfoTableReaderDao.getNextListOfApps(oldnumAlreadyDisplayed, NumOfAllowedApps, categoryDisplayed);
			if (nameList != null)
				resultList.addAll(nameList);
			/*
			if (nameList != null) {
				for (String name : nameList) {
					AppInfoBean x = appinfoTableReaderDao.getAppInfo (name);
					resultList.add(x);
				}
			}
			*/
			categories = appinfoTableReaderDao.getCategories();
						
			//System.out.println("ResultList : " + resultList.size() + "  Categories : " + categories.size());						
		}catch (DAOException e){
			System.out.println(e);
		}
		if (resultList.size() == 0)
			request.setAttribute("result", null);
		else
			request.setAttribute("result",resultList);
		request.setAttribute("categories", categories);
		request.setAttribute("selectedCategory", categoryDisplayed);

		RequestDispatcher d = request.getRequestDispatcher("/"+ "page_1.jsp");
		try{
			d.forward(request,response);
		}catch(Exception e){
			System.out.println(e);
		}
	}
}
