package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;


public class UpdateProductViewAction extends Action{

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		Integer prodNo= Integer.parseInt(request.getParameter("prodNo"));
		String menu = request.getParameter("menu");
		System.out.println("�������δ�Ʈ��׼��� �޴���:"+menu);
		ProductService service=new ProductServiceImpl();
		ProductVO productVO=service.getProduct((prodNo));
		
		request.setAttribute("productVO", productVO);
		request.setAttribute("menu", menu);
		return "forward:/product/updateProductView.jsp";
	}
}
