package com.robertob.p1ipc2.servlet;

import com.robertob.p1ipc2.database.FileInserter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;

@WebServlet("/inputFile")
@MultipartConfig
public class InputFileServlet extends HttpServlet {

    private FileInserter fileInserter;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Connection dbConnection = (Connection) session.getAttribute("dbConnection");
        Part filePart = request.getPart("archivoEntrada");
        String inputFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String content = null;

        guardarArchivo(filePart, inputFileName);

        try {
            content = Files.readString(Paths.get("/tmp/" + inputFileName));
            fileInserter = new FileInserter(content, dbConnection);
        } catch (IOException e) {
            e.printStackTrace();
        }

        request.setAttribute("fileOutput", content);
        request.getRequestDispatcher("fileOutput.jsp").forward(request,response);

    }

    private void guardarArchivo(Part filePart, String nombreArchivo) {
        File ruta = new File("/tmp");
        File file = new File(ruta, nombreArchivo);

        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Archivo " + nombreArchivo + " guardado");
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
