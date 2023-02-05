package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
			//Department obj = new Department(1, "books");
			
			//Seller seller = new Seller(21, "Bob", "bob@gmail.com", new Date(), 3000.0, obj);
			
			SellerDao sellerDao = DaoFactory.createSellerDao();
			
			Seller seller = sellerDao.findById(3);
			
			//System.out.println(obj);
			System.out.println("=== TEST 1: seller findbyId ===");
			System.out.println(seller);
			
			System.out.println("\n=== TEST 2: seller findbyDepartment ===");
			Department department = new Department(2,null);
			List<Seller> list = sellerDao.findByDepartment(department);
			for (Seller obj : list) {
				System.out.println(obj);
			}

			
			
	}

}
