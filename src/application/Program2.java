package application;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		//Department obj = new Department(1, "books");
		
		//Seller seller = new Seller(21, "Bob", "bob@gmail.com", new Date(), 3000.0, obj);
	   Scanner sc = new Scanner(System.in);	
	
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		
		Department department = departmentDao.findById(3);
		
		//System.out.println(obj);
		System.out.println("=== TEST 1: department findbyId ===");
		System.out.println(department);
		
		/*
		System.out.println("\n=== TEST 2: seller findbyDepartment ===");
		Department department = new Department(2,null);
		List<Seller> list = sellerDao.findByDepartment(department);
		for (Seller obj : list) {
			System.out.println(obj);
		}
*/
		System.out.println("\n=== TEST 2: department findAll ===");
		List<Department> list;
		list = departmentDao.findAll();
		for (Department obj : list) {
			System.out.println(obj);
		}

		System.out.println("=== TEST 3: department insert ===");
		Department newDepartment = new Department(null, "D2");
		departmentDao.insert(newDepartment);
		System.out.println("Inserted new id:"+ newDepartment.getId());
		
		
		System.out.println("=== TEST 4: department update ===");
		department = departmentDao.findById(1);
		department.setName("Depto1");
		departmentDao.update(department);
		System.out.println("Update complete!");

		System.out.println("=== TEST 5: seller delete ===");
		System.out.println("Enter id to delete test:");	
		int id = sc.nextInt();
		departmentDao.deletById(id);
		System.out.println("Delete completed");
		
		sc.close();
		
	}


}
