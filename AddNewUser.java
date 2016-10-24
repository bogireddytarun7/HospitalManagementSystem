package com.hms.servlet;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hms.domain.Department;
import com.hms.domain.Designation;
import com.hms.domain.LoginUser;
import com.hms.domain.UserCredentials;
import com.hms.domain.UserRole;
import com.hms.service.HMSServiceImpl;
import com.hms.service.IHMSService;

public class AddNewUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private IHMSService service;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		HttpSession session=request.getSession(false);  
		 System.out.println("session value "+(String)session.getAttribute("username"));
	        String username=(String)session.getAttribute("username"); 
	        
	        LoginUser user=new LoginUser();
	        String title=request.getParameter("title");
	        user.setTitle(title);
	        String lname=request.getParameter("lname");
	        System.out.println(lname);
	        user.setLastName(lname);
	        String fname=request.getParameter("fname");
	        System.out.println(fname);
	        user.setFirstName(fname);
	        String mname=request.getParameter("mname");
	        System.out.println(mname);
	        user.setMiddleName(mname);
	        System.out.println(request.getParameter("dob"));
	        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	        try{
	        java.util.Date date = formatter.parse(request.getParameter("dob"));
	        java.sql.Date birthdate = new java.sql.Date(date.getTime()); 
	        user.setBirthdate(birthdate);
	        System.out.println("sql date converted "+birthdate);
	        }catch(ParseException pe)
	        {
	        	
	        }
	        
	        Long phone=Long.parseLong(request.getParameter("phone"));
	        System.out.println(phone);
	        user.setPhone(phone);
	        String email=request.getParameter("email");
	        System.out.println(email);
	        user.setEmail(email);
	        
	        String birthplace=request.getParameter("birthplace");
	        System.out.println(birthplace);
	        user.setBirthPlace(birthplace);
	        String gender=request.getParameter("gender");
	        System.out.println(gender);
	        user.setGender(gender);
	        String status=request.getParameter("status");
	        System.out.println(status);
	        user.setCivilStatus(status);
	        int department_id=Integer.parseInt(request.getParameter("department"));
	        int designation_id=Integer.parseInt(request.getParameter("designation"));
	        int role_id=Integer.parseInt(request.getParameter("role"));
	        Department department=new Department();
	        department.setDepartmentID(department_id);
	        user.setDepartment(department);
	        Designation designation=new Designation();
	        designation.setDesignationID(designation_id);
	        user.setDesignation(designation);
	        UserRole userRole=new UserRole();
	        userRole.setUserRole(role_id);
	        user.setUserRole(userRole);
	        service=new HMSServiceImpl(); 
	        if(service.addNewUser(user))
	        {
	        	System.out.println("new user is inserted");
	    	  	request.setAttribute("message", "New User added successfully!");
		        RequestDispatcher rd=request.getRequestDispatcher("jsp/success.jsp");
				rd.forward(request,response);
	        }
	}

	

}
