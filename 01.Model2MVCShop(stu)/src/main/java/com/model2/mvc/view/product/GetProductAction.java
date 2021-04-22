package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;


public class GetProductAction extends Action{

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		String menu = request.getParameter("menu");
		ProductService service=new ProductServiceImpl();
		ProductVO productVO = service.getProduct(prodNo);
		
		request.setAttribute("productVO", productVO);
		request.setAttribute("menu", menu);
		

		
		Cookie cookie = new Cookie("history", Integer.toString(prodNo));
		cookie.setMaxAge(-1);
		
		response.addCookie(cookie);
		
		return "forward:/product/getProduct.jsp";
		
	}
}