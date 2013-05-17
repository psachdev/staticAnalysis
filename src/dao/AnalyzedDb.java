package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import helper.ConnectionLocalHost;
import helper.ConnectionMgrWestLakeIsr;

import org.mybeans.dao.DAOException;
import org.mybeans.factory.BeanFactory;
import org.mybeans.factory.BeanFactoryException;
import org.mybeans.factory.BeanTable;

import beans.AppInfoBean;
import beans.CategoryBean;
import beans.PermissionCountBean;
import beans.XternalPackageCountBean;


public class AnalyzedDb {
	private BeanFactory<AppInfoBean> factory;
	private static AnalyzedDb context = null;
	
	public static AnalyzedDb getInstance() throws DAOException {
		if(context == null) context = new AnalyzedDb();
		return context;
	}

	private AnalyzedDb() throws DAOException {
		ConnectionLocalHost.CreateConnectionMgr();
	}
	
	public String getPermissionDescription (String permission) throws DAOException {
		try {
			Connection con = ConnectionLocalHost.GetConnection();

			Statement stmt = con.createStatement();
			
			String query = null;
			query = "select permissiondescription from uniquepermissionlist where permissionstring=\'" + permission + "\'";
			
			//System.out.println("Query : " + query);
			ResultSet rs = stmt.executeQuery(query);
			/*Iterate, Store & Return */
			String description = new String ();
			if (rs.first()){
				description = (rs.getString(1));			
			}
			else {
				description = null;
			}
			rs.close();
			stmt.close();
			ConnectionLocalHost.ReleaseConnection(con);
			return description;
		}catch (SQLTimeoutException e){
			throw new DAOException (e);
		}catch (SQLException e) {
			throw new DAOException (e);
		}
	}
	
	public String getPermissionType (String permission) throws DAOException {
		try {
			Connection con = ConnectionLocalHost.GetConnection();

			Statement stmt = con.createStatement();
			
			String query = null;
			query = "select permissiongroup from uniquepermissionlist where permissionstring=\'" + permission + "\'";
			
			//System.out.println("Query : " + query);
			ResultSet rs = stmt.executeQuery(query);
			/*Iterate, Store & Return */
			String permtype = new String ();
			if (rs.first()){
				permtype = (rs.getString(1));			
			}
			else {
				permtype = null;
			}
			rs.close();
			stmt.close();
			ConnectionLocalHost.ReleaseConnection(con);
			return permtype;
		}catch (SQLTimeoutException e){
			throw new DAOException (e);
		}catch (SQLException e) {
			throw new DAOException (e);
		}
	}
	
	public String getExternalPackageWebSite (String packagename) throws DAOException {
		try {
			Connection con = ConnectionLocalHost.GetConnection();

			Statement stmt = con.createStatement();
			
			String query = null;
			query = "select website from labeled3rdparty where externalpack=\'" + packagename + "\'";
			
			//System.out.println("Query : " + query);
			ResultSet rs = stmt.executeQuery(query);
			/*Iterate, Store & Return */
			String category = new String ();
			if (rs.first()){
				category = (rs.getString(1));			
			}
			else {
				category = null;
			}
			rs.close();
			stmt.close();
			ConnectionLocalHost.ReleaseConnection(con);
			return category;
		}catch (SQLTimeoutException e){
			throw new DAOException (e);
		}catch (SQLException e) {
			throw new DAOException (e);
		}
	}
	
	public String getExternalPackageCategory (String packagename) throws DAOException {
		try {
			Connection con = ConnectionLocalHost.GetConnection();

			Statement stmt = con.createStatement();
			
			String query = null;
			query = "select type from labeled3rdparty where externalpack=\'" + packagename + "\'";
			
			//System.out.println("Query : " + query);
			ResultSet rs = stmt.executeQuery(query);
			/*Iterate, Store & Return */
			String category = new String ();
			if (rs.first()){
				category = (rs.getString(1));			
			}
			else {
				category = null;
			}
			rs.close();
			stmt.close();
			ConnectionLocalHost.ReleaseConnection(con);
			return category;
		}catch (SQLTimeoutException e){
			throw new DAOException (e);
		}catch (SQLException e) {
			throw new DAOException (e);
		}
	}
	
	public List<String> getOtherAppsUsingExternalPackage (String packagename) throws DAOException {
		try {
			Connection con = ConnectionLocalHost.GetConnection();

			Statement stmt = con.createStatement();
			
			String query = null;
			query = "select packagename from Test_3rd_party_packages where 3rd_party_package=\'" +packagename + "\' limit 0,3";
			
			//System.out.println("Query : " + query);
			ResultSet rs = stmt.executeQuery(query);
			/*Iterate, Store & Return */
			ArrayList<String> packageNames = new ArrayList<String>();
			while (rs.next()){
				String packageName = new String();
				packageName = (rs.getString(1));
				packageNames.add (packageName);				
			}
			if (packageNames.size() == 0) return null;
			rs.close();
			stmt.close();
			ConnectionLocalHost.ReleaseConnection(con);
			return packageNames;
		}catch (SQLTimeoutException e){
			throw new DAOException (e);
		}catch (SQLException e) {
			throw new DAOException (e);
		}
	}
	
	public Integer getCountOfOtherAppsUsingExternalPackage (String packagename) throws DAOException {
		try {
			Connection con = ConnectionLocalHost.GetConnection();

			Statement stmt = con.createStatement();
			
			String query = null;
			query = "select packagename from Test_3rd_party_packages where 3rd_party_package=\'" +packagename + "\'";
			
			//System.out.println("Query : " + query);
			ResultSet rs = stmt.executeQuery(query);
			/*Iterate, Store & Return */
			Integer size = 0;
			while (rs.next()){
				size ++;		
			}
			rs.close();
			stmt.close();
			ConnectionLocalHost.ReleaseConnection(con);
			return size;
		}catch (SQLTimeoutException e){
			throw new DAOException (e);
		}catch (SQLException e) {
			throw new DAOException (e);
		}
	}
	
	public boolean isExternal(String packagename, String permission) throws DAOException{
		try {
			Connection con = ConnectionLocalHost.GetConnection();

			Statement stmt = con.createStatement();
			
			String query = null;
			query = "select  distinct 3rd_party_package from Test_permissionlist where packagename=\'" +
					packagename + "\' and permission=\'" + permission +"\' and 3rd_party_package!=\"NA\" and is_external=1";
			
			//System.out.println("Query : " + query);
			ResultSet rs = stmt.executeQuery(query);
			boolean returnVal;
			/*Iterate, Store & Return */
			if (rs.first()){
				returnVal = true;			
			} else {
				returnVal = false;
			}
			rs.close();
			stmt.close();
			ConnectionLocalHost.ReleaseConnection(con);
			return returnVal;
		}catch (SQLTimeoutException e){
			throw new DAOException (e);
		}catch (SQLException e) {
			throw new DAOException (e);
		}
	}

	public ArrayList<String> getExternalPackagesForPermission (String packagename, String permission) throws DAOException {
		try {
			Connection con = ConnectionLocalHost.GetConnection();

			Statement stmt = con.createStatement();
			
			String query = null;
			query = "select  distinct 3rd_party_package from Test_permissionlist where packagename=\'" +
					packagename + "\' and permission=\'" + permission +"\' and 3rd_party_package!=\"NA\"";
			
			//System.out.println("Query : " + query);
			ResultSet rs = stmt.executeQuery(query);
			/*Iterate, Store & Return */
			ArrayList<String> externalPackageList = new ArrayList<String>();
			while (rs.next()){
				String externalPackage = new String();
				externalPackage = (rs.getString(1));
				externalPackageList.add (externalPackage);				
			}
			if (externalPackageList.size() == 0) return null;
			rs.close();
			stmt.close();
			ConnectionLocalHost.ReleaseConnection(con);
			return externalPackageList;
		}catch (SQLTimeoutException e){
			throw new DAOException (e);
		}catch (SQLException e) {
			throw new DAOException (e);
		}
	}
	
	public List<String> getPermissions (String packagename) throws DAOException {
		try {
			Connection con = ConnectionLocalHost.GetConnection();

			Statement stmt = con.createStatement();
			
			String query = null;
			query = "select distinct permission from Test_permissionlist where packagename=\'" +packagename + "\'";
			
			//System.out.println("Query : " + query);
			ResultSet rs = stmt.executeQuery(query);
			/*Iterate, Store & Return */
			ArrayList<String> permissionsList = new ArrayList<String>();
			while (rs.next()){
				String permission = new String();
				permission = (rs.getString(1));
				permissionsList.add (permission);				
			}
			if (permissionsList.size() == 0) return null;
			rs.close();
			stmt.close();
			ConnectionLocalHost.ReleaseConnection(con);
			return permissionsList;
		}catch (SQLTimeoutException e){
			throw new DAOException (e);
		}catch (SQLException e) {
			throw new DAOException (e);
		}
	}
	
	public List<String> getExternalPackages (String packagename) throws DAOException {
		try {
			Connection con = ConnectionLocalHost.GetConnection();

			Statement stmt = con.createStatement();
			
			String query = null;
			query = "select 3rd_Party_package from Test_3rd_party_packages where packagename=\'" +packagename + "\'";
			
			//System.out.println("Query : " + query);
			ResultSet rs = stmt.executeQuery(query);
			/*Iterate, Store & Return */
			ArrayList<String> externalPackages = new ArrayList<String>();
			while (rs.next()){
				String externalPackage = new String();
				externalPackage = (rs.getString(1));
				externalPackages.add (externalPackage);				
			}
			if (externalPackages.size() == 0) return null;
			rs.close();
			stmt.close();
			ConnectionLocalHost.ReleaseConnection(con);
			return externalPackages;
		}catch (SQLTimeoutException e){
			throw new DAOException (e);
		}catch (SQLException e) {
			throw new DAOException (e);
		}
	}
	
	public ArrayList<String> getNextListOfApps(int begin, int end, String category) throws DAOException {
		try {
			Connection con = ConnectionLocalHost.GetConnection();
			Statement stmt = con.createStatement();
			String beginStr = String.valueOf(begin);
			String endStr   = String.valueOf(end);
			String query = null;
			if (category.equals("all")){
				query = "select distinct packagename from Test_3rd_party_packages limit "+ beginStr + "," + endStr;
			}else {
				query = "select distinct packagename from Test_3rd_party_packages where category=\'"+ category + "\' limit "+ begin + "," + end;
			}
			//System.out.println("Query : " + query);
			ResultSet rs = stmt.executeQuery(query);
			/*Iterate, Store & Return */
			ArrayList<String> resultList = new ArrayList<String>();
			while (rs.next()){
				String bean = new String();
				bean = rs.getString(1);
				resultList.add(bean);				
			}
			if (resultList.size() == 0) return null;
			rs.close();
			stmt.close();
			ConnectionLocalHost.ReleaseConnection(con);
			return resultList;
		}catch (SQLTimeoutException e){
			throw new DAOException (e);
		}catch (SQLException e) {
			throw new DAOException (e);
		}
	}
	
	public ArrayList<PermissionCountBean> getAllPermissionsCount() throws DAOException {
		try{
			Connection con = ConnectionLocalHost.GetConnection();

			Statement stmt = con.createStatement();
			String query = "select permission, COUNT(*) as count from Test_permissionlist group by permission order by count desc limit 0,20";

			ResultSet rs = stmt.executeQuery(query);
			/*Iterate, Store & Return */
			ArrayList<PermissionCountBean> resultList = new ArrayList<PermissionCountBean>();
			while (rs.next()){
				PermissionCountBean bean = new PermissionCountBean();
				bean.setPermission(rs.getString(1));
				bean.setCount(rs.getString(2));
				resultList.add(bean);				
			}
			if (resultList.size() == 0) return null;
			rs.close();
			stmt.close();
			ConnectionLocalHost.ReleaseConnection(con);
			return resultList;
		}catch (SQLTimeoutException e){
			throw new DAOException (e);
		}catch (SQLException e) {
			throw new DAOException (e);
		}
	}
	
	public Map<String, Integer> populateTopPermissionsForApp (String arg, Map<String, Integer> unsortMap) throws DAOException {
		try{
			Connection con = ConnectionLocalHost.GetConnection();

			Statement stmt = con.createStatement();
			
			String query = "select permission, COUNT(*) as count from Test_permissionlist where " + arg + " group by permission order by count desc limit 0,20";
			System.out.println (query);
			ResultSet rs = stmt.executeQuery(query);
			/*Iterate, Store & Return */
			
			while (rs.next()){
				PermissionCountBean bean = new PermissionCountBean();
				unsortMap.put(rs.getString(1), Integer.parseInt(rs.getString(2)));
			}
			rs.close();
			stmt.close();
			ConnectionLocalHost.ReleaseConnection(con);
			return unsortMap;
		}catch (SQLTimeoutException e){
			throw new DAOException (e);
		}catch (SQLException e) {
			throw new DAOException (e);
		}
	}
	
	public ArrayList<XternalPackageCountBean> getAllXternalPackagesCount() throws DAOException {
		try{
			Connection con = ConnectionLocalHost.GetConnection();

			Statement stmt = con.createStatement();
			String query = "select 3rd_party_package, COUNT(*) as count from Test_3rd_party_packages group by 3rd_party_package order by count desc limit 0,20";

			ResultSet rs = stmt.executeQuery(query);
			/*Iterate, Store & Return */
			ArrayList<XternalPackageCountBean> resultList = new ArrayList<XternalPackageCountBean>();
			while (rs.next()){
				XternalPackageCountBean bean = new XternalPackageCountBean();
				bean.setExternalpackage(rs.getString(1));
				bean.setCount(rs.getString(2));
				
				resultList.add(bean);				
			}
			if (resultList.size() == 0) return null;
			rs.close();
			stmt.close();
			ConnectionLocalHost.ReleaseConnection(con);
			return resultList;
		}catch (SQLTimeoutException e){
			throw new DAOException (e);
		}catch (SQLException e) {
			throw new DAOException (e);
		}
	}
}
