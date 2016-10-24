package com.hms.domain;

import java.sql.Timestamp;

public class OPDPatient {
	
	private int patientOPDID;
	private int patientID;
	private Patient patient;
	private LoginUser doctor;
	private Department department;
	private VitalParameters vitalParameters;
	private PatientHistory patientHistory;
	private Timestamp visitedDate;
	private String diagnosis;
	private String medication;
	private String complain;
	public int getPatientOPDID() {
		return patientOPDID;
	}
	public void setPatientOPDID(int patientOPDID) {
		this.patientOPDID = patientOPDID;
	}
	public int getPatientID() {
		return patientID;
	}
	public void setPatientID(int patientID) {
		this.patientID = patientID;
	}
	public LoginUser getDoctor() {
		return doctor;
	}
	public void setDoctor(LoginUser doctor) {
		this.doctor = doctor;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public VitalParameters getVitalParameters() {
		return vitalParameters;
	}
	public void setVitalParameters(VitalParameters vitalParameters) {
		this.vitalParameters = vitalParameters;
	}
	public PatientHistory getPatientHistory() {
		return patientHistory;
	}
	public void setPatientHistory(PatientHistory patientHistory) {
		this.patientHistory = patientHistory;
	}
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public Timestamp getVisitedDate() {
		return visitedDate;
	}
	public void setVisitedDate(Timestamp visitedDate) {
		this.visitedDate = visitedDate;
	}
	public String getDiagnosis() {
		return diagnosis;
	}
	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}
	public String getMedication() {
		return medication;
	}
	public void setMedication(String medication) {
		this.medication = medication;
	}
	public String getComplain() {
		return complain;
	}
	public void setComplain(String complain) {
		this.complain = complain;
	}
	

}
