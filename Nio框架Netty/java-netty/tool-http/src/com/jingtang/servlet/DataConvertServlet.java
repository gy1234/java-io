package com.jingtang.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jingtang.service.DataConvertServer;
import com.jingtang.service.factory.ConvertServerFactory;

public class DataConvertServlet extends HttpServlet {

    private static final long serialVersionUID = -4075619077307940824L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        String sfName = req.getParameter("sfname");
        if (null == sfName || sfName.isEmpty()) {
            redirect(req, resp, "请填写原始数据文件!");
            return;
        }
        String cmethod = req.getParameter("cmethod");
        if (null == cmethod || sfName.isEmpty()) {
            redirect(req, resp, "请填写转换方式!");
            return;
        }
        DataConvertServer server = ConvertServerFactory.buildServer(Integer.parseInt(cmethod));
        if (null == server) {
            redirect(req, resp, "暂不支持转换方式!");
            return;
        }
        String result = "data convert fail!";
        try {
            result = server.doConvert(sfName);
        } catch (Exception e) {
            result = "data convert exception! info=" + e.getMessage();
            e.printStackTrace();
        }
        redirect(req, resp, result);
    }

    /**
     * Title: redirect <br/>
     * Description: 消息转发输出 <br/>
     * @return: void
     * @throws ServletException 
     */
    private void redirect(HttpServletRequest req, HttpServletResponse resp, String result) throws IOException, ServletException {
        HttpSession session = req.getSession();
        session.setAttribute("result", result);
        req.getRequestDispatcher("/WEB-INF/jsp/show.jsp").forward(req, resp);
    }
}
