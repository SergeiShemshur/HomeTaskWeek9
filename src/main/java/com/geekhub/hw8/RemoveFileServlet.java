package com.geekhub.hw8;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet("/file/remove")
public class RemoveFileServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String removeFile = req.getParameter("remove");
        Path path = Paths.get(removeFile);
        String folder = path.getParent().toString();
        if(Files.exists(path)){
            Files.delete(path);
        }
        req.getRequestDispatcher(folder+"/").forward(req, resp);


    }
}
