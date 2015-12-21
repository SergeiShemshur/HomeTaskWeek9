package com.geekhub.hw8;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@WebServlet("/file/view")
public class ViewFileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Path path = (Path) req.getAttribute("path");

        resp.setContentType("application");
        resp.setHeader("Content-Disposition","attachment;filename=\"" + path.getFileName().toString() + "\"");

        byte[] file =Files.readAllBytes(path);
        resp.setContentLength(file.length);

        resp.getOutputStream().write(file);
    }
}
