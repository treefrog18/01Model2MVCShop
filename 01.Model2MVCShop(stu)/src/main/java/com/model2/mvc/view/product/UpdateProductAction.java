package com.model2.mvc.view.product;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;



public class UpdateProductAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		
		int prodNo= Integer.parseInt(request.getParameter("prodNo"));
		
		ProductVO productVO = new ProductVO();
		productVO.setProdNo(prodNo);
		productVO.setProdName(request.getParameter("prodName"));
		productVO.setFileName(request.getParameter("fileName"));
		productVO.setProdDetail(request.getParameter("prodDetail"));
		productVO.setManuDate(request.getParameter("manuDate"));
		productVO.setPrice(Integer.parseInt(request.getParameter("price")));
		

		String menu = request.getParameter("menu");
		System.out.println("�������δ�Ʈ�׼��Ǹ޴���:"+menu);
		
		request.setAttribute("menu", menu);
		request.setAttribute("productVO", productVO);
		
		ProductService service=new ProductServiceImpl();
		service.updateProduct(productVO);
		//HttpSession session = request.getSession();
		//Integer sessionId= (((ProductVO)session.getAttribute("productVO")).getProdNo());
		//Integer sessionId=((ProductVO)session.getAttribute("productVO")).getProdNo();
	
		
		return "forward:/getProduct.do?prodNo="+prodNo+"&menu=manage";
	}
}