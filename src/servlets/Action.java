package servlets;

import java.util.HashMap;
import java.util.Map;

import org.mybeans.dao.DAOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Action {
    // Returns the name of the action, used to match the request in the hash table
    public abstract String getName();

    // Returns the name of the jsp used to render the output.
    public abstract void perform(HttpServletRequest request, HttpServletResponse response) throws DAOException;

    //
    // Class methods to manage dispatching to Actions
    //
    private static Map<String,Action> hash = new HashMap<String,Action>();

    public static void add(Action a) {
    	synchronized (hash) {
    		hash.put(a.getName(),a);
    	}
    }

    public static void perform(String name,HttpServletRequest request, HttpServletResponse response)throws DAOException {
        Action a;
        synchronized (hash) {
        	a = hash.get(name);
        }
        
        if (a != null)  a.perform(request, response);
    }
}
