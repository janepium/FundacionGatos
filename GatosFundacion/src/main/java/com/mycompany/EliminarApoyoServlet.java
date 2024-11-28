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
 * Servlet para eliminar un apoyo de la base de datos.
 */
@WebServlet(name = "ServletEliminarApoyo", urlPatterns = {"/eliminar-apoyo"})
public class EliminarApoyoServlet extends HttpServlet {

    // Consulta SQL para eliminar un apoyo por su ID
    private static final String query = "DELETE FROM gatosbasededatos WHERE id = ?";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener el ID del apoyo a eliminar desde el formulario
        String id = request.getParameter("id");

        // Validar que el ID no sea nulo o vacío
        if (id == null || id.trim().isEmpty()) {
            response.getWriter().println("<p class='error'>Por favor ingresa un ID válido.</p>");
            return;
        }

        try (Connection conexion = Conexion.getConnection(); 
             PreparedStatement stmt = conexion.prepareStatement(query)) {
            
            // Establecemos el ID en la consulta SQL
            stmt.setString(1, id);
            
            // Ejecutamos la eliminación
            int filasAfectadas = stmt.executeUpdate();

            // Comprobamos si se eliminó un registro
            if (filasAfectadas > 0) {
                // Si la eliminación fue exitosa
                response.getWriter().println("<p class='mensaje'>El apoyo con ID " + id + " ha sido eliminado exitosamente.</p>");
            } else {
                // Si no se encontró un apoyo con ese ID
                response.getWriter().println("<p class='error'>No se encontró el apoyo con el ID " + id + ".</p>");
            }
        } catch (SQLException ex) {
            // Manejo de excepciones
            Logger.getLogger(EliminarApoyoServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.getWriter().println("<p class='error'>Error al conectar con la base de datos. Intenta más tarde.</p>");
        }
    }
}
