package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;


public class UpdateTranCodeAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		
		
		if(request.getParameter("prodNo") == null) {
			int tranNo = Integer.parseInt(request.getParameter("tranNo"));
			PurchaseService service=new PurchaseServiceImpl();
			PurchaseVO purchaseVO=service.getPurchase(tranNo);
			System.out.println(purchaseVO);
			
			service.updateTranCode(purchaseVO);
			System.out.println(purchaseVO);
			
			request.setAttribute("purchaseVO", purchaseVO);
			return "forward:/listPurchase.do";
		}else {
			int prodNo = Integer.parseInt(request.getParameter("prodNo"));
			PurchaseService service=new PurchaseServiceImpl();
			PurchaseVO purchaseVO=service.getPurchase2(prodNo);
			System.out.println(purchaseVO);
			
			
			service.updateTranCode(purchaseVO);
			
			request.setAttribute("purchaseVO", purchaseVO);
			System.out.println(purchaseVO);
			return "forward:/listProduct.do?menu=manage";
		}
	}
}