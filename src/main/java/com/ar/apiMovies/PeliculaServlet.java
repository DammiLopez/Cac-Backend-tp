package com.ar.apiMovies;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/peliculas/*")
public class PeliculaServlet extends HttpServlet {

    private PeliculaService service;

    public PeliculaServlet() {
        super();
        this.service = new PeliculaService();
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
        Gson gson = new Gson();
        String json;

        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {

            List<Pelicula> peliculas = service.listarpeliculas();

            json = gson.toJson(peliculas);
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
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de película no válido");
                return;
            }

            Pelicula p = null;
            try {
                p = service.buscarpeliculaPorId(id);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (p == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Película no encontrada");
                return;
            }

            json = gson.toJson(p);
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
            sb.append(line);
        }

        Gson gson = new Gson();
        Pelicula pelicula;
        try {
            pelicula = gson.fromJson(sb.toString(), Pelicula.class);
        } catch (JsonSyntaxException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "JSON no válido");
            return;
        }

        try {
            service.guardarPelicula(pelicula);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al guardar la película");
            return;
        }

        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(gson.toJson(pelicula));
        out.flush();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        setCorsHeaders(resp);
        String pathInfo = req.getPathInfo(); // Obtener la parte de la URL que contiene el ID

        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de película no proporcionado");
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
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de película no válido");
            return;
        }

        StringBuilder sb = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        Gson gson = new Gson();
        Pelicula pelicula;
        try {
            pelicula = gson.fromJson(sb.toString(), Pelicula.class);
        } catch (JsonSyntaxException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "JSON no válido");
            return;
        }

        pelicula.setId(id);

        try {
            service.modificarpelicula(pelicula);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al editar la película");
            return;
        }

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(gson.toJson(pelicula));
        out.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        setCorsHeaders(resp);

        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de película no proporcionado");
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
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de película no válido");
            return;
        }

        try {
            service.eliminarpelicula(id);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al borrar la película");
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