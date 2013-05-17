package servlets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mybeans.dao.DAOException;

import beans.CategoryAppCountBean;
import beans.CategoryBean;
import beans.ExternalPackageBean;
import beans.PermissionBean;
import beans.PermissionCountBean;
import beans.XternalPackageCountBean;

import dao.AppinfoTableReader;
import dao.AnalyzedDb;

public class Statistics extends Action{
	private AppinfoTableReader appinfoTableReaderDao;
	private AnalyzedDb analyzedDb;

	public String getName() { return "statistics"; }
	public Statistics() {
		try{
			appinfoTableReaderDao = AppinfoTableReader.getInstance();
			analyzedDb = AnalyzedDb.getInstance();
		}catch(DAOException e){
			appinfoTableReaderDao = null;
			analyzedDb = null;
		}
	}

	public void perform(HttpServletRequest request, HttpServletResponse response) throws DAOException {
		List<CategoryBean> categories = null;
		String requestCategory = (String)request.getParameter("category");
		String selectedAppCategory = (String) request.getParameter("selectedAppCategory");


		System.out.println ("Request Category " + requestCategory + " selected App category = " + selectedAppCategory);

		if (requestCategory == null) {
			RequestDispatcher d = request.getRequestDispatcher("/"+ "page_4.jsp");
			try{
				request.setAttribute("category", "0");
				request.setAttribute("resultList", null);
				d.forward(request,response);
			}catch(Exception e){
				System.out.println(e);
			}				
		} else if ((requestCategory.equals("3") || requestCategory.equals("5")) && (selectedAppCategory == null)){
			List<CategoryBean> appCategories = appinfoTableReaderDao.getCategories();
			request.setAttribute("appCategoryList", appCategories);
			request.setAttribute("category", requestCategory);
		} else if (requestCategory.equals("1")){
			ArrayList<CategoryAppCountBean> resultList = null;
			resultList = getPercentageAppsPerCategory ();
			request.setAttribute("category", requestCategory);
			request.setAttribute("resultList", resultList);
		} else if (requestCategory.equals("2")) {
			ArrayList<PermissionCountBean> resultList = null;
			resultList = getTopPermissionsAll ();
			request.setAttribute("category", requestCategory);
			request.setAttribute("resultList", resultList);
		} else if (requestCategory.equals("3")) {
			List<CategoryBean> appCategories = appinfoTableReaderDao.getCategories();
			request.setAttribute("appCategoryList", appCategories);

			ArrayList<PermissionCountBean> resultList = null;
			resultList = getTopPermissionsByCategory (selectedAppCategory);
			//getTopPermissionsByCategory (selectedAppCategory);
			request.setAttribute("selectedAppCategory", selectedAppCategory);
			request.setAttribute("category", requestCategory);
			request.setAttribute("resultList", resultList);
		} else if (requestCategory.equals("4")) {
			ArrayList<XternalPackageCountBean> resultList = null;
			resultList = getTopXternalPackagesAll ();
			request.setAttribute("category", requestCategory);
			request.setAttribute("resultList", resultList);
		} /*else if (requestCategory.equals("5")) {
			ArrayList<XternalPackageCountBean> resultList = null;
			resultList = getTopXternalPackagesByCategory ();
			request.setAttribute("category", requestCategory);
			request.setAttribute("resultList", resultList);
		}*/

		RequestDispatcher d = request.getRequestDispatcher("/"+ "page_4.jsp");
		try{
			d.forward(request,response);
		}catch(Exception e){
			System.out.println(e);
		}		
	}


	private String getArg (ArrayList<String> appsInCategoryList, int from, int to) {
		String arg = new String ();
		int i = from;
		for (; i < to - 1; i++){
			arg += " packagename = \'" + appsInCategoryList.get(i) + "\' or";
		}
		arg += " packagename = \'" + appsInCategoryList.get(i) + "\'";
		return arg;
	}
	
	private ArrayList<PermissionCountBean> getTopPermissionsByCategory (String category) throws DAOException{
		ArrayList<PermissionCountBean> resultList = new ArrayList<PermissionCountBean> ();
		
		ArrayList<String> appsInCategoryList = appinfoTableReaderDao.getAppsInCategory (category);
		Map<String, Integer> unsortMap = new HashMap<String, Integer>();
		int i =0;
		int max = appsInCategoryList.size();
		for (; i < max/500; ) {
			String arg = null;
			if (i + 10 < max){
				arg = getArg (appsInCategoryList, i, i + 100);
			}else {
				arg = getArg (appsInCategoryList, i, max);
			}
			unsortMap = analyzedDb.populateTopPermissionsForApp (arg, unsortMap);
			i += 100;
		}

		//System.out.println("Before sorting......");
		//printMap(unsortMap);

		System.out.println("After sorting descindeng order......");
		Map<String, Integer> sortedMapDesc = sortByComparator(unsortMap, false);
		return getSortedResultList (sortedMapDesc, resultList);
	}

    private static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean order)
    {

        List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Entry<String, Integer>>()
        {
            public int compare(Entry<String, Integer> o1,
                    Entry<String, Integer> o2)
            {
                if (order)
                {
                    return o1.getValue().compareTo(o2.getValue());
                }
                else
                {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Entry<String, Integer> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    private ArrayList<PermissionCountBean> getSortedResultList(Map<String, Integer> map, ArrayList<PermissionCountBean> resultList)
    {
        for (Entry<String, Integer> entry : map.entrySet())
        {
            System.out.println("Key : " + entry.getKey() + " Value : "+ entry.getValue());
            PermissionCountBean x = new PermissionCountBean ();
            x.setCount(String.valueOf(entry.getValue()));
            x.setPermission(entry.getKey());
            resultList.add(x);
        }
        return resultList;
    }
    
	ArrayList<XternalPackageCountBean> getTopXternalPackagesAll () throws DAOException {
		return analyzedDb.getAllXternalPackagesCount();
	}

	ArrayList<PermissionCountBean> getTopPermissionsAll () throws DAOException {
		return analyzedDb.getAllPermissionsCount();
	}

	ArrayList<CategoryAppCountBean> getPercentageAppsPerCategory () throws DAOException{
		ArrayList<CategoryAppCountBean> resultList = new ArrayList<CategoryAppCountBean> ();
		resultList =  appinfoTableReaderDao.getCategoryAppCount();
		Integer totalCount = appinfoTableReaderDao.getTotalAppCount ();
		Double total = (double)0;

		for (CategoryAppCountBean x : resultList) {
			Integer count = 0;
			try {
				count = Integer.parseInt(x.getCount());
			}catch (NumberFormatException e){
				//System.out.println ("category " + x.getCategory());
				e.printStackTrace();
				count = 0;
			}
			Double percentage = ((double)(count)) / ((double)totalCount);
			total += percentage;
			x.setCount(String.valueOf(percentage * 100));
			//System.out.println ("percentage - " + x.getCount() + " count " + count + " totalCount " + totalCount);
		}
		//System.out.println ("total = " + total);
		return resultList;
	}
}
