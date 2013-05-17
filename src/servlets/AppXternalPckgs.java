package servlets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mybeans.dao.DAOException;

import beans.ExternalPackageBean;

import dao.AppinfoTableReader;
import dao.AnalyzedDb;

public class AppXternalPckgs extends Action{
	private AppinfoTableReader appinfoTableReaderDao;
	private AnalyzedDb analyzedDb;
	private CustomComparator comparator;
	
	public String getName() { return "appXternalPckgs"; }
	public AppXternalPckgs() {
		try{
			appinfoTableReaderDao = AppinfoTableReader.getInstance();
			analyzedDb = AnalyzedDb.getInstance();
			comparator = new CustomComparator ();
		}catch(DAOException e){
			appinfoTableReaderDao = null;
			analyzedDb = null;
		}
	}	
	
	private class CustomComparator implements Comparator<ExternalPackageBean> {
		
		public int compare(ExternalPackageBean o1, ExternalPackageBean o2) {
			return o1.compareTo(o2);
		}
	}
	
	public void perform(HttpServletRequest request, HttpServletResponse response) throws DAOException {
		HttpSession session  = request.getSession(false);
		String category = (String)session.getAttribute("category");
		
		String packagename = (String)request.getParameter("packagename");
		String appname = (String)request.getParameter("appname");
		
		if (packagename != null && appname != null) {
			session.setAttribute("packagename", packagename);
			session.setAttribute("appname", appname);			
		} else {
			packagename = (String)session.getAttribute("packagename");
			appname = (String)session.getAttribute("appname");
		}
		
		if (packagename == null || appname == null) {
			request.setAttribute("errMsg", "Invalid request...");
			RequestDispatcher d = request.getRequestDispatcher("/"+ "page_2.jsp");
	     	try{
	     		d.forward(request,response);
	     	}catch(Exception e){
	     		System.out.println(e);
	     	}
	     	return;			
		}
		
		request.setAttribute("selectedApp", appname);
		request.setAttribute("selectedCategory", category);
		
		System.out.println("category - " + category + " appname - " + appname + " packagename = " + packagename);
		List<String> externalPackages = analyzedDb.getExternalPackages (packagename);
		/* App not found in our database */
		if (externalPackages == null) {
			request.setAttribute("errMsg", "No external packages found...");
			RequestDispatcher d = request.getRequestDispatcher("/"+ "page_2.jsp");
	     	try{
	     		d.forward(request,response);
	     	}catch(Exception e){
	     		System.out.println(e);
	     	}
	     	return;
		}
		
		ArrayList<ExternalPackageBean> externalPackageList = new ArrayList<ExternalPackageBean>();
		
		for (String externalPackage : externalPackages){
			//System.out.println ("External Package Name - " + externalPackage);
			List<String> otherPackageNames = analyzedDb.getOtherAppsUsingExternalPackage (externalPackage);
			ArrayList<String>appNames = new ArrayList<String>();
			if (otherPackageNames == null) continue;
			for (String otherPackageName : otherPackageNames) {
				//System.out.println ("OtherPackage - " + otherPackageName);
				String appName = appinfoTableReaderDao.getAppName (otherPackageName);
				appNames.add(appName);
			}
			String externalPackageCategory = analyzedDb.getExternalPackageCategory (externalPackage);
			String externalPackageWebsite = analyzedDb.getExternalPackageWebSite (externalPackage);
			//System.out.println ("ExternalPackageCategory " + externalPackageCategory);
			ExternalPackageBean x = new ExternalPackageBean ();
			x.setExternalPackageName (externalPackage);
			x.setOtherPackageNames (appNames);
			x.setWebsite (externalPackageWebsite);
			x.setNumOtherPackages (analyzedDb.getCountOfOtherAppsUsingExternalPackage (externalPackage));
			x.setExternalPackageCategory(externalPackageCategory);
			externalPackageList.add(x);
		}
		
		Collections.sort(externalPackageList, comparator);
		
		/*
		for (ExternalPackageBean x : externalPackageList){
			System.out.println ("External Package Name - " + x.getExternalPackageName() + " count " + x.count);
		}
		*/
		
		request.setAttribute("externalPackages", externalPackageList);
		
     	RequestDispatcher d = request.getRequestDispatcher("/"+ "page_2.jsp");
     	try{
     		d.forward(request,response);
     	}catch(Exception e){
     		System.out.println(e);
     	}		
	}
}
