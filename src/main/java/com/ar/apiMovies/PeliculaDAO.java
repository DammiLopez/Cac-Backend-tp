package com.ar.apiMovies;

import java.util.ArrayList;
import java.util.List;

public class PeliculaDAO extends DAO {

    public void guardarPelicula(Pelicula pelicula) throws Exception {
        try {
            if (pelicula == null) {
                throw new Exception("Debe indicar el pelicula");
            }
            String sql = "INSERT INTO peliculas (titulo, genero, duracion, imagen) "
                    + "VALUES ( '" + pelicula.getTitulo() + "' , '" + pelicula.getGenero() + "' ,"
                    + pelicula.getDuracion() + " , '" + pelicula.getImagen() + "' );";

            System.out.println(sql);
            insertarModificarEliminar(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            desconectarBase();
        }
    }

    public void modificarpelicula(Pelicula pelicula) throws Exception {
        try {
            if (pelicula == null) {
                throw new Exception("Debe indicar el pelicula que desea modificar");
            }
            String sql = "UPDATE peliculas SET "
                    + " titulo = '" + pelicula.getTitulo() + "' , genero = '" + pelicula.getGenero() + "' , duracion = "
                    + pelicula.getDuracion() + " , imagen = '" + pelicula.getImagen()
                    + "' WHERE id = '" + pelicula.getId() + "'";
            System.out.println(sql);
            insertarModificarEliminar(sql);
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public void eliminarpelicula(int id) throws Exception {
        try {
            String sql = "DELETE FROM peliculas WHERE id = " + id + "";
            insertarModificarEliminar(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            desconectarBase();
        }
    }

    public Pelicula buscarpeliculaPorId(int id) throws Exception {

        try {
            String sql = "SELECT * FROM peliculas WHERE id = " + id + "";
            consultarBase(sql);
            Pelicula pelicula = null;
            while (rs.next()) {
                pelicula = new Pelicula();
                pelicula.setId(rs.getLong(1));
                pelicula.setTitulo(rs.getString(2));
                pelicula.setGenero(rs.getString(3));
                pelicula.setDuracion(rs.getInt(4));
                pelicula.setImagen(rs.getString(5));
            }
            desconectarBase();
            return pelicula;
        } catch (Exception e) {
            desconectarBase();
            e.printStackTrace();
        }

        return null;

    }

    public List<Pelicula> listarpeliculas() throws Exception {
        try {
            String sql = "SELECT * FROM peliculas";
            consultarBase(sql);
            Pelicula pelicula = null;
            List<Pelicula> peliculas = new ArrayList<Pelicula>();
            while (rs.next()) {
                pelicula = new Pelicula();
                pelicula.setId(rs.getLong(1));
                pelicula.setTitulo(rs.getString(2));
                pelicula.setGenero(rs.getString(3));
                pelicula.setDuracion(rs.getInt(4));
                pelicula.setImagen(rs.getString(5));
                peliculas.add(pelicula);
            }
            desconectarBase();
            return peliculas;
        } catch (Exception e) {
            e.printStackTrace();
            desconectarBase();
        }
        return null;
    }

}
