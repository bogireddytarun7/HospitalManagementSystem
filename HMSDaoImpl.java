package com.hms.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.hms.domain.Bed;
import com.hms.domain.BillGroup;
import com.hms.domain.Building;
import com.hms.domain.Department;
import com.hms.domain.Designation;
import com.hms.domain.Floor;
import com.hms.domain.IPDPatient;
import com.hms.domain.LoginUser;
import com.hms.domain.OPDPatient;
import com.hms.domain.Patient;
import com.hms.domain.PatientHistory;
import com.hms.domain.Room;
import com.hms.domain.RoomCategory;
import com.hms.domain.UserCredentials;
import com.hms.domain.UserRole;
import com.hms.domain.VitalParameters;

public class HMSDaoImpl implements IHMSDao {

	
	public boolean verify(String username, String password) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
			System.out.println("hello");
			PreparedStatement pst=con.prepareStatement("select password from login_users where username=?");
			pst.setString(1, username);
			ResultSet rs=pst.executeQuery();
			if(rs.next())
			{
				if(rs.getString(1).equals(password))
				{
					System.out.println("matched");
					return true;
				}
				System.out.println("not matched");
				return false;
			}
		}
		catch(Exception e)
		{
			
		}
		return false;
	}

	
	public LoginUser getProfile(String username) {
		
		LoginUser user=new LoginUser();
		Department department=new Department();
		Designation designation=new Designation();
		UserRole userRole=new UserRole();
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
		
			PreparedStatement pst=con.prepareStatement("select user_id,title,last_name,first_name,middle_name,birth_date,phone,email,birth_place,gender,civil_status,department_name,designation_name,role_name from login_user_profile p,department dept,designation des,user_role role where user_name=? and p.department_id=dept.department_id and p.designation_id=des.designation_id and p.user_role=role.user_role");
			pst.setString(1, username);
			ResultSet rs=pst.executeQuery();
			if(rs.next())
			{
				user.setUserID(rs.getInt(1));
				user.setTitle(rs.getString(2));
				user.setLastName(rs.getString(3));
				user.setFirstName(rs.getString(4));
				user.setMiddleName(rs.getString(5));
				user.setBirthdate(rs.getDate(6));
				user.setPhone(rs.getLong(7));
				user.setEmail(rs.getString(8));
				user.setBirthPlace(rs.getString(9));
				user.setGender(rs.getString(10));
				user.setCivilStatus(rs.getString(11));
				department.setDepartmentName(rs.getString(12));
				user.setDepartment(department);
				designation.setDesignationName(rs.getString(13));
				user.setDesignation(designation);
				userRole.setRoleName(rs.getString(14));
				user.setUserRole(userRole);
				return user;
			}
			return null;
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return null;
	}

	
	public boolean updateProfile(LoginUser user) {
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
		
			
			PreparedStatement pst=con.prepareStatement("update login_user_profile set last_name=?,first_name=?,middle_name=?,birth_date=?,phone=?,email=?,birth_place=?,civil_status=?,gender=? where user_name=?");
			System.out.println("inside update profile");
			pst.setString(1, user.getLastName());
			pst.setString(2, user.getFirstName());
			pst.setString(3, user.getMiddleName());
			pst.setDate(4, user.getBirthdate());
			pst.setLong(5, user.getPhone());
			pst.setString(6, user.getEmail());
			pst.setString(7, user.getBirthPlace());
			pst.setString(8, user.getCivilStatus());
			pst.setString(9, user.getGender());
			pst.setString(10, user.getUserCredentials().getUsername());
			if(pst.executeUpdate()==1)
			{
				System.out.println("updated");
				return true;
			}
			else
			{
				System.out.println("not updated");
				return false;
			}
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		
		return false;
	}

	
	public boolean changePassword(UserCredentials credentials) {
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
		
			
			PreparedStatement pst=con.prepareStatement("update login_users set password=? where username=? and password=?");
			System.out.println("inside update profile");
			pst.setString(1, credentials.getPassword());
			pst.setString(2, credentials.getUsername());
			pst.setString(3, credentials.getOldpassword());
			
			if(pst.executeUpdate()==1)
			{
				System.out.println("updated");
				return true;
			}
			else
			{
				System.out.println("not updated");
				return false;
			}
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		
		return false;
	}


	public List<Department> getDepartments() {
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
		
			
			PreparedStatement pst=con.prepareStatement("select * from department");
			System.out.println("inside update profile");
			
			ResultSet rs=pst.executeQuery();
			List<Department> departments=new ArrayList<Department>();
			
			while(rs.next())
			{
				Department department=new Department();
				department.setDepartmentID(rs.getInt(1));
				department.setDepartmentName(rs.getString(2));
				departments.add(department);
			}
			return departments;
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		
		return null;
	}


	public boolean addDepartment(String departmentName) {
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
		
			
			PreparedStatement pst=con.prepareStatement("insert into department(department_name) values(?)");
			System.out.println("inside update profile");
			pst.setString(1, departmentName);
			
			if(pst.executeUpdate()==1)
			{
				System.out.println("inserted new department");
				return true;
			}
			else
			{
				System.out.println("not inserted");
				return false;
			}
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		
		return false;
	}


	public Department getDepartment(int department_id) {
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
		
			
			PreparedStatement pst=con.prepareStatement("select * from department where department_id=?");
			System.out.println("inside update profile");
			pst.setInt(1,department_id);
			ResultSet rs=pst.executeQuery();
			
			while(rs.next())
			{
				Department department=new Department();
				department.setDepartmentID(rs.getInt(1));
				department.setDepartmentName(rs.getString(2));
				return department;
			}
			
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		
		return null;
	}


	public boolean modifyDepartment(Department department) {
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
		
			
			PreparedStatement pst=con.prepareStatement("update department set department_name=? where department_id=?");
			System.out.println("inside update profile");
			pst.setString(1, department.getDepartmentName());
			pst.setInt(2, department.getDepartmentID());
			if(pst.executeUpdate()==1)
			{
				System.out.println("updated");
				return true;
			}
			else
			{
				System.out.println("not updated");
				return false;
			}
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return false;
	}


	public boolean deleteDepartment(Department department) {
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
		
			
			PreparedStatement pst=con.prepareStatement("delete from department where department_id=?");
			System.out.println("inside update profile");
			pst.setInt(1, department.getDepartmentID());
			if(pst.executeUpdate()==1)
			{
				System.out.println("updated");
				return true;
			}
			else
			{
				System.out.println("not updated");
				return false;
			}
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return false;
	}


	public List<Designation> getDesignations() {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
		
			
			PreparedStatement pst=con.prepareStatement("select * from designation");
			System.out.println("inside update profile");
			
			ResultSet rs=pst.executeQuery();
			List<Designation> designations=new ArrayList<Designation>();
			
			while(rs.next())
			{
				Designation designation=new Designation();
				designation.setDesignationID(rs.getInt(1));
				designation.setDesignationName(rs.getString(2));
				designation.setDesignationDescription(rs.getString(3));
				designations.add(designation);
			}
			return designations;
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return null;
	}


	public Designation getDesignation(Designation designation) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
		
			
			PreparedStatement pst=con.prepareStatement("select * from designation where designation_id=?");
			System.out.println("inside update profile");
			pst.setInt(1,designation.getDesignationID());
			ResultSet rs=pst.executeQuery();
			
			while(rs.next())
			{
				designation=new Designation();
				designation.setDesignationID(rs.getInt(1));
				designation.setDesignationName(rs.getString(2));
				designation.setDesignationDescription(rs.getString(3));
				return designation;
			}
			
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return null;
	}


	public boolean modifyDesignation(Designation designation) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
		
			
			PreparedStatement pst=con.prepareStatement("update designation set designation_name=?,designation_description=? where designation_id=?");
			System.out.println("inside update profile");
			pst.setString(1, designation.getDesignationName());
			pst.setString(2, designation.getDesignationDescription());
			pst.setInt(3, designation.getDesignationID());
			if(pst.executeUpdate()==1)
			{
				System.out.println("updated");
				return true;
			}
			else
			{
				System.out.println("not updated");
				return false;
			}
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return false;
	}


	public boolean deleteDesignation(Designation designation) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
		
			
			PreparedStatement pst=con.prepareStatement("delete from designation where designation_id=?");
			System.out.println("inside update profile");
			pst.setInt(1, designation.getDesignationID());
			if(pst.executeUpdate()==1)
			{
				System.out.println("updated");
				return true;
			}
			else
			{
				System.out.println("not updated");
				return false;
			}
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return false;
	}


	public boolean addDesignation(Designation designation) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
		
			
			PreparedStatement pst=con.prepareStatement("insert into designation(designation_name,designation_description) values(?,?)");
			System.out.println("inside update profile");
			pst.setString(1, designation.getDesignationName());
			pst.setString(2, designation.getDesignationDescription());
			
			if(pst.executeUpdate()==1)
			{
				System.out.println("inserted new designation");
				return true;
			}
			else
			{
				System.out.println("not inserted");
				return false;
			}
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return false;
	}


	public List<BillGroup> getBillGroups() {
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
		
			
			PreparedStatement pst=con.prepareStatement("select * from bill_group_name");
			System.out.println("inside bill group list");
			
			ResultSet rs=pst.executeQuery();
			List<BillGroup> billgroups=new ArrayList<BillGroup>();
			
			while(rs.next())
			{
				BillGroup billgroup=new BillGroup();
				billgroup.setBillGroupID(rs.getInt(1));
				billgroup.setBillGroupName(rs.getString(2));
				billgroup.setBillGroupDescription(rs.getString(3));
				billgroups.add(billgroup);
			
			}
			return billgroups;
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return null;
	}


	public boolean addBillGroup(BillGroup billgroup) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
		
			
			PreparedStatement pst=con.prepareStatement("insert into bill_group_name(billgroup_name,billgroup_description) values(?,?)");
			System.out.println("inside update profile");
			pst.setString(1, billgroup.getBillGroupName());
			pst.setString(2, billgroup.getBillGroupDescription());
			
			if(pst.executeUpdate()==1)
			{
				System.out.println("inserted new bill group");
				return true;
			}
			else
			{
				System.out.println("not inserted");
				return false;
			}
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return false;
	}


	public BillGroup getBillGroup(BillGroup billgroup) {
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
		
			
			PreparedStatement pst=con.prepareStatement("select * from bill_group_name where billgroup_id=?");
			System.out.println("inside bill group table");
			pst.setInt(1, billgroup.getBillGroupID());
			ResultSet rs=pst.executeQuery();
			
			while(rs.next())
			{
				billgroup=new BillGroup();
				billgroup.setBillGroupID(rs.getInt(1));
				billgroup.setBillGroupName(rs.getString(2));
				billgroup.setBillGroupDescription(rs.getString(3));
				return billgroup;
			}
			
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return null;
	}


	public boolean modifyBillGroup(BillGroup billgroup) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
		
			
			PreparedStatement pst=con.prepareStatement("update bill_group_name set billgroup_name=?,billgroup_description=? where billgroup_id=?");
			System.out.println("inside update profile");
			pst.setString(1, billgroup.getBillGroupName());
			pst.setString(2, billgroup.getBillGroupDescription());
			pst.setInt(3, billgroup.getBillGroupID());
			
			if(pst.executeUpdate()==1)
			{
				System.out.println("updated");
				return true;
			}
			else
			{
				System.out.println("not updated");
				return false;
			}
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return false;
	}


	public boolean deleteBillGroup(BillGroup billgroup) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
		
			
			PreparedStatement pst=con.prepareStatement("delete from bill_group_name where billgroup_id=?");
			System.out.println("inside update profile");
			pst.setInt(1, billgroup.getBillGroupID());
			if(pst.executeUpdate()==1)
			{
				System.out.println("updated");
				return true;
			}
			else
			{
				System.out.println("not updated");
				return false;
			}
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return false;
	}


	public List<UserRole> getUserRoles() {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
		
			
			PreparedStatement pst=con.prepareStatement("select * from user_role");
			System.out.println("inside user role list");
			
			ResultSet rs=pst.executeQuery();
			List<UserRole> userRoles=new ArrayList<UserRole>();
			
			while(rs.next())
			{
				UserRole userRole=new UserRole();
				userRole.setUserRole(rs.getInt(1));
				userRole.setRoleName(rs.getString(2));
				userRole.setDescription(rs.getString(3));
				userRoles.add(userRole);
				
			}
			return userRoles;
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return null;
	}


	public boolean addNewUser(LoginUser user) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
			
			PreparedStatement pst=con.prepareStatement("insert into login_user_profile(title,last_name,first_name,middle_name,birth_date,phone,email,birth_place,gender,civil_status,department_id,designation_id,user_role) values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
			System.out.println("inside update profile");
			pst.setString(1, user.getTitle());
			pst.setString(2, user.getLastName());
			pst.setString(3, user.getFirstName());
			pst.setString(4, user.getMiddleName());
			pst.setDate(5, user.getBirthdate());
			pst.setLong(6, user.getPhone());
			pst.setString(7, user.getEmail());
			pst.setString(8, user.getBirthPlace());
			pst.setString(9, user.getGender());
			pst.setString(10, user.getCivilStatus());
			pst.setInt(11, user.getDepartment().getDepartmentID());
			pst.setInt(12, user.getDesignation().getDesignationID());
			pst.setInt(13, user.getUserRole().getUserRole());
			
			
			
			
			if(pst.executeUpdate()==1)
			{
				System.out.println("inserted new designation");
				return true;
			}
			else
			{
				System.out.println("not inserted");
				return false;
			}
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return false;
	}


	public List<LoginUser> getUsers() {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
		
			
			PreparedStatement pst=con.prepareStatement("select user_id,last_name,first_name,middle_name,birth_date,gender,department_name,designation_name,role_name from login_user_profile user,department dept,designation des,user_role role  where user.department_id=dept.department_id and user.designation_id=des.designation_id and user.user_role=role.user_role");
			System.out.println("inside update profile");
			
			ResultSet rs=pst.executeQuery();
			List<LoginUser> users=new ArrayList<LoginUser>();
			
			while(rs.next())
			{
				LoginUser user=new LoginUser();
				user.setUserID(rs.getInt(1));
				user.setLastName(rs.getString(2));
				user.setFirstName(rs.getString(3));
				user.setMiddleName(rs.getString(4));
				user.setBirthdate(rs.getDate(5));
				user.setGender(rs.getString(6));
				Department department=new Department();
				department.setDepartmentName(rs.getString(7));
				user.setDepartment(department);
				Designation designation=new Designation();
				designation.setDesignationName(rs.getString(8));
				user.setDesignation(designation);
				UserRole role=new UserRole();
				role.setRoleName(rs.getString(9));
				user.setUserRole(role);
				users.add(user);
			}
			return users;
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return null;
	}


	public LoginUser getProfile(int userid) {
		
		
		LoginUser user=new LoginUser();
		Department department=new Department();
		Designation designation=new Designation();
		UserRole userRole=new UserRole();
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
			
			PreparedStatement pst=con.prepareStatement("select user_id,title,last_name,first_name,middle_name,birth_date,phone,email,birth_place,gender,civil_status,department_name,designation_name,role_name,p.department_id,p.designation_id,p.user_role from login_user_profile p,department dept,designation des,user_role role where user_id=? and p.department_id=dept.department_id and p.designation_id=des.designation_id and p.user_role=role.user_role");
			pst.setInt(1, userid);
			
			ResultSet rs=pst.executeQuery();
			if(rs.next())
			{
				
				user.setUserID(rs.getInt(1));
				user.setTitle(rs.getString(2));
				user.setLastName(rs.getString(3));
				user.setFirstName(rs.getString(4));
				user.setMiddleName(rs.getString(5));
				user.setBirthdate(rs.getDate(6));
				user.setPhone(rs.getLong(7));
				user.setEmail(rs.getString(8));
				user.setBirthPlace(rs.getString(9));
				user.setGender(rs.getString(10));
				user.setCivilStatus(rs.getString(11));
				department.setDepartmentName(rs.getString(12));
				department.setDepartmentID(rs.getInt(15));
				user.setDepartment(department);
				designation.setDesignationName(rs.getString(13));
				designation.setDesignationID(rs.getInt(16));
				user.setDesignation(designation);
				userRole.setRoleName(rs.getString(14));
				userRole.setUserRole(rs.getInt(17));
				user.setUserRole(userRole);
				return user;
			}
			return null;
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return null;
	}


	public boolean modifyUser(LoginUser user) {
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
		
			
			PreparedStatement pst=con.prepareStatement("update login_user_profile set title=?,last_name=?,first_name=?,middle_name=?,birth_date=?,phone=?,email=?,birth_place=?,civil_status=?,gender=?,department_id=?,designation_id=?,user_role=? where user_id=?");
			System.out.println("inside update profile");
			pst.setString(1, user.getTitle());
			pst.setString(2, user.getLastName());
			pst.setString(3, user.getFirstName());
			pst.setString(4, user.getMiddleName());
			pst.setDate(5, user.getBirthdate());
			pst.setLong(6, user.getPhone());
			pst.setString(7, user.getEmail());
			pst.setString(8, user.getBirthPlace());
			pst.setString(9, user.getCivilStatus());
			pst.setString(10, user.getGender());
			
			pst.setInt(11, user.getDepartment().getDepartmentID());
			pst.setInt(12, user.getDesignation().getDesignationID());
			pst.setInt(13, user.getUserRole().getUserRole());
			pst.setInt(14, user.getUserID());
			if(pst.executeUpdate()==1)
			{
				System.out.println("updated");
				return true;
			}
			else
			{
				System.out.println("not updated");
				return false;
			}
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		
		
		return false;
	}


	public boolean deleteUser(LoginUser user) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
		
			
			PreparedStatement pst=con.prepareStatement("delete from login_user_profile where user_id=?");
			System.out.println("inside update profile");
			pst.setInt(1, user.getUserID());
			if(pst.executeUpdate()==1)
			{
				System.out.println("deleted user");
				return true;
			}
			else
			{
				System.out.println("not deleted user");
				return false;
			}
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return false;
	}


	public boolean checkUser(String username,int userid) {
		try{
			System.out.println("hello tarun");
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
			
			PreparedStatement pst=con.prepareStatement("select user_name from login_user_profile where user_name=?");
			pst.setString(1, username);
			
			ResultSet rs=pst.executeQuery();
			if(rs.next())
			{
				PreparedStatement pst1=con.prepareStatement("select * from login_user_profile where user_id=? and user_name is null");
				pst1.setInt(1, userid);
				ResultSet rs1=pst1.executeQuery();
				if(rs1.next())
				{
					return true;

				}
				return false;
			}
			return false;
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return false;
	}


	public boolean createCredentials(LoginUser user) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
		
			
			PreparedStatement pst=con.prepareStatement("insert into login_users values(?,?)");
			System.out.println("inside insert credentials");
			pst.setString(1, user.getUserCredentials().getUsername());
			pst.setString(2, user.getUserCredentials().getPassword());
			
			if(pst.executeUpdate()==1)
			{
				System.out.println("added credentials");
				PreparedStatement pst1=con.prepareStatement("update login_user_profile set user_name=? where user_id=?");
				pst1.setString(1, user.getUserCredentials().getUsername());
				pst1.setInt(2, user.getUserID());
				if(pst1.executeUpdate()==1)
				return true;
				else
					return false;
			}
			else
			{
				System.out.println("not updated");
				return false;
			}
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return false;
	}


	public List<RoomCategory> getRoomCategories() {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
		
			
			PreparedStatement pst=con.prepareStatement("select * from room_category");
			System.out.println("inside room category");
			
			ResultSet rs=pst.executeQuery();
			List<RoomCategory> categories=new ArrayList<RoomCategory>();
			
			while(rs.next())
			{
				
				RoomCategory category=new RoomCategory();
				category.setCategoryId(rs.getInt(1));
				category.setCategoryName(rs.getString(2));
				category.setDescription(rs.getString(3));
				categories.add(category);
			}
			return categories;
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return null;
	}


	public boolean addRoomCategory(RoomCategory category) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
		
			
			PreparedStatement pst=con.prepareStatement("insert into room_category(category_name,description) values(?,?)");
			System.out.println("inside update profile");
			pst.setString(1, category.getCategoryName());
			pst.setString(2, category.getDescription());
			
			if(pst.executeUpdate()==1)
			{
				System.out.println("inserted new room category");
				return true;
			}
			else
			{
				System.out.println("not inserted");
				return false;
			}
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return false;
	}


	public RoomCategory getRoomCategory(RoomCategory category) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
		
			
			PreparedStatement pst=con.prepareStatement("select * from room_category where category_id=?");
			System.out.println("inside update profile");
			pst.setInt(1,category.getCategoryId());
			ResultSet rs=pst.executeQuery();
			
			while(rs.next())
			{
				category=new RoomCategory();
				category.setCategoryId(rs.getInt(1));
				category.setCategoryName(rs.getString(2));
				category.setDescription(rs.getString(3));
				return category;
			}
			
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return null;
	}


	public boolean modifyRoomCategory(RoomCategory category) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
		
			
			PreparedStatement pst=con.prepareStatement("update room_category set category_name=?,description=? where category_id=?");
			System.out.println("inside update profile");
			pst.setString(1, category.getCategoryName());
			pst.setString(2, category.getDescription());
			pst.setInt(3, category.getCategoryId());
			
			if(pst.executeUpdate()==1)
			{
				System.out.println("updated");
				return true;
			}
			else
			{
				System.out.println("not updated");
				return false;
			}
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return false;
	}


	public boolean deleteRoomCategory(RoomCategory category) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
		
			
			PreparedStatement pst=con.prepareStatement("delete from room_category where category_id=?");
			System.out.println("inside update profile");
			pst.setInt(1, category.getCategoryId());
			if(pst.executeUpdate()==1)
			{
				System.out.println("updated");
				return true;
			}
			else
			{
				System.out.println("not updated");
				return false;
			}
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return false;
	}


	public List<Room> getRooms() {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
		
			
			PreparedStatement pst=con.prepareStatement("SELECT r.room_id,r.room_no,r.room_rate,rc.category_name,f.floor_no,f.floor_name,b.building_id,b.building_name  FROM room r,FLOOR f,building b,building_floor bf,room_category rc WHERE r.build_floor_id=bf.build_floor_id AND "
					+ "bf.building_id=b.building_id AND bf.build_floor_id=f.floor_no AND r.category_id=rc.category_id");
			System.out.println("inside update profile");
			
			ResultSet rs=pst.executeQuery();
			List<Room> rooms=new ArrayList<Room>();
			
			while(rs.next())
			{
				
				Room room=new Room();
				room.setRoomID(rs.getInt(1));
				
				room.setRoomNo(rs.getInt(2));
				
				room.setRoomRate(rs.getInt(3));
				RoomCategory category=new RoomCategory();
				category.setCategoryName(rs.getString(4));
				room.setCategory(category);
				
				Floor floor=new Floor();
				floor.setFloorNo(rs.getInt(5));
				floor.setFloorName(rs.getString(6));
				Building building=new Building();
				building.setBuildingID(rs.getInt(7));
				building.setBuildingName(rs.getString(8));
				floor.setBuilding(building);
				room.setFloor(floor);
				room.setTotalBeds(0);
			
				PreparedStatement pst1=con.prepareStatement("SELECT COUNT(*) AS total_beds FROM bed b,room r WHERE b.room_id=r.room_id AND r.room_id=?");
				pst1.setInt(1, room.getRoomID());
				System.out.println("inside room category");
				
				ResultSet rs1=pst1.executeQuery();
				while(rs1.next())
				{
					System.out.println("hello wwworld");
					room.setTotalBeds(rs1.getInt(1));
				}
				
				
				PreparedStatement pst2=con.prepareStatement("SELECT COUNT(*) AS total_beds FROM bed b,room r WHERE b.room_id=r.room_id AND r.room_id=? AND b.ipd_number IS NULL");
				pst2.setInt(1, room.getRoomID());
				System.out.println("inside room category");
				
				ResultSet rs2=pst2.executeQuery();
				while(rs2.next())
				{
					System.out.println("hello world world");
					room.setUnoccupiedBeds(rs2.getInt(1));
					room.setOccupiedBeds((room.getTotalBeds()-room.getUnoccupiedBeds()));
				}
				rooms.add(room);
				
				
				
			}
			return rooms;
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return null;
	}


	public List<Bed> getBeds() {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
		
			
			PreparedStatement pst=con.prepareStatement("SELECT bd.bed_no,r.room_id,r.room_no,r.room_rate,rc.category_name,f.floor_no,f.floor_name,b.building_id,b.building_name FROM room r,FLOOR f,building b,building_floor bf,room_category rc,bed bd WHERE bd.room_id=r.room_id AND r.build_floor_id=bf.build_floor_id AND bf.building_id=b.building_id AND bf.build_floor_id=f.floor_no AND r.category_id=rc.category_id;");
			System.out.println("inside update profile");
			
			ResultSet rs=pst.executeQuery();
			List<Bed> beds=new ArrayList<Bed>();
			
			while(rs.next())
			{
				Bed bed=new Bed();
				bed.setBedNo(rs.getInt(1));
				
				
				Room room=new Room();
				room.setRoomID(rs.getInt(2));
				
				room.setRoomNo(rs.getInt(3));
				
				room.setRoomRate(rs.getInt(4));
				RoomCategory category=new RoomCategory();
				category.setCategoryName(rs.getString(5));
				room.setCategory(category);
				
				Floor floor=new Floor();
				floor.setFloorNo(rs.getInt(6));
				floor.setFloorName(rs.getString(7));
				Building building=new Building();
				building.setBuildingID(rs.getInt(8));
				building.setBuildingName(rs.getString(9));
				floor.setBuilding(building);
				room.setFloor(floor);
				room.setTotalBeds(0);
			
				PreparedStatement pst1=con.prepareStatement("SELECT COUNT(*) AS total_beds FROM bed b,room r WHERE b.room_id=r.room_id AND r.room_id=?");
				pst1.setInt(1, room.getRoomID());
				System.out.println("inside room category");
				
				ResultSet rs1=pst1.executeQuery();
				while(rs1.next())
				{
					room.setTotalBeds(rs1.getInt(1));
				}
				bed.setRoom(room);
				beds.add(bed);
				
				
			}
			return beds;
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return null;
	}


	public int getNewPatientID() {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
		
			
			PreparedStatement pst=con.prepareStatement("SELECT MAX(patient_id) AS last_id FROM patient_info");
			System.out.println("inside update profile");
			ResultSet rs=pst.executeQuery();
			
			if(rs.next())
			{
				
				return (rs.getInt(1)+1);
			}
			else
			{
				return 1;
			}
			
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return 1;
	}


	public boolean addPatient(Patient patient) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
			
			PreparedStatement pst=con.prepareStatement("insert into patient_info(title,last_name,first_name,middle_name,birth_date,phone,email,birth_place,gender,civil_status,blood_group,entry_date,patient_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
			System.out.println("inside update profile");
			pst.setString(1, patient.getTitle());
			pst.setString(2, patient.getLastName());
			pst.setString(3, patient.getFirstName());
			pst.setString(4, patient.getMiddleName());
			pst.setDate(5, patient.getBirthdate());
			pst.setLong(6, patient.getPhone());
			pst.setString(7, patient.getEmail());
			pst.setString(8, patient.getBirthPlace());
			pst.setString(9, patient.getGender());
			pst.setString(10, patient.getCivilStatus());
			pst.setString(11, patient.getBloodGroup());
			java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
			pst.setTimestamp(12, date);
			pst.setInt(13, patient.getPatientID());
			
			if(pst.executeUpdate()==1)
			{
				System.out.println("inserted new patient");
				return true;
			}
			else
			{
				System.out.println("not inserted patient");
				return false;
			}
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return false;
	}


	public List<Patient> getPatients() {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
		
			
			PreparedStatement pst=con.prepareStatement("select * from patient_info");
			System.out.println("inside update profile");
			
			ResultSet rs=pst.executeQuery();
			List<Patient> patients=new ArrayList<Patient>();
			
			while(rs.next())
			{
				System.out.println("hello world");
				Patient patient=new Patient();
				patient.setPatientID(rs.getInt(1));
				patient.setTitle(rs.getString(2));
				patient.setLastName(rs.getString(3));
				patient.setFirstName(rs.getString(4));
				patient.setMiddleName(rs.getString(5));
				patient.setBirthdate(rs.getDate(6));
				patient.setGender(rs.getString(7));
				patient.setCivilStatus(rs.getString(11));
				patient.setEntryDate(rs.getTimestamp(13));
				patients.add(patient);
			}
			return patients;
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return null;
	}


	public Patient getPatientProfile(Integer patientID) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
			
			PreparedStatement pst=con.prepareStatement("select * from patient_info where patient_id=?");
			pst.setInt(1, patientID);
			Patient patient=new Patient();
			ResultSet rs=pst.executeQuery();
			if(rs.next())
			{
				
				patient.setPatientID(rs.getInt(1));
				patient.setTitle(rs.getString(2));
				patient.setLastName(rs.getString(3));
				patient.setFirstName(rs.getString(4));
				patient.setMiddleName(rs.getString(5));
				patient.setBirthdate(rs.getDate(6));
				patient.setPhone(rs.getLong(8));
				patient.setEmail(rs.getString(9));
				patient.setBirthPlace(rs.getString(10));
				patient.setGender(rs.getString(7));
				patient.setCivilStatus(rs.getString(11));
				patient.setBloodGroup(rs.getString(12));
				patient.setEntryDate(rs.getTimestamp(13));
				return patient;
			}
			return null;
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return null;
	}


	public boolean modifyPatientProfile(Patient patient) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
		
			
			PreparedStatement pst=con.prepareStatement("update patient_info set title=?,last_name=?,first_name=?,middle_name=?,birth_date=?,phone=?,email=?,birth_place=?,civil_status=?,gender=?,blood_group=? where patient_id=?");
			System.out.println("inside update profile");
			pst.setString(1, patient.getTitle());
			pst.setString(2, patient.getLastName());
			pst.setString(3, patient.getFirstName());
			pst.setString(4, patient.getMiddleName());
			pst.setDate(5, patient.getBirthdate());
			pst.setLong(6, patient.getPhone());
			pst.setString(7, patient.getEmail());
			pst.setString(8, patient.getBirthPlace());
			pst.setString(9, patient.getCivilStatus());
			pst.setString(10, patient.getGender());
			pst.setString(11, patient.getBloodGroup());
			pst.setInt(12, patient.getPatientID());
			if(pst.executeUpdate()==1)
			{
				System.out.println("updated");
				return true;
			}
			else
			{
				System.out.println("not updated");
				return false;
			}
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		
		return false;
	}


	public List<LoginUser> getDoctors() {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
		
			
			PreparedStatement pst=con.prepareStatement("select user_id,last_name,first_name,middle_name,birth_date,gender,department_name,designation_name,role_name from login_user_profile user,department dept,designation des,user_role role  where user.department_id=dept.department_id and user.designation_id=des.designation_id and user.user_role=role.user_role and role.role_name=\'Doctor\'");
			System.out.println("inside update profile");
			
			ResultSet rs=pst.executeQuery();
			List<LoginUser> users=new ArrayList<LoginUser>();
			
			while(rs.next())
			{
				LoginUser user=new LoginUser();
				user.setUserID(rs.getInt(1));
				user.setLastName(rs.getString(2));
				user.setFirstName(rs.getString(3));
				user.setMiddleName(rs.getString(4));
				user.setBirthdate(rs.getDate(5));
				user.setGender(rs.getString(6));
				Department department=new Department();
				department.setDepartmentName(rs.getString(7));
				user.setDepartment(department);
				Designation designation=new Designation();
				designation.setDesignationName(rs.getString(8));
				user.setDesignation(designation);
				UserRole role=new UserRole();
				role.setRoleName(rs.getString(9));
				user.setUserRole(role);
				users.add(user);
			}
			return users;
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return null;
	}


	public int getNewOPDPatientID() {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
		
			
			PreparedStatement pst=con.prepareStatement("SELECT MAX(opd_number) AS last_id FROM opdpatientinfo");
			System.out.println("inside update profile");
			ResultSet rs=pst.executeQuery();
			
			if(rs.next())
			{
				
				return (rs.getInt(1)+1);
			}
			else
			{
				return 1;
			}
			
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return 1;
	}


	public boolean addOPDPatient(OPDPatient patient) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
			
			PreparedStatement pst=con.prepareStatement("insert into opdpatientinfo(OPD_Number,patient_id,doctor_id,department,pulse_rate,temperature,height,bp,respiration,weight,allergies,warnings,social_history,family_history,personal_history,past_medical_history,visited_date) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			System.out.println("inside update profile");
			pst.setInt(1, patient.getPatientOPDID());
			pst.setInt(2, patient.getPatientID());
			pst.setInt(3, patient.getDoctor().getUserID());
			pst.setInt(4, patient.getDepartment().getDepartmentID());
			pst.setInt(5, patient.getVitalParameters().getPulseRate());
			pst.setDouble(6, patient.getVitalParameters().getTemperature());
			pst.setDouble(7, patient.getVitalParameters().getHeight());
			pst.setInt(8, patient.getVitalParameters().getBp());
			pst.setInt(9, patient.getVitalParameters().getRespiration());
			pst.setDouble(10, patient.getVitalParameters().getWeight());
			pst.setString(11, patient.getPatientHistory().getAllergies());
			pst.setString(12, patient.getPatientHistory().getWarnings());
			pst.setString(13, patient.getPatientHistory().getSocialHistory());
			pst.setString(14, patient.getPatientHistory().getFamilyHistory());
			pst.setString(15, patient.getPatientHistory().getPersonalHistory());
			pst.setString(16, patient.getPatientHistory().getPastMedicalHistory());
			
			java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
			pst.setTimestamp(17, date);
			
			
			if(pst.executeUpdate()==1)
			{
				System.out.println("inserted new opd patient");
				return true;
			}
			else
			{
				System.out.println("not inserted opd patient");
				return false;
			}
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return false;
	}


	public List<OPDPatient> getOPDPatients() {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
		
			
			PreparedStatement pst=con.prepareStatement("SELECT opd.OPD_Number,opd.patient_id,opd.doctor_id,opd.department,d.department_name,doctor.title,doctor.first_name,doctor.middle_name,doctor.last_name,opd.visited_date,opd.pulse_rate,opd.temperature,opd.height,opd.bp,opd.respiration,opd.weight,opd.allergies,opd.warnings,opd.social_history,opd.family_history,opd.personal_history,opd.past_medical_history,opd.diagnosis,opd.medication,opd.complain FROM opdpatientinfo opd,department d,login_user_profile doctor WHERE opd.doctor_id=doctor.user_id AND opd.department=d.department_id");
			System.out.println("inside update profile");
			
			ResultSet rs=pst.executeQuery();
			List<OPDPatient> patients=new ArrayList<OPDPatient>();
			
			while(rs.next())
			{
				System.out.println("hello world");
				OPDPatient patient=new OPDPatient();
				patient.setPatientOPDID(rs.getInt(1));
				patient.setPatientID(rs.getInt(2));
				LoginUser doctor=new LoginUser();
				doctor.setUserID(rs.getInt(3));
				doctor.setTitle(rs.getString(6));
				doctor.setFirstName(rs.getString(7));
				doctor.setMiddleName(rs.getString(8));
				doctor.setLastName(rs.getString(9));
				patient.setDoctor(doctor);
				Department department=new Department();
				department.setDepartmentID(rs.getInt(4));
				department.setDepartmentName(rs.getString(5));
				patient.setDepartment(department);
				patient.setVisitedDate(rs.getTimestamp(10));
				VitalParameters parameters=new VitalParameters();
				parameters.setPulseRate(rs.getInt(11));
				parameters.setTemperature(rs.getDouble(12));
				parameters.setHeight(rs.getDouble(13));
				parameters.setBp(rs.getInt(14));
				parameters.setRespiration(rs.getInt(15));
				parameters.setWeight(rs.getDouble(16));
				patient.setVitalParameters(parameters);
				PatientHistory history=new PatientHistory();
				history.setAllergies(rs.getString(17));
				history.setWarnings(rs.getString(18));
				history.setSocialHistory(rs.getString(19));
				history.setFamilyHistory(rs.getString(20));
				history.setPersonalHistory(rs.getString(21));
				history.setPastMedicalHistory(rs.getString(22));
				patient.setPatientHistory(history);
				patient.setDiagnosis(rs.getString(23));
				patient.setMedication(rs.getString(24));
				patient.setComplain(rs.getString(25));
								
				PreparedStatement pst1=con.prepareStatement("select title,first_name,middle_name,last_name,birth_date from patient_info where patient_id=?");
				pst1.setInt(1, patient.getPatientID());
				ResultSet rs1=pst1.executeQuery();
				while(rs1.next())
				{
						Patient patient1=new Patient();
						patient1.setTitle(rs1.getString(1));
						patient1.setFirstName(rs1.getString(2));
						patient1.setMiddleName(rs1.getString(3));
						patient1.setLastName(rs1.getString(4));
						patient1.setBirthdate(rs1.getDate(5));
						patient.setPatient(patient1);
				}
				
				patients.add(patient);
			}
			return patients;
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return null;
	}


	public List<OPDPatient> getOPDPatients(String username) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
		
			
			PreparedStatement pst=con.prepareStatement("SELECT opd.OPD_Number,opd.patient_id,opd.doctor_id,opd.department,d.department_name,doctor.title,doctor.first_name,doctor.middle_name,doctor.last_name,opd.visited_date,opd.pulse_rate,opd.temperature,opd.height,opd.bp,opd.respiration,opd.weight,opd.allergies,opd.warnings,opd.social_history,opd.family_history,opd.personal_history,opd.past_medical_history,opd.diagnosis,opd.medication,opd.complain FROM opdpatientinfo opd,department d,login_user_profile doctor WHERE opd.doctor_id=doctor.user_id AND opd.department=d.department_id and doctor.user_name=?");
			pst.setString(1, username);
			System.out.println("inside update profile");
			
			ResultSet rs=pst.executeQuery();
			List<OPDPatient> patients=new ArrayList<OPDPatient>();
			
			while(rs.next())
			{
				System.out.println("hello world");
				OPDPatient patient=new OPDPatient();
				patient.setPatientOPDID(rs.getInt(1));
				patient.setPatientID(rs.getInt(2));
				LoginUser doctor=new LoginUser();
				doctor.setUserID(rs.getInt(3));
				doctor.setTitle(rs.getString(6));
				doctor.setFirstName(rs.getString(7));
				doctor.setMiddleName(rs.getString(8));
				doctor.setLastName(rs.getString(9));
				patient.setDoctor(doctor);
				Department department=new Department();
				department.setDepartmentID(rs.getInt(4));
				department.setDepartmentName(rs.getString(5));
				patient.setDepartment(department);
				patient.setVisitedDate(rs.getTimestamp(10));
				VitalParameters parameters=new VitalParameters();
				parameters.setPulseRate(rs.getInt(11));
				parameters.setTemperature(rs.getDouble(12));
				parameters.setHeight(rs.getDouble(13));
				parameters.setBp(rs.getInt(14));
				parameters.setRespiration(rs.getInt(15));
				parameters.setWeight(rs.getDouble(16));
				patient.setVitalParameters(parameters);
				PatientHistory history=new PatientHistory();
				history.setAllergies(rs.getString(17));
				history.setWarnings(rs.getString(18));
				history.setSocialHistory(rs.getString(19));
				history.setFamilyHistory(rs.getString(20));
				history.setPersonalHistory(rs.getString(21));
				history.setPastMedicalHistory(rs.getString(22));
				patient.setPatientHistory(history);
				patient.setDiagnosis(rs.getString(23));
				patient.setMedication(rs.getString(24));
				patient.setComplain(rs.getString(25));
								
				PreparedStatement pst1=con.prepareStatement("select title,first_name,middle_name,last_name,birth_date from patient_info where patient_id=?");
				pst1.setInt(1, patient.getPatientID());
				ResultSet rs1=pst1.executeQuery();
				while(rs1.next())
				{
						Patient patient1=new Patient();
						patient1.setTitle(rs1.getString(1));
						patient1.setFirstName(rs1.getString(2));
						patient1.setMiddleName(rs1.getString(3));
						patient1.setLastName(rs1.getString(4));
						patient1.setBirthdate(rs1.getDate(5));
						patient.setPatient(patient1);
				}
				
				patients.add(patient);
			}
			return patients;
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return null;
	}


	public OPDPatient getOPDPatientProfile(Integer patientID) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
			
			PreparedStatement pst=con.prepareStatement("SELECT opd.OPD_Number,opd.patient_id,opd.doctor_id,opd.department,d.department_name,doctor.title,doctor.first_name,doctor.middle_name,doctor.last_name,opd.visited_date,opd.pulse_rate,opd.temperature,opd.height,opd.bp,opd.respiration,opd.weight,opd.allergies,opd.warnings,opd.social_history,opd.family_history,opd.personal_history,opd.past_medical_history,opd.diagnosis,opd.medication,opd.complain FROM opdpatientinfo opd,department d,login_user_profile doctor WHERE opd.doctor_id=doctor.user_id AND opd.department=d.department_id and opd.OPD_Number=?");
			pst.setInt(1, patientID);
			OPDPatient patient=new OPDPatient();
			ResultSet rs=pst.executeQuery();
			if(rs.next())
			{
				
				System.out.println("hello world");
				patient.setPatientOPDID(rs.getInt(1));
				patient.setPatientID(rs.getInt(2));
				LoginUser doctor=new LoginUser();
				doctor.setUserID(rs.getInt(3));
				doctor.setTitle(rs.getString(6));
				doctor.setFirstName(rs.getString(7));
				doctor.setMiddleName(rs.getString(8));
				doctor.setLastName(rs.getString(9));
				patient.setDoctor(doctor);
				Department department=new Department();
				department.setDepartmentID(rs.getInt(4));
				department.setDepartmentName(rs.getString(5));
				patient.setDepartment(department);
				patient.setVisitedDate(rs.getTimestamp(10));
				VitalParameters parameters=new VitalParameters();
				parameters.setPulseRate(rs.getInt(11));
				parameters.setTemperature(rs.getDouble(12));
				parameters.setHeight(rs.getDouble(13));
				parameters.setBp(rs.getInt(14));
				parameters.setRespiration(rs.getInt(15));
				parameters.setWeight(rs.getDouble(16));
				patient.setVitalParameters(parameters);
				PatientHistory history=new PatientHistory();
				history.setAllergies(rs.getString(17));
				history.setWarnings(rs.getString(18));
				history.setSocialHistory(rs.getString(19));
				history.setFamilyHistory(rs.getString(20));
				history.setPersonalHistory(rs.getString(21));
				history.setPastMedicalHistory(rs.getString(22));
				patient.setPatientHistory(history);
				patient.setDiagnosis(rs.getString(23));
				patient.setMedication(rs.getString(24));
				patient.setComplain(rs.getString(25));
								
				PreparedStatement pst1=con.prepareStatement("select title,first_name,middle_name,last_name,birth_date from patient_info where patient_id=?");
				pst1.setInt(1, patient.getPatientID());
				ResultSet rs1=pst1.executeQuery();
				while(rs1.next())
				{
						Patient patient1=new Patient();
						patient1.setTitle(rs1.getString(1));
						patient1.setFirstName(rs1.getString(2));
						patient1.setMiddleName(rs1.getString(3));
						patient1.setLastName(rs1.getString(4));
						patient1.setBirthdate(rs1.getDate(5));
						patient.setPatient(patient1);
				}
				return patient;
			}
			return null;
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return null;
	}


	public boolean updateOPDPatient(OPDPatient patient) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
		
			
			PreparedStatement pst=con.prepareStatement("UPDATE opdpatientinfo SET pulse_rate=?,temperature=?,height=?,bp=?,respiration=?,weight=?,allergies=?,WARNINGS=?,social_history=?,family_history=?,personal_history=?,past_medical_history=?,diagnosis=?,medication=?,complain=? WHERE OPD_Number=?");
			System.out.println("inside update profile");
			pst.setInt(1, patient.getVitalParameters().getPulseRate());
			pst.setDouble(2, patient.getVitalParameters().getTemperature());
			pst.setDouble(3, patient.getVitalParameters().getHeight());
			pst.setInt(4, patient.getVitalParameters().getBp());
			pst.setInt(5, patient.getVitalParameters().getRespiration());
			pst.setDouble(6, patient.getVitalParameters().getWeight());
			pst.setString(7, patient.getPatientHistory().getAllergies());
			pst.setString(8, patient.getPatientHistory().getWarnings());
			pst.setString(9, patient.getPatientHistory().getSocialHistory());
			pst.setString(10, patient.getPatientHistory().getFamilyHistory());
			pst.setString(11, patient.getPatientHistory().getPersonalHistory());
			pst.setString(12, patient.getPatientHistory().getPastMedicalHistory());
			pst.setString(13, patient.getDiagnosis());
			pst.setString(14, patient.getMedication());
			pst.setString(15, patient.getComplain());
			pst.setInt(16, patient.getPatientOPDID());
				
			if(pst.executeUpdate()==1)
			{
				System.out.println("updated opd patient");
				return true;
			}
			else
			{
				System.out.println("not updated");
				return false;
			}
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return false;
	}


	public int getNewIPDPatientID() {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
		
			
			PreparedStatement pst=con.prepareStatement("SELECT MAX(ipd_number) AS last_id FROM ipdpatientinfo");
			System.out.println("inside update profile");
			ResultSet rs=pst.executeQuery();
			
			if(rs.next())
			{
				
				return (rs.getInt(1)+1);
			}
			else
			{
				return 1;
			}
			
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return 1;
		
	}


	public boolean addIPDPatient(IPDPatient patient) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
			
			
			PreparedStatement pst1=con.prepareStatement("update bed set ipd_number=? where bed_no=?");
			pst1.setInt(1, patient.getPatientIPDID());
			pst1.setInt(2, patient.getBed().getBedNo());
			
			
			if(pst1.executeUpdate()==1)
			{
				
				
				PreparedStatement pst=con.prepareStatement("insert into ipdpatientinfo(IPD_Number,patient_id,doctor_id,department,pulse_rate,temperature,height,bp,respiration,weight,allergies,warnings,social_history,family_history,personal_history,past_medical_history,visited_date) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				System.out.println("inside update profile");
				pst.setInt(1, patient.getPatientIPDID());
				pst.setInt(2, patient.getPatientID());
				pst.setInt(3, patient.getDoctor().getUserID());
				pst.setInt(4, patient.getDepartment().getDepartmentID());
				pst.setInt(5, patient.getVitalParameters().getPulseRate());
				pst.setDouble(6, patient.getVitalParameters().getTemperature());
				pst.setDouble(7, patient.getVitalParameters().getHeight());
				pst.setInt(8, patient.getVitalParameters().getBp());
				pst.setInt(9, patient.getVitalParameters().getRespiration());
				pst.setDouble(10, patient.getVitalParameters().getWeight());
				pst.setString(11, patient.getPatientHistory().getAllergies());
				pst.setString(12, patient.getPatientHistory().getWarnings());
				pst.setString(13, patient.getPatientHistory().getSocialHistory());
				pst.setString(14, patient.getPatientHistory().getFamilyHistory());
				pst.setString(15, patient.getPatientHistory().getPersonalHistory());
				pst.setString(16, patient.getPatientHistory().getPastMedicalHistory());
				
				java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
				pst.setTimestamp(17, date);
				
				
				if(pst.executeUpdate()==1)
				{
					System.out.println("inserted new opd patient");
					return true;
				}
				else
				{
					System.out.println("not inserted ipd patient");
					return false;
				}
				
			}
			else
			{
				System.out.println("not inserted ipd patient");
				return false;
			}
				
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return false;
	}


	public List<IPDPatient> getIPDPatients() {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
		
			
			PreparedStatement pst=con.prepareStatement("SELECT ipd.IPD_Number,ipd.patient_id,ipd.doctor_id,ipd.department,d.department_name,doctor.title,doctor.first_name,doctor.middle_name,doctor.last_name,ipd.visited_date,ipd.pulse_rate,ipd.temperature,ipd.height,ipd.bp,ipd.respiration,ipd.weight,ipd.allergies,ipd.warnings,ipd.social_history,ipd.family_history,ipd.personal_history,ipd.past_medical_history,ipd.diagnosis,ipd.medication,ipd.complain,b.bed_no,r.room_no  FROM ipdpatientinfo ipd,department d,login_user_profile doctor,bed b,room r WHERE ipd.doctor_id=doctor.user_id AND ipd.department=d.department_id and b.ipd_number=ipd.IPD_Number and b.room_id=r.room_id");
			System.out.println("inside update profile");
			
			ResultSet rs=pst.executeQuery();
			List<IPDPatient> patients=new ArrayList<IPDPatient>();
			
			while(rs.next())
			{
				System.out.println("hello world");
				IPDPatient patient=new IPDPatient();
				patient.setPatientIPDID(rs.getInt(1));
				patient.setPatientID(rs.getInt(2));
				LoginUser doctor=new LoginUser();
				doctor.setUserID(rs.getInt(3));
				doctor.setTitle(rs.getString(6));
				doctor.setFirstName(rs.getString(7));
				doctor.setMiddleName(rs.getString(8));
				doctor.setLastName(rs.getString(9));
				patient.setDoctor(doctor);
				Department department=new Department();
				department.setDepartmentID(rs.getInt(4));
				department.setDepartmentName(rs.getString(5));
				patient.setDepartment(department);
				patient.setVisitedDate(rs.getTimestamp(10));
				VitalParameters parameters=new VitalParameters();
				parameters.setPulseRate(rs.getInt(11));
				parameters.setTemperature(rs.getDouble(12));
				parameters.setHeight(rs.getDouble(13));
				parameters.setBp(rs.getInt(14));
				parameters.setRespiration(rs.getInt(15));
				parameters.setWeight(rs.getDouble(16));
				patient.setVitalParameters(parameters);
				PatientHistory history=new PatientHistory();
				history.setAllergies(rs.getString(17));
				history.setWarnings(rs.getString(18));
				history.setSocialHistory(rs.getString(19));
				history.setFamilyHistory(rs.getString(20));
				history.setPersonalHistory(rs.getString(21));
				history.setPastMedicalHistory(rs.getString(22));
				patient.setPatientHistory(history);
				patient.setDiagnosis(rs.getString(23));
				patient.setMedication(rs.getString(24));
				patient.setComplain(rs.getString(25));
				Room room=new Room();
				room.setRoomNo(rs.getInt(27));
				Bed bed=new Bed();
				bed.setRoom(room);
				bed.setBedNo(rs.getInt(26));
				
				patient.setBed(bed);		
				
				PreparedStatement pst1=con.prepareStatement("select title,first_name,middle_name,last_name,birth_date from patient_info where patient_id=?");
				pst1.setInt(1, patient.getPatientID());
				ResultSet rs1=pst1.executeQuery();
				while(rs1.next())
				{
						Patient patient1=new Patient();
						patient1.setTitle(rs1.getString(1));
						patient1.setFirstName(rs1.getString(2));
						patient1.setMiddleName(rs1.getString(3));
						patient1.setLastName(rs1.getString(4));
						patient1.setBirthdate(rs1.getDate(5));
						patient.setPatient(patient1);
				}
				
				patients.add(patient);
			}
			return patients;
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return null;
	}


	public List<IPDPatient> getIPDPatients(String username) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
		
			
			PreparedStatement pst=con.prepareStatement("SELECT ipd.IPD_Number,ipd.patient_id,ipd.doctor_id,ipd.department,d.department_name,doctor.title,doctor.first_name,doctor.middle_name,doctor.last_name,ipd.visited_date,ipd.pulse_rate,ipd.temperature,ipd.height,ipd.bp,ipd.respiration,ipd.weight,ipd.allergies,ipd.warnings,ipd.social_history,ipd.family_history,ipd.personal_history,ipd.past_medical_history,ipd.diagnosis,ipd.medication,ipd.complain,b.bed_no,r.room_no FROM ipdpatientinfo ipd,department d,login_user_profile doctor,bed b,room r WHERE ipd.doctor_id=doctor.user_id AND ipd.department=d.department_id and b.ipd_number=ipd.IPD_Number and b.room_id=r.room_id and doctor.user_name=?");
			pst.setString(1, username);
			System.out.println("inside update profile");
			
			ResultSet rs=pst.executeQuery();
			List<IPDPatient> patients=new ArrayList<IPDPatient>();
			
			while(rs.next())
			{
				System.out.println("hello world");
				IPDPatient patient=new IPDPatient();
				patient.setPatientIPDID(rs.getInt(1));
				patient.setPatientID(rs.getInt(2));
				LoginUser doctor=new LoginUser();
				doctor.setUserID(rs.getInt(3));
				doctor.setTitle(rs.getString(6));
				doctor.setFirstName(rs.getString(7));
				doctor.setMiddleName(rs.getString(8));
				doctor.setLastName(rs.getString(9));
				patient.setDoctor(doctor);
				Department department=new Department();
				department.setDepartmentID(rs.getInt(4));
				department.setDepartmentName(rs.getString(5));
				patient.setDepartment(department);
				patient.setVisitedDate(rs.getTimestamp(10));
				VitalParameters parameters=new VitalParameters();
				parameters.setPulseRate(rs.getInt(11));
				parameters.setTemperature(rs.getDouble(12));
				parameters.setHeight(rs.getDouble(13));
				parameters.setBp(rs.getInt(14));
				parameters.setRespiration(rs.getInt(15));
				parameters.setWeight(rs.getDouble(16));
				patient.setVitalParameters(parameters);
				PatientHistory history=new PatientHistory();
				history.setAllergies(rs.getString(17));
				history.setWarnings(rs.getString(18));
				history.setSocialHistory(rs.getString(19));
				history.setFamilyHistory(rs.getString(20));
				history.setPersonalHistory(rs.getString(21));
				history.setPastMedicalHistory(rs.getString(22));
				patient.setPatientHistory(history);
				patient.setDiagnosis(rs.getString(23));
				patient.setMedication(rs.getString(24));
				patient.setComplain(rs.getString(25));
				Room room=new Room();
				room.setRoomNo(rs.getInt(27));
				Bed bed=new Bed();
				bed.setRoom(room);
				bed.setBedNo(rs.getInt(26));
				
				patient.setBed(bed);
				
								
				PreparedStatement pst1=con.prepareStatement("select title,first_name,middle_name,last_name,birth_date from patient_info where patient_id=?");
				pst1.setInt(1, patient.getPatientID());
				ResultSet rs1=pst1.executeQuery();
				while(rs1.next())
				{
						Patient patient1=new Patient();
						patient1.setTitle(rs1.getString(1));
						patient1.setFirstName(rs1.getString(2));
						patient1.setMiddleName(rs1.getString(3));
						patient1.setLastName(rs1.getString(4));
						patient1.setBirthdate(rs1.getDate(5));
						patient.setPatient(patient1);
				}
				
				patients.add(patient);
			}
			return patients;
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return null;
	}


	public IPDPatient getIPDPatientProfile(Integer patientID) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
			
			PreparedStatement pst=con.prepareStatement("SELECT ipd.IPD_Number,ipd.patient_id,ipd.doctor_id,ipd.department,d.department_name,doctor.title,doctor.first_name,doctor.middle_name,doctor.last_name,ipd.visited_date,ipd.pulse_rate,ipd.temperature,ipd.height,ipd.bp,ipd.respiration,ipd.weight,ipd.allergies,ipd.warnings,ipd.social_history,ipd.family_history,ipd.personal_history,ipd.past_medical_history,ipd.diagnosis,ipd.medication,ipd.complain,b.bed_no,r.room_no FROM ipdpatientinfo ipd,department d,login_user_profile doctor,bed b,room r WHERE ipd.doctor_id=doctor.user_id AND ipd.department=d.department_id and b.ipd_number=ipd.IPD_Number and b.room_id=r.room_id and ipd.IPD_Number=?");
			pst.setInt(1, patientID);
			IPDPatient patient=new IPDPatient();
			ResultSet rs=pst.executeQuery();
			if(rs.next())
			{
				
				System.out.println("hello world");
				patient.setPatientIPDID(rs.getInt(1));
				patient.setPatientID(rs.getInt(2));
				LoginUser doctor=new LoginUser();
				doctor.setUserID(rs.getInt(3));
				doctor.setTitle(rs.getString(6));
				doctor.setFirstName(rs.getString(7));
				doctor.setMiddleName(rs.getString(8));
				doctor.setLastName(rs.getString(9));
				patient.setDoctor(doctor);
				Department department=new Department();
				department.setDepartmentID(rs.getInt(4));
				department.setDepartmentName(rs.getString(5));
				patient.setDepartment(department);
				patient.setVisitedDate(rs.getTimestamp(10));
				VitalParameters parameters=new VitalParameters();
				parameters.setPulseRate(rs.getInt(11));
				parameters.setTemperature(rs.getDouble(12));
				parameters.setHeight(rs.getDouble(13));
				parameters.setBp(rs.getInt(14));
				parameters.setRespiration(rs.getInt(15));
				parameters.setWeight(rs.getDouble(16));
				patient.setVitalParameters(parameters);
				PatientHistory history=new PatientHistory();
				history.setAllergies(rs.getString(17));
				history.setWarnings(rs.getString(18));
				history.setSocialHistory(rs.getString(19));
				history.setFamilyHistory(rs.getString(20));
				history.setPersonalHistory(rs.getString(21));
				history.setPastMedicalHistory(rs.getString(22));
				patient.setPatientHistory(history);
				patient.setDiagnosis(rs.getString(23));
				patient.setMedication(rs.getString(24));
				patient.setComplain(rs.getString(25));
				Room room=new Room();
				room.setRoomNo(rs.getInt(27));
				Bed bed=new Bed();
				bed.setRoom(room);
				bed.setBedNo(rs.getInt(26));
				
				patient.setBed(bed);				
				
				PreparedStatement pst1=con.prepareStatement("select title,first_name,middle_name,last_name,birth_date from patient_info where patient_id=?");
				pst1.setInt(1, patient.getPatientID());
				ResultSet rs1=pst1.executeQuery();
				while(rs1.next())
				{
						Patient patient1=new Patient();
						patient1.setTitle(rs1.getString(1));
						patient1.setFirstName(rs1.getString(2));
						patient1.setMiddleName(rs1.getString(3));
						patient1.setLastName(rs1.getString(4));
						patient1.setBirthdate(rs1.getDate(5));
						patient.setPatient(patient1);
				}
				return patient;
			}
			return null;
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return null;
	}


	public boolean updateIPDPatient(IPDPatient patient) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
		
			
			PreparedStatement pst=con.prepareStatement("UPDATE ipdpatientinfo SET pulse_rate=?,temperature=?,height=?,bp=?,respiration=?,weight=?,allergies=?,WARNINGS=?,social_history=?,family_history=?,personal_history=?,past_medical_history=?,diagnosis=?,medication=?,complain=? WHERE IPD_Number=?");
			System.out.println("inside update profile");
			pst.setInt(1, patient.getVitalParameters().getPulseRate());
			pst.setDouble(2, patient.getVitalParameters().getTemperature());
			pst.setDouble(3, patient.getVitalParameters().getHeight());
			pst.setInt(4, patient.getVitalParameters().getBp());
			pst.setInt(5, patient.getVitalParameters().getRespiration());
			pst.setDouble(6, patient.getVitalParameters().getWeight());
			pst.setString(7, patient.getPatientHistory().getAllergies());
			pst.setString(8, patient.getPatientHistory().getWarnings());
			pst.setString(9, patient.getPatientHistory().getSocialHistory());
			pst.setString(10, patient.getPatientHistory().getFamilyHistory());
			pst.setString(11, patient.getPatientHistory().getPersonalHistory());
			pst.setString(12, patient.getPatientHistory().getPastMedicalHistory());
			pst.setString(13, patient.getDiagnosis());
			pst.setString(14, patient.getMedication());
			pst.setString(15, patient.getComplain());
			pst.setInt(16, patient.getPatientIPDID());
				
			if(pst.executeUpdate()==1)
			{
				System.out.println("updated ipd patient");
				return true;
			}
			else
			{
				System.out.println("not updated");
				return false;
			}
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return false;
	}


	public boolean deletePatient(Patient patient) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
		
			
			PreparedStatement pst=con.prepareStatement("delete from patient_info where patient_id=?");
			System.out.println("inside update profile");
			pst.setInt(1, patient.getPatientID());
			if(pst.executeUpdate()==1)
			{
				System.out.println("deleted patient");
				return true;
			}
			else
			{
				System.out.println("not deleted patient");
				return false;
			}
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return false;
	}


	public List<IPDPatient> getBeds(int roomID) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/hms","root","tarun");
		
			
			PreparedStatement pst=con.prepareStatement("SELECT b.bed_no,r.room_no,r.room_rate,ipd.ipd_number,ipd.patient_id,ipd.visited_date,p.title,p.last_name,p.middle_name,p.first_name FROM bed b,room r,ipdpatientinfo ipd,patient_info p WHERE b.ipd_number=ipd.IPD_Number AND ipd.patient_id=p.patient_id AND b.room_id=r.room_id and b.room_id=?");
			pst.setInt(1, roomID);
			System.out.println("inside update profile");
			
			ResultSet rs=pst.executeQuery();
			List<IPDPatient> patients=new ArrayList<IPDPatient>();
			
			
			while(rs.next())
			{
				System.out.println("occupied");
				Bed bed=new Bed();
				bed.setBedNo(rs.getInt(1));
				
				
				Room room=new Room();
				
				
				room.setRoomNo(rs.getInt(2));
				
				room.setRoomRate(rs.getInt(3));
				bed.setRoom(room);
				IPDPatient patient=new IPDPatient();
				patient.setBed(bed);
				patient.setPatientIPDID(rs.getInt(4));
				patient.setPatientID(rs.getInt(5));
				patient.setAdmittedDate(rs.getTimestamp(6));
				Patient patient1=new Patient();
				patient1.setTitle(rs.getString(7));
				patient1.setLastName(rs.getString(8));
				patient1.setMiddleName(rs.getString(9));
				patient1.setFirstName(rs.getString(10));
				patient.setPatient(patient1);
				patient.setBedStatus(true);	
				patients.add(patient);
				
			}
			
			PreparedStatement pst1=con.prepareStatement("SELECT b.bed_no,r.room_no  FROM bed b,room r WHERE b.room_id=r.room_id and b.ipd_number IS NULL AND b.room_id=?");
			pst1.setInt(1, roomID);
			System.out.println("inside update profile");
			
			ResultSet rs1=pst1.executeQuery();
			
			while(rs1.next())
			{
				System.out.println("hello tarun");
				IPDPatient patient=new IPDPatient();
				Bed bed=new Bed();
				bed.setBedNo(rs1.getInt(1));
				
				Room room=new Room();
				room.setRoomNo(rs1.getInt(2));
				
				bed.setRoom(room);
				patient.setBed(bed);
				patient.setBedStatus(false);
				patients.add(patient);
			}
			
			
			
			System.out.println("size="+patients.size());
			
			return patients;
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return null;
	}


	
	
}
