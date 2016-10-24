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

import com.hms.domain.Patient;
import com.hms.service.HMSServiceImpl;
import com.hms.service.IHMSService;


public class AddPatient extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	private IHMSService service;
	
	   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session=request.getSession(false);  
		 System.out.println("session value "+(String)session.getAttribute("username"));
	        String username=(String)session.getAttribute("username"); 
		
	        Patient patient=new Patient();
	        
	        int patientID=Integer.parseInt(request.getParameter("pid"));
	        patient.setPatientID(patientID);
	        System.out.println(patientID);
	        String title=request.getParameter("title");
	        patient.setTitle(title);
	        System.out.println(patient.getTitle());
	        String lname=request.getParameter("lname");
	        System.out.println(lname);
	        patient.setLastName(lname);
	        String fname=request.getParameter("fname");
	        System.out.println(fname);
	        patient.setFirstName(fname);
	        String mname=request.getParameter("mname");
	        System.out.println(mname);
	        patient.setMiddleName(mname);
	        System.out.println(request.getParameter("dob"));
	        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	        try{
	        java.util.Date date = formatter.parse(request.getParameter("dob"));
	        java.sql.Date birthdate = new java.sql.Date(date.getTime()); 
	        patient.setBirthdate(birthdate);
	        System.out.println("sql date converted "+birthdate);
	        }catch(ParseException pe)
	        {
	        	
	        }
	        
	        Long phone=Long.parseLong(request.getParameter("phone"));
	        System.out.println(phone);
	        patient.setPhone(phone);
	        String email=request.getParameter("email");
	        System.out.println(email);
	        patient.setEmail(email);
	        
	        String birthplace=request.getParameter("birthplace");
	        System.out.println(birthplace);
	        patient.setBirthPlace(birthplace);
	        String gender=request.getParameter("gender");
	        System.out.println(gender);
	        patient.setGender(gender);
	        String status=request.getParameter("status");
	        System.out.println(status);
	        patient.setCivilStatus(status);
	        String bloodGroup=request.getParameter("bloodgroup");
	        System.out.println(bloodGroup);
	        patient.setBloodGroup(bloodGroup);
		
		service=new HMSServiceImpl();
		if(service.addPatient(patient))
		{
			System.out.println("new patient is inserted");
    	  	request.setAttribute("message", "patient added successfully!");
	        RequestDispatcher rd=request.getRequestDispatcher("jsp/success.jsp");
			rd.forward(request,response);
		}
		
		
	}

}
