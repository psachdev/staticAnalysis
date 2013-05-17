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
import beans.PermissionBean;

import dao.AppinfoTableReader;
import dao.AnalyzedDb;

public class PermissionsList extends Action{
	private AppinfoTableReader appinfoTableReaderDao;
	private AnalyzedDb analyzedDb;
	private CustomComparator comparator;
	
	private class CustomComparator implements Comparator<PermissionBean> {
		
		public int compare(PermissionBean o1, PermissionBean o2) {
			return o1.compareTo(o2);
		}
	}
	
	public String getName() { return "permissionsList"; }
	public PermissionsList() {
		try{
			appinfoTableReaderDao = AppinfoTableReader.getInstance();
			analyzedDb = AnalyzedDb.getInstance();
		}catch(DAOException e){
			appinfoTableReaderDao = null;
			analyzedDb = null;
		}
	}
	
	public void perform(HttpServletRequest request, HttpServletResponse response) throws DAOException {
		HttpSession session  = request.getSession(false);
		String category = (String)session.getAttribute("category");
		
		String	packagename = (String)session.getAttribute("packagename");
		String	appname = (String)session.getAttribute("appname");
		
		if (packagename == null || appname == null) {
			request.setAttribute("errMsg", "Invalid request...");
			RequestDispatcher d = request.getRequestDispatcher("/"+ "page_3.jsp");
	     	try{
	     		d.forward(request,response);
	     	}catch(Exception e){
	     		System.out.println(e);
	     	}
	     	return;			
		}
		
		//System.out.println("category - " + category + " appname - " + appname + " packagename = " + packagename);
		List<String> permissionsList = analyzedDb.getPermissions (packagename);
		/* App not found in our database */
		if (permissionsList == null) {
			request.setAttribute("errMsg", "No permissions found...");
			RequestDispatcher d = request.getRequestDispatcher("/"+ "page_3.jsp");
	     	try{
	     		d.forward(request,response);
	     	}catch(Exception e){
	     		System.out.println(e);
	     	}
	     	return;
		}
		
		ArrayList<PermissionBean> permissionBeanList = new ArrayList<PermissionBean>();
		
		for (String permission : permissionsList){
			ArrayList<String> otherPackageNames = analyzedDb.getExternalPackagesForPermission (packagename, permission);
			String description = analyzedDb.getPermissionDescription (permission);
			String group = analyzedDb.getPermissionType (permission);
			boolean isExternal = analyzedDb.isExternal(packagename, permission);
			
			PermissionBean x = new PermissionBean ();
			x.setPermission(permission);
			x.setDescription(description);
			x.setExternalPackages(otherPackageNames);
			x.setIs_external(isExternal);
			x.setGroup (group);
			permissionBeanList.add(x);
		}
		//Collections.sort(permissionBeanList, comparator);
		
		for (PermissionBean x : permissionBeanList){
			System.out.println (x.getPermission() + " -- " + x.getDescription());
		}
		request.setAttribute("selectedApp", appname);
		request.setAttribute("selectedCategory", category);
		request.setAttribute("permissionsList", permissionBeanList);
		
     	RequestDispatcher d = request.getRequestDispatcher("/"+ "page_3.jsp");
     	try{
     		d.forward(request,response);
     	}catch(Exception e){
     		System.out.println(e);
     	}		
	}
}
