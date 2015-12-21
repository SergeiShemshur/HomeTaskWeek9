package com.geekhub.hw8;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@WebServlet(value = "/dir/view", initParams = {
        @WebInitParam(name = "root", value = "/home/")
})
public class ViewDirectoryServlet extends HttpServlet {

    private static Path ROOT_PATH;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ROOT_PATH = Paths.get(config.getInitParameter("root"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Path pathFolder = (Path) req.getAttribute("path");
        String folder;
        StringBuilder sb = new StringBuilder();

        sb.append("<html>");
        sb.append("<head>");
        sb.append("</head>");
        sb.append("<body>");

        if (pathFolder == null) {
            for (Path path : pathList(ROOT_PATH)) {
                appendLink(sb, path.getFileName().toString(), path);
            }
            folder = ROOT_PATH.toString();
        } else {
            for (Path path : pathList(pathFolder)) {
                appendLink(sb, path.getFileName().toString(), path);
            }
            folder = pathFolder.toString();
        }

        sb.append("<h3> Create file :</h3>");
        sb.append("<form  action=\"/file/create\" method=\"get\">\n" +
                "    <input type=\"text\" name=\"folder\" hidden value=\"" + folder + "\">\n" +
                "    <label>File name</label>\n" +
                "    <input type=\"text\" name=\"fileName\">\n" +
                "    <label>File extension</label>\n" +
                "    <input type=\"text\" name=\"fileExtension\">\n" +
                "    <button type=\"submit\">Create file</button>\n" +
                "</form>");

        sb.append("</body>");
        sb.append("</html>");

        resp.getWriter().write(sb.toString());
    }


    private void appendLink(StringBuilder sb, String text, Path path) {
        if (Files.isDirectory(path)) {
            sb.append("<img src =\"http://findicons.com/files/icons/2221/folder/128/normal_folder.png\" height=\"15\" width=\"15\">");
            sb.append("<a href=\"" + path.toString() + "/" + "\">" + text + "</a>");
        } else {
            sb.append("<img src =\"http://megaicons.net/static/img/icons_sizes/8/178/256/very-basic-file-icon.png\" height=\"15\" width=\"15\">");
            sb.append("<a href=\"" + path.toString() + "\">" + text + "</a>");
        }
        sb.append("<form action=\"/file/remove\" method=\"get\">\n" +
                "    <input type=\"text\" name=\"remove\" value=\"" + path.toString() + "\" hidden>\n" +
                "    <button type=\"submit\">remove</button>\n" +
                "</form>");
    }

    public List<Path> pathList(Path directory) {
        List<Path> paths = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directory)) {
            for (Path path : directoryStream) {
                paths.add(path);
            }
        } catch (IOException ex) {
            System.out.println("IOException");
        }
        Collections.sort(paths, (Path p1, Path p2) -> p1.getFileName().toString().compareTo(p2.getFileName().toString()));
        return paths;
    }
}
