/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet para buscar un registro por ID.
 */
@WebServlet(name = "ServletBuscarApoyo", urlPatterns = {"/buscar"})
public class ServletBuscar extends HttpServlet {

    private static final String QUERY_SELECT = "SELECT * FROM gatosbasededatos WHERE id = ?";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        
        if (id == null || id.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID no proporcionado");
            return;
        }

        try (Connection conexion = Conexion.getConnection();
             PreparedStatement stmt = conexion.prepareStatement(QUERY_SELECT)) {
            
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String correo = rs.getString("correo");
                    String celular = rs.getString("celular");
                    String apoyo = rs.getString("apoyo");
                    String mensaje = rs.getString("mensaje");

                    response.setContentType("application/json");
                    PrintWriter out = response.getWriter();
                    out.println("{");
                    out.println("\"id\": \"" + id + "\",");
                    out.println("\"nombre\": \"" + nombre + "\",");
                    out.println("\"correo\": \"" + correo + "\",");
                    out.println("\"celular\": \"" + celular + "\",");
                    out.println("\"apoyo\": \"" + apoyo + "\",");
                    out.println("\"mensaje\": \"" + mensaje + "\"");
                    out.println("}");
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "ID no encontrado");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServletBuscar.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al consultar la base de datos");
        }
    }
}
