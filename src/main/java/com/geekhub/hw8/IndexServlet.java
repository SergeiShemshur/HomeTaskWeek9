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

@WebServlet("/")
public class IndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Path path = Paths.get(req.getRequestURI());

        if (!path.toString().equals("/")) {
            req.setAttribute("path", path);
        }

        if (Files.isDirectory(path)) {
            req.getRequestDispatcher("/dir/view").forward(req, resp);
        } else req.getRequestDispatcher("/file/view").forward(req, resp);
    }
}
