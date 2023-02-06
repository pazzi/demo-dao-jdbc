package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao{
	
	private Connection conn;
	
	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	

	@Override
	public void insert(Department obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"insert into department "
					+"(Name) "
					+ "values "
					+ "(?)",
					Statement.RETURN_GENERATED_KEYS);
			
		st.setString(1, obj.getName());
				
		int rowsAffected = st.executeUpdate();
		
		if (rowsAffected > 0) {
			ResultSet rs = st.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);
				obj.setId(id);
			}
			DB.closeResultSet(rs);
		}else {
			throw new DbException("Unexpected error!: No rows affected!");
		}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}	

	@Override
	public void update(Department obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"update department "
					+ "set Name=? "
					+ "where Id=?");
			
		st.setString(1, obj.getName());
		st.setInt(2, obj.getId());
			
		st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void deletById(Integer Id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(""
					+ "delete from department "
					+ "where Id=?");
			st.setInt(1, Id);
			st.executeUpdate();
			
		}
		catch (SQLException e){
			throw new DbException(e.getMessage());
			
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Department findById(Integer Id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"select department.* "
							+ "from department "
							+ " where Id = ?");
			
			st.setInt(1, Id);
			rs = st.executeQuery();
			if (rs.next()) {
				Department dep = instantiateDepartment(rs);
				return dep;
				
			}
			return(null);				
					
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
			
		}
			}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("Id"));
		dep.setName(rs.getString("Name"));
		return dep;
	}


	@Override
	public List<Department> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"select department.* "
							+ "from department "
							+ "order by Name");
			
			rs = st.executeQuery();
			
			List<Department> list = new ArrayList<>();
			
			
			while (rs.next()) {
				
				Department obj = instantiateDepartment(rs);
				list.add(obj);
			}
			return list;				
					
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
			
		}
	}

/*
	@Override
	public List<Department> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"select seller.*, department.name as DepName "
							+ "from seller, department "
							+ "where seller.DepartmentId = department.id "
							+ " and seller.DepartmentId = ? 	"
							+ "order by Name");
			
			st.setInt(1, department.getId());
			
			rs = st.executeQuery();
			
			List<Seller> list = new ArrayList<>();
			
			Map<Integer, Department> map = new HashMap<>();
			
			while (rs.next()) {
				
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				if (dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				Seller obj = instantiateSeller(rs, dep);
				list.add(obj);
			}
			return list;				
					
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
			
		}
	}
*/	
	

}
