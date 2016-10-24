package com.hms.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hms.domain.Designation;
import com.hms.service.HMSServiceImpl;
import com.hms.service.IHMSService;

public class AddNewPatient extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	private IHMSService service;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session=request.getSession(false);  
		 
		 System.out.println("session value "+(String)session.getAttribute("username"));
	        String username=(String)session.getAttribute("username"); 
	        service=new HMSServiceImpl();
	        Integer patientID=service.getNewPatientID();
	
	        RequestDispatcher rd=request.getRequestDispatcher("jsp/AddNewPatient.jsp");
	        request.setAttribute("patientID",patientID);
			rd.forward(request,response);
	}



}
