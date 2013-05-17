package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import helper.ConnectionMgrWestLakeIsr;

import org.mybeans.dao.DAOException;
import org.mybeans.factory.BeanFactory;
import org.mybeans.factory.BeanFactoryException;
import org.mybeans.factory.BeanTable;

import beans.AppInfoBean;
import beans.CategoryAppCountBean;
import beans.CategoryBean;
import beans.PermissionCountBean;


public class AppinfoTableReader {
	private BeanFactory<AppInfoBean> factory;
	private static AppinfoTableReader context = null;
	
	public static AppinfoTableReader getInstance() throws DAOException {
		if(context == null) context = new AppinfoTableReader();
		return context;
	}

	private AppinfoTableReader() throws DAOException {
		ConnectionMgrWestLakeIsr.CreateConnectionMgr();
	}

	public ArrayList<AppInfoBean> getNextListOfApps(int begin, int end, String category) throws DAOException {
		try {
			Connection con = ConnectionMgrWestLakeIsr.GetConnection();

			Statement stmt = con.createStatement();
			String beginStr = String.valueOf(begin);
			String endStr   = String.valueOf(end);
			String query = null;
			if (category.equals("all")){
				query = "select id,appname,packagename,rating_value,installs_max,installs_min,category from appinfo order by installs_max desc limit "+ beginStr + "," + endStr;
			}else {
				query = "select id,appname,packagename,rating_value,installs_max,installs_min,category from appinfo where category=\'"+ category + "\'order by installs_max desc limit "+ begin + "," + end;
			}
			//System.out.println("Query : " + query);
			ResultSet rs = stmt.executeQuery(query);
			/*Iterate, Store & Return */
			ArrayList<AppInfoBean> resultList = new ArrayList<AppInfoBean>();
			while (rs.next()){
				AppInfoBean bean = new AppInfoBean();
				bean.setId(rs.getLong(1));
				String image_link = "http://locationnames.org/appprivacy/Icons/" + rs.getString(3) + "_icon.png"; 
				//System.out.println("image link - " + image_link);
				bean.setAppname(rs.getString(2));
				bean.setImageSrc(image_link);
				bean.setPackagename(rs.getString(3));
				bean.setRating_value(rs.getDouble(4));
				bean.setInstalls_max(rs.getInt(5));
				bean.setInstalls_min(rs.getInt(6));
				bean.setCategory(rs.getString(7));
				resultList.add(bean);				
			}
			if (resultList.size() == 0) return null;
			rs.close();
			stmt.close();
			ConnectionMgrWestLakeIsr.ReleaseConnection(con);
			return resultList;
		}catch (SQLTimeoutException e){
			throw new DAOException (e);
		}catch (SQLException e) {
			throw new DAOException (e);
		}
	}

	public AppInfoBean getAppInfo (String packagename) throws DAOException {
		try {
			Connection con = ConnectionMgrWestLakeIsr.GetConnection();

			Statement stmt = con.createStatement();
			String query = "select id,appname,packagename,rating_value,installs_max,installs_min,category from appinfo where packagename=\'" + packagename + "\'";
			
			//System.out.println("Query : " + query);
			ResultSet rs = stmt.executeQuery(query);
			/*Iterate, Store & Return */
			AppInfoBean bean = null;
			if (rs.first()){
				bean = new AppInfoBean();
				bean.setId(rs.getLong(1));
				String image_link = "http://locationnames.org/appprivacy/Icons/" + rs.getString(3) + "_icon.png"; 
				//System.out.println("image link - " + image_link);
				bean.setAppname(rs.getString(2));
				bean.setImageSrc(image_link);
				bean.setPackagename(rs.getString(3));
				bean.setRating_value(rs.getDouble(4));
				bean.setInstalls_max(rs.getInt(5));
				bean.setInstalls_min(rs.getInt(6));
				bean.setCategory(rs.getString(7));				
			}
			rs.close();
			stmt.close();
			ConnectionMgrWestLakeIsr.ReleaseConnection(con);
			return bean;
		}catch (SQLTimeoutException e){
			throw new DAOException (e);
		}catch (SQLException e) {
			throw new DAOException (e);
		}
	}

	public List<CategoryBean> getCategories() throws DAOException {
		try{
			Connection con = ConnectionMgrWestLakeIsr.GetConnection();

			Statement stmt = con.createStatement();
			String query = "select distinct category from appinfo";

			ResultSet rs = stmt.executeQuery(query);
			/*Iterate, Store & Return */
			ArrayList<CategoryBean> resultList = new ArrayList<CategoryBean>();
			while (rs.next()){
				CategoryBean bean = new CategoryBean();
				bean.setCategory(rs.getString(1));
				resultList.add(bean);				
			}
			if (resultList.size() == 0) return null;
			rs.close();
			stmt.close();
			ConnectionMgrWestLakeIsr.ReleaseConnection(con);
			return resultList;
		}catch (SQLTimeoutException e){
			throw new DAOException (e);
		}catch (SQLException e) {
			throw new DAOException (e);
		}
	}
	
	public ArrayList<CategoryAppCountBean> getCategoryAppCount() throws DAOException {
		try{
			Connection con = ConnectionMgrWestLakeIsr.GetConnection();

			Statement stmt = con.createStatement();
			String query = "select category, COUNT(*) from appinfo group by category";

			ResultSet rs = stmt.executeQuery(query);
			/*Iterate, Store & Return */
			ArrayList<CategoryAppCountBean> resultList = new ArrayList<CategoryAppCountBean>();
			while (rs.next()){
				CategoryAppCountBean bean = new CategoryAppCountBean();
				bean.setCategory(rs.getString(1));
				bean.setCount(rs.getString(2));
				resultList.add(bean);				
			}
			if (resultList.size() == 0) return null;
			rs.close();
			stmt.close();
			ConnectionMgrWestLakeIsr.ReleaseConnection(con);
			return resultList;
		}catch (SQLTimeoutException e){
			throw new DAOException (e);
		}catch (SQLException e) {
			throw new DAOException (e);
		}
	}
	
	public Integer getTotalAppCount() throws DAOException {
		try{
			Connection con = ConnectionMgrWestLakeIsr.GetConnection();

			Statement stmt = con.createStatement();
			String query = "select COUNT(*) from appinfo";

			ResultSet rs = stmt.executeQuery(query);
			/*Iterate, Store & Return */
			Integer x = 0;
			if (rs.first()){
				x = rs.getInt(1);			
			}
			
			rs.close();
			stmt.close();
			ConnectionMgrWestLakeIsr.ReleaseConnection(con);
			return x;
		}catch (SQLTimeoutException e){
			throw new DAOException (e);
		}catch (SQLException e) {
			throw new DAOException (e);
		}
	}
	
	public String getAppName (String packagename) throws DAOException {
		try{
			Connection con = ConnectionMgrWestLakeIsr.GetConnection();

			Statement stmt = con.createStatement();
			String query = "select appname from appinfo where packagename=\'" + packagename +"\'";

			ResultSet rs = stmt.executeQuery(query);
			/*Iterate, Store & Return */
			String appName = new String ();
			if (rs.first()){
				appName = rs.getString(1);	
			}
			else {
				appName = null;
			}
			rs.close();
			stmt.close();
			ConnectionMgrWestLakeIsr.ReleaseConnection(con);
			return appName;
		}catch (SQLTimeoutException e){
			throw new DAOException (e);
		}catch (SQLException e) {
			throw new DAOException (e);
		}
	}

	public ArrayList<String> getAppsInCategory(String category) throws DAOException {
		try{
			Connection con = ConnectionMgrWestLakeIsr.GetConnection();

			Statement stmt = con.createStatement();
			String query = "select packagename from appinfo where category=\'" + category + "\'";

			ResultSet rs = stmt.executeQuery(query);
			/*Iterate, Store & Return */
			ArrayList<String> resultList = new ArrayList<String>();
			while (rs.next()){
				String bean = rs.getString(1);
				resultList.add(bean);				
			}
			if (resultList.size() == 0) return null;
			rs.close();
			stmt.close();
			ConnectionMgrWestLakeIsr.ReleaseConnection(con);
			return resultList;
		}catch (SQLTimeoutException e){
			throw new DAOException (e);
		}catch (SQLException e) {
			throw new DAOException (e);
		}
	}
}
