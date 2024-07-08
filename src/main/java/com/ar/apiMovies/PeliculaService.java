package com.ar.apiMovies;

import java.util.List;

public class PeliculaService {

    private PeliculaDAO peliculaDAO;

    public PeliculaService() {
        this.peliculaDAO = new PeliculaDAO();
    }

    public void guardarPelicula(Pelicula pelicula) throws Exception {
        this.peliculaDAO.guardarPelicula(pelicula);
    }

    public void modificarpelicula(Pelicula pelicula) throws Exception {
        this.peliculaDAO.modificarpelicula(pelicula);
    }

    public void eliminarpelicula(int id) throws Exception {
        this.peliculaDAO.eliminarpelicula(id);
    }

    public Pelicula buscarpeliculaPorId(int id) throws Exception {
        return this.peliculaDAO.buscarpeliculaPorId(id);
    }

    public List<Pelicula> listarpeliculas() {
        try {
            return this.peliculaDAO.listarpeliculas();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
