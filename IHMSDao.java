package com.hms.dao;

import java.util.List;

import com.hms.domain.Bed;
import com.hms.domain.BillGroup;
import com.hms.domain.Department;
import com.hms.domain.Designation;
import com.hms.domain.IPDPatient;
import com.hms.domain.LoginUser;
import com.hms.domain.OPDPatient;
import com.hms.domain.Patient;
import com.hms.domain.Room;
import com.hms.domain.RoomCategory;
import com.hms.domain.UserCredentials;
import com.hms.domain.UserRole;

public interface IHMSDao {
	
	public boolean verify(String username,String password);
	public LoginUser getProfile(String username);
	public boolean updateProfile(LoginUser user);
	public boolean changePassword(UserCredentials credentials);
	public List<Department> getDepartments();
	public boolean addDepartment(String departmentName);
	public Department getDepartment(int department_id);
	public boolean modifyDepartment(Department department);
	public boolean deleteDepartment(Department department);
	public List<Designation> getDesignations();
	public boolean addDesignation(Designation designation);
	public Designation getDesignation(Designation designation);
	public boolean modifyDesignation(Designation designation);
	public boolean deleteDesignation(Designation designation);
	public List<BillGroup> getBillGroups();
	public boolean addBillGroup(BillGroup billgroup);
	public BillGroup getBillGroup(BillGroup billgroup);
	public boolean modifyBillGroup(BillGroup billgroup);
	public boolean deleteBillGroup(BillGroup billgroup);
	public List<UserRole> getUserRoles();
	public boolean addNewUser(LoginUser user);
	public List<LoginUser> getUsers();
	public LoginUser getProfile(int userid);
	public boolean modifyUser(LoginUser user);
	public boolean deleteUser(LoginUser user);
	public boolean checkUser(String username,int userid);
	public boolean createCredentials(LoginUser user);
	public List<RoomCategory> getRoomCategories();
	public boolean addRoomCategory(RoomCategory category);
	public RoomCategory getRoomCategory(RoomCategory category);
	public boolean modifyRoomCategory(RoomCategory category);
	public boolean deleteRoomCategory(RoomCategory category);
	public List<Room> getRooms();
	public List<Bed> getBeds();
	public int getNewPatientID();
	public boolean addPatient(Patient patient);
	public List<Patient> getPatients();
	public Patient getPatientProfile(Integer patientID);
	public boolean modifyPatientProfile(Patient patient);
	public List<LoginUser> getDoctors();
	public int getNewOPDPatientID();
	public boolean addOPDPatient(OPDPatient patient);
	public List<OPDPatient> getOPDPatients();
	public List<OPDPatient> getOPDPatients(String username);
	public OPDPatient getOPDPatientProfile(Integer patientID);
	public boolean updateOPDPatient(OPDPatient patient);
	public int getNewIPDPatientID();
	public boolean addIPDPatient(IPDPatient patient);
	public List<IPDPatient> getIPDPatients();
	public List<IPDPatient> getIPDPatients(String username);
	public IPDPatient getIPDPatientProfile(Integer patientID);
	public boolean updateIPDPatient(IPDPatient patient);
	public boolean deletePatient(Patient patient);
	public List<IPDPatient> getBeds(int roomID);
}

