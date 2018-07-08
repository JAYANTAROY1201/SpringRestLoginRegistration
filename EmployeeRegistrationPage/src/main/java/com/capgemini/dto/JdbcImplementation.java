package com.capgemini.dto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.capgemini.model.EmployeeBean;

public class JdbcImplementation implements GeneralJdbc{

	@Override
	public EmployeeBean getEmployee(String email) {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		ConnectionPool pool=null;
		EmployeeBean emp=new EmployeeBean();
		String query="select * from emp where email=?";
		try
		{
			pool=ConnectionPool.getInstance();
			con=pool.getConnectionFromPool();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			// 4. Process the Results returned by SQL Queries
			if (rs.next()) {
				System.out.println("email exist");
				emp.setId(rs.getString("id"));
				emp.setEmail(rs.getString("email"));
				emp.setUserName(rs.getString("userName"));
				emp.setMobile(rs.getString("mobile"));
				emp.setPassword(rs.getString("password"));	
			} else {
				emp=null;
				System.out.println("email not exist");
			}
			
		}
		catch(Exception e)
		{
			System.out.println("Exception occur at getEmployee()");
		}
		 finally {
				// 5. Close ALL JDBC Objects
				try {
					if (rs != null) {
						rs.close();
					}
					pool.returnConnectionToPool(con);

					if (pstmt != null) {
						pstmt.close();
					}

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		System.out.println(emp);
		return emp;
	}

	/* (non-Javadoc)
	 * @see com.capgemini.dto.GenralJdbc#saveEmployee(com.capgemini.model.EmployeeBean)
	 */
	@Override
	public boolean setEmployee(EmployeeBean emp) {
		Connection con=null;
		PreparedStatement pstmt=null;
		ConnectionPool pool=null;
		boolean res=false;
		String query="insert into emp values(?,?,?,?,?)";
		try {
			pool=ConnectionPool.getInstance();
			con=pool.getConnectionFromPool();
			pstmt=con.prepareStatement(query);
			pstmt.setString(1,emp.getId());
			pstmt.setString(2,emp.getEmail());
			pstmt.setString(3,emp.getUserName());
			pstmt.setString(4,emp.getMobile());
			pstmt.setString(5,emp.getPassword());
			pstmt.executeUpdate();
			System.out.println("Signup successful");
			res=true;
			
		} catch (Exception e) {
			System.out.println("Error in setEmployee()");
		}
		finally {
			try {
				pool.returnConnectionToPool(con);
				if (pstmt != null) {
					pstmt.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return res;
	}

}
