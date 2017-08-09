package com.jingtang.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jingtang.service.Amf2JsonService;

public class Amf2JsonServlet extends HttpServlet {

	private static final long serialVersionUID = -4075619077307940824L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");

		String result;
		try {
			result = new Amf2JsonService().doAnalysis();
		}
		catch (Exception e) {
			result = "fail";
			e.printStackTrace();
		}
		HttpSession session = req.getSession();
		session.setAttribute("result", result);
		resp.sendRedirect("jsp/show.jsp");
	}
}
