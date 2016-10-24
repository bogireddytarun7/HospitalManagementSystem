package com.hms.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hms.domain.Bed;
import com.hms.domain.Department;
import com.hms.domain.IPDPatient;
import com.hms.domain.LoginUser;
import com.hms.domain.OPDPatient;
import com.hms.domain.PatientHistory;
import com.hms.domain.Room;
import com.hms.domain.VitalParameters;
import com.hms.service.HMSServiceImpl;
import com.hms.service.IHMSService;
import com.hms.util.NumberCheck;


public class AddIPDPatient extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	private IHMSService service;
	
	   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session=request.getSession(false);  
		 System.out.println("session value "+(String)session.getAttribute("username"));
	        String username=(String)session.getAttribute("username"); 
		
	        IPDPatient patient=new IPDPatient();
	        
	        int patientID=Integer.parseInt(request.getParameter("pid"));
	        patient.setPatientID(patientID);
	        System.out.println(patientID);
	        int patientIPDID=Integer.parseInt(request.getParameter("ipdpid"));
	        patient.setPatientIPDID(patientIPDID);
	        System.out.println(patient.getPatientIPDID());
	        LoginUser doctor=new LoginUser();
	        doctor.setUserID(Integer.parseInt(request.getParameter("doctorID")));
	        patient.setDoctor(doctor);
	        System.out.println(patient.getDoctor().getUserID());
	        Department department=new Department();
	        department.setDepartmentID(Integer.parseInt(request.getParameter("department")));
	        patient.setDepartment(department);
	        System.out.println(patient.getDepartment().getDepartmentID());
	        VitalParameters parameters=new VitalParameters();
	        NumberCheck check=new NumberCheck();
	        String bp=request.getParameter("bp");
	        System.out.println(bp);
	        if(check.isNumeric(bp))
	        parameters.setBp(Integer.parseInt("bp"));
	        else
	        parameters.setBp(0);
	        
	        String pulseRate=request.getParameter("pulserate");
	        if(check.isNumeric(pulseRate))
	        parameters.setPulseRate(Integer.parseInt("pulseRate"));
	        else
	        parameters.setPulseRate(0);
	        
	        
	        String temperature=request.getParameter("temperature");
	        System.out.println(temperature);
	        if(check.isNumeric(temperature))
	        parameters.setTemperature(Double.parseDouble("temperature"));
	        else
	        parameters.setTemperature(0);
	        
	        String respiration=request.getParameter("respiration");
	        if(check.isNumeric(respiration))
	        parameters.setRespiration(Integer.parseInt("respiration"));
	        else
	        parameters.setRespiration(0);
	        
	        String height=request.getParameter("height");
	        if(check.isNumeric(height))
	        parameters.setHeight(Double.parseDouble(height));
	        else
	        parameters.setHeight(0);
	        
	        String weight=request.getParameter("weight");
	        if(check.isNumeric(weight))
	        parameters.setWeight(Double.parseDouble(weight));
	        else
	        parameters.setWeight(0);
	        
	        patient.setVitalParameters(parameters);
	        
	        PatientHistory history=new PatientHistory();
	        
	        history.setAllergies(request.getParameter("allergies"));
	        history.setFamilyHistory(request.getParameter("familyhistory"));
	        history.setPastMedicalHistory(request.getParameter("pastmedicalhistory"));
	        history.setPersonalHistory(request.getParameter("personalhistory"));
	        history.setSocialHistory(request.getParameter("socialhistory"));
	        history.setWarnings(request.getParameter("warnings"));
	        
	        patient.setPatientHistory(history);
	        
	        Bed bed=new Bed();
	        bed.setBedNo(Integer.parseInt(request.getParameter("bedno")));
	        
	        Room room=new Room();
	        room.setRoomNo(Integer.parseInt(request.getParameter("roomno")));
	        bed.setRoom(room);
	        patient.setBed(bed);
	        
		
		service=new HMSServiceImpl();
		if(service.addIPDPatient(patient))
		{
			System.out.println("new ipd patient is inserted");
    	  	request.setAttribute("message", "ipd patient added successfully!");
	        RequestDispatcher rd=request.getRequestDispatcher("jsp/success.jsp");
			rd.forward(request,response);
		}
		else
		{
			request.setAttribute("message", "room or bed selected not vacant");
	        RequestDispatcher rd=request.getRequestDispatcher("jsp/success.jsp");
			rd.forward(request,response);
		}
		
		
	}

	
}
