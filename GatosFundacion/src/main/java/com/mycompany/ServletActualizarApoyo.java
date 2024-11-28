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

@WebServlet(name = "ServletActualizar", urlPatterns = {"/actualizar"})
public class ServletActualizarApoyo extends HttpServlet {

    private static final String QUERY_SELECT = "SELECT * FROM gatosbasededatos WHERE id = ?";
    private static final String QUERY_UPDATE = "UPDATE gatosbasededatos SET nombre = ?, correo = ?, celular = ?, apoyo = ?, mensaje = ? WHERE id = ?";

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

                    // Configuramos la respuesta como JSON
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
            Logger.getLogger(ServletActualizarApoyo.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al consultar la base de datos");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String nombre = request.getParameter("nombre");
        String correo = request.getParameter("correo");
        String celular = request.getParameter("celular");
        String apoyo = request.getParameter("apoyo");
        String mensaje = request.getParameter("mensaje");

        // Verificación de parámetros
        if (id == null || id.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID no proporcionado");
            return;
        }
        if (nombre == null || nombre.trim().isEmpty() || correo == null || correo.trim().isEmpty() || celular == null || celular.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Faltan campos requeridos");
            return;
        }

        try (Connection conexion = Conexion.getConnection();
             PreparedStatement stmt = conexion.prepareStatement(QUERY_UPDATE)) {
            
            // Establecer parámetros de la actualización
            stmt.setString(1, nombre);
            stmt.setString(2, correo);
            stmt.setString(3, celular);
            stmt.setString(4, apoyo);
            stmt.setString(5, mensaje);
            stmt.setString(6, id);

            // Ejecutar actualización
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                response.sendRedirect("actualizar.html?success=true");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "ID no encontrado para actualizar");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServletActualizarApoyo.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al actualizar la base de datos");
        }
    }
}
