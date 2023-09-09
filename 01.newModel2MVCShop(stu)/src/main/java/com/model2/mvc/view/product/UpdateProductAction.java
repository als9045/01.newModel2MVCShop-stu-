package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.user.vo.UserVO;

public class UpdateProductAction extends Action{

	public String execute(HttpServletRequest request, HttpServletResponse respos) throws Exception{

		int prodNo=Integer.parseInt(request.getParameter("prodNo"));
		System.out.println("no~~"+prodNo);
		
		ProductVO productVO =new ProductVO();
		productVO.setProdName(request.getParameter("prodName"));
		productVO.setProdDetail(request.getParameter("prodDetail"));
		productVO.setManuDate(request.getParameter("manuDate"));
		productVO.setPrice(Integer.parseInt(request.getParameter("price")));
		productVO.setProdNo(Integer.parseInt(request.getParameter("prodNo")));
		//NumberFormatException 이유 물어보기
		
		ProductService service = new ProductServiceImpl();
		ProductVO vo=service.updateProduct(productVO);
		System.out.println("PoroductVO~~"+vo);
			
		request.setAttribute("productVO", vo);
		
		
		return "redirect:/getProduct.do?prodNo="+prodNo;
		
	}

}
