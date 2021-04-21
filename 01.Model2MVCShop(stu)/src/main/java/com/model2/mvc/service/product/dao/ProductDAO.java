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
	
	public ProductDAO(){
	}

	public void insertProduct(ProductVO productVO) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "insert into PRODUCT values (seq_product_prod_no.nextval,?,?,?,?,?,sysdate)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate().replace("-", ""));
		stmt.setInt(4, productVO.getPrice());
		stmt.setString(5, productVO.getFileName());
		stmt.executeUpdate();
		
		con.close();
	}

	public ProductVO findProduct(Integer prodNo) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "select * from PRODUCT where PROD_NO=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);

		ResultSet rs = stmt.executeQuery();

		ProductVO productVO = null;
		while (rs.next()) {
			productVO = new ProductVO();
			productVO.setProdNo(rs.getInt("PROD_NO"));
			productVO.setProdName(rs.getString("PROD_NAME"));
			productVO.setFileName(rs.getString("IMAGE_FILE"));
			productVO.setProdDetail(rs.getString("PROD_DETAIL"));
			productVO.setManuDate(rs.getString("MANUFACTURE_DAY"));
			productVO.setPrice(rs.getInt("PRICE"));
			productVO.setRegDate(rs.getDate("REG_DATE"));

		}
		
		con.close();

		return productVO;
	}
	
	public HashMap<String,Object> getProductList(SearchVO searchVO) throws Exception {
		
		Connection con = DBUtil.getConnection();
		
		String sql = "select p.prod_no, p.prod_name, p.reg_date, p.price,"
				+ " nvl(t.tran_status_code, 0) tran_status_code  "
				+ "from product p, transaction t"
				+ " where p.prod_no = t.prod_no(+) ";
		if (searchVO.getSearchCondition() != null) {
			if (searchVO.getSearchCondition().equals("0")) {
				sql += "and p.PROD_NO LIKE '" + searchVO.getSearchKeyword()
							+ "%'";
			} else if (searchVO.getSearchCondition().equals("1")) {
				sql += "and p.PROD_NAME LIKE '" + searchVO.getSearchKeyword()
							+ "%'";
			}
		}
		sql += " order by p.PROD_NO";

		PreparedStatement stmt = 
			con.prepareStatement(	sql,
														ResultSet.TYPE_SCROLL_INSENSITIVE,
														ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = stmt.executeQuery();

		rs.last();
		int total = rs.getRow();
		System.out.println("�ο��� ��:" + total);

		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("count", new Integer(total));

		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1);
		System.out.println("searchVO.getPage():" + searchVO.getPage());
		System.out.println("searchVO.getPageUnit():" + searchVO.getPageUnit());

		ArrayList<ProductVO> list = new ArrayList<ProductVO>();
		if (total > 0) {
			for (int i = 0; i < searchVO.getPageUnit(); i++) {
				ProductVO vo = new ProductVO();
				vo.setProdNo(rs.getInt("PROD_NO"));
				vo.setProdName(rs.getString("PROD_NAME"));
				vo.setRegDate(rs.getDate("REG_DATE"));
				vo.setPrice(rs.getInt("PRICE"));
				vo.setProTranCode(rs.getString("tran_status_code").trim());
				
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
	
	public void updateProduct(ProductVO productVO) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "update PRODUCT set PROD_NAME=?, IMAGE_FILE=?, PROD_DETAIL=?, "
				+ "MANUFACTURE_DAY=?, PRICE=? where PROD_NO=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getFileName());
		stmt.setString(3, productVO.getProdDetail());
		stmt.setString(4, productVO.getManuDate().replace("-", ""));
		stmt.setInt(5, productVO.getPrice());
		//stmt.setDate(6, productVO.getRegDate());
		stmt.setInt(6, productVO.getProdNo());
		stmt.executeUpdate();
		
		con.close();
	}
	
}