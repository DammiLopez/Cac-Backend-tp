package com.ar.apiMovies;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/usuarios/*")
public class UsuarioServlet extends HttpServlet {

    private UsuarioService service;

    public UsuarioServlet() {
        super();
        this.service = new UsuarioService();
    }

    private void setCorsHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:5501");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setCorsHeaders(resp);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new SqlDateDeserializer())
                .create();
        String json;

        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {

            List<Usuario> usuarios = service.listarUsuarios();

            json = gson.toJson(usuarios);
            PrintWriter out = resp.getWriter();
            out.print(json);
            out.flush();
        } else {
            String[] pathParts = pathInfo.split("/");

            System.out.println(pathParts.length);
            if (pathParts.length != 2) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Formato de URL incorrecto");
                return;
            }

            String idStr = pathParts[1];
            int id;
            try {
                id = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID del usuario no válido");
                return;
            }

            Usuario u = null;
            try {
                u = service.buscarUsuarioPorID(id);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (u == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Usuario no encontrado");
                return;
            }

            json = gson.toJson(u);
            PrintWriter out = resp.getWriter();
            out.print(json);
            out.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        setCorsHeaders(resp);

        StringBuilder sb = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println("linea -> " + line);
            sb.append(line);
        }
        System.out.println("-----------------");
        System.out.println(sb.toString());
        System.out.println("-----------------");

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new SqlDateDeserializer())
                .create();

        Usuario usuario;
        try {
            usuario = gson.fromJson(sb.toString(), Usuario.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "JSON no válido");
            return;
        }

        try {
            service.guardarUsuario(usuario);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al guardar el usuario");
            return;
        }

        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(gson.toJson(usuario));
        out.flush();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        setCorsHeaders(resp);
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID del usuario no proporcionado");
            return;
        }

        String[] pathParts = pathInfo.split("/");
        if (pathParts.length != 2) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Formato de URL incorrecto");
            return;
        }

        String idStr = pathParts[1];
        Long id;
        try {
            id = Long.parseLong(idStr);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID del usuario no válido");
            return;
        }

        StringBuilder sb = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new SqlDateDeserializer())
                .create();
        Usuario usuario;
        try {
            usuario = gson.fromJson(sb.toString(), Usuario.class);
        } catch (JsonSyntaxException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "JSON no válido");
            return;
        }

        usuario.setId(id);

        try {
            service.modificarUsuario(usuario);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al editar el usuario");
            return;
        }

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(gson.toJson(usuario));
        out.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        setCorsHeaders(resp);

        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID el usuario no proporcionado");
            return;
        }

        String[] pathParts = pathInfo.split("/");
        if (pathParts.length != 2) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Formato de URL incorrecto");
            return;
        }

        String idStr = pathParts[1];
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID del usuario no válido");
            return;
        }

        try {
            service.elimiarUsuario(id);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al borrar el usuario");
            return;
        }

        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setCorsHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}