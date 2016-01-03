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

@WebServlet("/file/create")
public class CreateFileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String folder = req.getParameter("folder");
        String fileName = req.getParameter("fileName");
        String fileExtension = req.getParameter("fileExtension");

        Path path = Paths.get(folder + "/" + fileName + "." + fileExtension);

        if (!Files.exists(path)) {
            Files.createFile(path);
        }else try {
            throw new Exception("File with this name exist");
        } catch (Exception e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher("/dir/view?path=" + folder + "/").forward(req, resp);
    }
}
