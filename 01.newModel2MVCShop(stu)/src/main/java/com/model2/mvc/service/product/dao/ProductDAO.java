package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.vo.ProductVO;

public class ProductDAO {

	public ProductDAO() {
		// TODO Auto-generated constructor stub
	}

	public void insertProduct(ProductVO productVO)throws Exception{
		
		Connection con =DBUtil.getConnection();
	
		String sql = "insert into  PRODUCT vlaues(?,?,'product',?,?,?,sysdate)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setInt(1, productVO.getProdNo());
		stmt.setString(2, productVO.getProdName());
		stmt.setString(3, productVO.getProdDetail());
		stmt.setString(4, productVO.getManuDate());
		stmt.setInt(5, productVO.getPrice());
		
		stmt.executeUpdate();
		
		con.close();
		
	}
public HashMap<String,Object> getProductList(SearchVO searchVO) throws Exception {
		
		Connection con = DBUtil.getConnection();
		
		String sql = "select * from PRODUCT ";
		if (searchVO.getSearchCondition() != null) {
			if (searchVO.getSearchCondition().equals("0")) {
				sql += " where PROD_NO LIKE '" + searchVO.getSearchKeyword()+"%'";
						
			} else if (searchVO.getSearchCondition().equals("1")) {
				sql += " where PROD_NAME LIKE '" + searchVO.getSearchKeyword()+"%'";
			}
		}
		sql += " order by PROD_NO";

		PreparedStatement stmt = 
			con.prepareStatement(	sql,
														ResultSet.TYPE_SCROLL_INSENSITIVE,
														ResultSet.CONCUR_UPDATABLE);
																	
		ResultSet rs = stmt.executeQuery();

		rs.last();
		int total = rs.getRow();
		System.out.println("로우의 수:"+ total);
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("count", new Integer(total));

		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1);
		System.out.println("searchVO.getPage():" + searchVO.getPage());
		System.out.println("searchVO.getPageUnit():" + searchVO.getPageUnit());

		ArrayList<ProductVO> list = new ArrayList<ProductVO>();
		if (total > 0) {
			for (int i = 0; i < searchVO.getPageUnit(); i++) {
				ProductVO  vo = new ProductVO();
				vo.setProdName(rs.getString("PROD_NAME"));
				vo.setFileName(rs.getString("IMAGE_FILE"));
				vo.setManuDate(rs.getString("MANUFACTURE_DAY"));
				vo.setProdDetail(rs.getString("PROD_DETAIL"));
				//vo.setProTranCode(rs.getString("PRODRANCODE"));
				vo.setPrice(rs.getInt("PRICE"));
				vo.setProdNo(rs.getInt("PROD_NO"));
		
				list.add(vo);
				if (!rs.next())
					break;
			}
		}
			System.out.println("list.size() : "+ list.size());
			map.put("list", list);
			System.out.println("map().size() : "+ map.size());

			con.close();
	
			return map;
}

	public ProductVO findPoduct(int prodNo) throws Exception{
		
		Connection con = DBUtil.getConnection();
		
		String sql = "select * from PRODUCT where PROD_NO=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setInt(1,prodNo);

		ResultSet rs = stmt.executeQuery();
		
		ProductVO productVO = null;
		while (rs.next()) {
			productVO=new ProductVO();
			productVO.setProdName(rs.getString("PROD_NAME"));
			productVO.setFileName(rs.getString("IMAGE_FILE"));
			productVO.setManuDate(rs.getString("MANUFACTURE_DAY"));
			productVO.setProdDetail(rs.getString("PROD_DETAIL"));
			//productVO.setProTranCode(rs.getString("PROTRANCODE"));
			productVO.setPrice(rs.getInt("PRICE"));
			productVO.setProdNo(rs.getInt("PROD_NO"));
			System.out.println("가나다라마바사"+prodNo);
		}
			con.close();

			return productVO;
			
}
	
	public void updateProduct(ProductVO productVO) throws Exception {
		
		Connection con = DBUtil.getConnection();
		
		String sql = "update PRODUCT set PROD_NAME=?,PROD_DETAIL=?,MANUFACTURE_DAY=?,PRICE =? where PROD_NO=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		System.out.println("=======디비연결======");
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate());
		stmt.setInt(4, productVO.getPrice());
		stmt.setInt(5, productVO.getProdNo());
		stmt.executeUpdate();

		
		
		con.close();
	}
}