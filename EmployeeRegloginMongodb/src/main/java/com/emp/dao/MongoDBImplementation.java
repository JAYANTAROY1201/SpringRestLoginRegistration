package com.emp.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.emp.model.EmployeeBean;
import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DBCollection;

public class MongoDBImplementation implements GeneralMongo{

	@Override
	public EmployeeBean getEmployee(String email){
		
		DBCollection col=(DBCollection) MongoDbCollection.getCollection();
		EmployeeBean emp=new EmployeeBean();
		//String query="select * from emp where email=?";
		BasicDBObject ob=new BasicDBObject("email",email);
		Cursor rs=col.find(ob);
	     if(rs.hasNext())
	     {
	    	 BasicDBObject bo=(BasicDBObject)rs.next();
	    	 emp.setId(bo.get("_id").toString());
			 emp.setEmail(bo.get("email").toString());
			 emp.setUserName(bo.get("userName").toString());
			 emp.setMobile(bo.get("mobile").toString());
			 emp.setPassword(bo.get("password").toString());	
	     }
		 else {
				emp=null;
				System.out.println("email not exist");
			}		
		return emp;
	}

	/* (non-Javadoc)
	 * @see com.capgemini.dto.GenralJdbc#saveEmployee(com.capgemini.model.EmployeeBean)
	 */
	@Override
	public boolean setEmployee(EmployeeBean emp) {
		DBCollection col=(DBCollection) MongoDbCollection.getCollection();
		boolean res=false;
		//String query="insert into emp values(?,?,?,?,?)";
		try {
			BasicDBObject obj=new BasicDBObject("_id", emp.getId()).
					append("email", emp.getEmail()).
					append("userName", emp.getUserName()).
					append("mobile", emp.getMobile()).
					append("password",emp.getPassword());
			col.insert(obj);
			res=true;
			
		} catch (Exception e) {
			System.out.println("Error in setEmployee()");
		}
		return res;
	}


}
