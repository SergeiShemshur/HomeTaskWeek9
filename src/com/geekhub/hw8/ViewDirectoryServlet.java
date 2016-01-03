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
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        Path path;
        String pathParameter = req.getParameter("path");
        if (pathParameter != null) {
            path = Paths.get(pathParameter);
        } else path = ROOT_PATH;


        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<head>");
        sb.append("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css\" integrity=\"sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7\" crossorigin=\"anonymous\">");
        sb.append("</head>");
        sb.append("<body>");
        sb.append("<div class =\"jumbotron\">");
        sb.append("<h3> Create file :</h3>");

        sb.append("<form  action=\"/file/create\" method=\"get\">\n" +
                "    <input type=\"text\" name=\"folder\" hidden value=\"" + path + "\">\n" +
                "    <label>File name</label>\n" +
                "    <input type=\"text\" name=\"fileName\">\n" +
                "    <label>File extension</label>\n" +
                "    <input type=\"text\" name=\"fileExtension\">\n" +
                "    <button type=\"submit\">Create file</button>\n" +
                "</form>");
        sb.append("</div>");


        sb.append("<div class=\"container\">");
        sb.append("<div class =\"row\">");
        sb.append("<div class = \"col-lg-8\">");
        sb.append("</div>");
        sb.append("<table class=\"table table-striped\">" +
                " <thead> " +
                "<tr>" +
                " <th>#</th>" +
                " <th>File name</th> " +
                "<th>Type</th> " +
                "<th>Remove</th>" +
                "</tr> " +
                "</thead>");

        sb.append("<tbody>");
        int rowNumber = 0;
        for (Path pathToFile : pathList(path)) {
            appendRow(sb, path.getFileName().toString(), pathToFile, ++rowNumber);
        }
        sb.append("</tbody>");
        sb.append("</table>");
        sb.append("</div>");
        sb.append("</div>");

        sb.append(" <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js\"></script>");
        sb.append("<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js\" integrity=\"sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS\" crossorigin=\"anonymous\"></script>");
        sb.append("</body>");
        sb.append("</html>");

        resp.getWriter().write(sb.toString());
    }

    private void appendRow(StringBuilder sb, String text, Path path, int rowNumber) {
        sb.append("<tr>");

        sb.append("<td>");
        sb.append(rowNumber);
        sb.append("</td>");

        if (Files.isDirectory(path)) {
            sb.append("<td>");
            sb.append("<a href=\"" + "/dir/view" + "?path=" + path.toString() + "\">" + path.getFileName().toString() + "</a>");
            sb.append("</td>");

            sb.append("<td>");
            sb.append("<img src =\"http://findicons.com/files/icons/2221/folder/128/normal_folder.png\" height=\"15\" width=\"15\">");
            sb.append("</td>");
        } else {
            sb.append("<td>");
            sb.append("<a href=\"" + "/file/view" + "?path=" + path.toString() + "\">" + path.getFileName().toString() + "</a>");
            sb.append("</td>");

            sb.append("<td>");
            sb.append("<img src =\"http://megaicons.net/static/img/icons_sizes/8/178/256/very-basic-file-icon.png\" height=\"15\" width=\"15\">");
            sb.append("</td>");
        }

        sb.append("<td>");
        sb.append("<form action=\"/file/remove\" method=\"get\">\n" +
                "    <input type=\"text\" name=\"remove\" value=\"" + path.toString() + "/" + "\" hidden>\n" +
                "    <button type=\"submit\">remove</button>\n" +
                "</form>");
        sb.append("</td>");

        sb.append("</tr>");

    }


    public List<Path> pathList(Path directory) {
        List<Path> paths = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directory)) {
            for (Path path : directoryStream) {
                paths.add(path);
            }
        } catch (IOException ex) {
            System.out.println(" IOException");
        }
        Collections.sort(paths, (Path p1, Path p2) -> p1.getFileName().toString().compareTo(p2.getFileName().toString()));
        return paths;
    }
}
