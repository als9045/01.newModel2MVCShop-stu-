package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;


public class GetProductAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int prodno=Integer.parseInt(request.getParameter("prodNo"));
		System.out.println("no~~"+prodno);
		ProductService service=new ProductServiceImpl();
		ProductVO vo=service.getProduct(prodno);
		System.out.println("vo~~~~~"+vo);
		request.setAttribute("productVO", vo);

		return "forward:/product/getProduct.jsp";
	}
}
