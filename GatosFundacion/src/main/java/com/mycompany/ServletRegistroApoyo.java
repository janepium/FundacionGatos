/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Valentina
 */
@WebServlet(name = "ServletRegistroApoyo", urlPatterns = {"/registro-apoyo"})
public class ServletRegistroApoyo extends HttpServlet {

    private static final String query = "INSERT INTO gatosbasededatos (id, nombre, correo, celular, apoyo, mensaje) VALUES (?, ?, ?, ?, ?, ?)";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String nombre = request.getParameter("nombre");
        String correo = request.getParameter("correo");
        String celular = request.getParameter("celular");
        String apoyo = request.getParameter("apoyo");
        String mensaje = request.getParameter("mensaje");

        System.out.println(id);
        System.out.println(celular);
        try (Connection conexion = Conexion.getConnection(); PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setString(1, id);
            stmt.setString(2, nombre);
            stmt.setString(3, correo);
            stmt.setString(4, celular);
            stmt.setString(5, apoyo);
            stmt.setString(6, mensaje); // Insertamos el mensaje
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ServletRegistroApoyo.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.sendRedirect("index.html");
    }

}
