package com.ar.apiMovies;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO extends DAO {

    public void guardarUsuario(Usuario usuario) throws Exception {
        try {
            if (usuario == null) {
                throw new Exception("Debe indicar la pelicula");
            }
            String sql = "INSERT INTO usuarios (nombre, apellido, email, contrasena, fechaNacimiento, pais) "
                    + "VALUES ( '" + usuario.getNombre() + "' , '" + usuario.getApellido() + "' ,'" + usuario.getEmail()
                    + "' , '"
                    + usuario.getContrasena() + "' , '" + usuario.getFechaNacimiento() + "' , '" + usuario.getPais()
                    + "');";

            System.out.println(sql);
            insertarModificarEliminar(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            desconectarBase();
        }
    }

    public void modificarUsuario(Usuario usuario) throws Exception {
        try {
            if (usuario == null) {
                throw new Exception("Debe indicar el usuario que desea modificar");
            }
            String sql = "UPDATE usuarios SET "
                    + " nombre = '" + usuario.getNombre() + "' , apellido = '" + usuario.getApellido() + "' , email = '"
                    + usuario.getEmail() + "' , contrasena = '" + usuario.getContrasena() + "', fechaNacimiento = '"
                    + usuario.getFechaNacimiento() + "' , pais = ' " + usuario.getPais() + "' "
                    + " WHERE id = '" + usuario.getId() + "'";
            System.out.println(sql);
            insertarModificarEliminar(sql);
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public void elimiarUsuario(int id) throws Exception {
        try {
            String sql = "DELETE FROM usuarios WHERE id = " + id + "";
            insertarModificarEliminar(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            desconectarBase();
        }
    }

    public Usuario buscarUsuarioPorId(int id) throws Exception {

        try {
            String sql = "SELECT * FROM usuarios WHERE id = " + id + "";
            consultarBase(sql);
            Usuario usuario = null;
            while (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getLong(1));
                usuario.setNombre(rs.getString(2));
                usuario.setApellido(rs.getString(3));
                usuario.setEmail(rs.getString(4));
                usuario.setContrasena(rs.getString(5));
                usuario.setFechaNacimiento(rs.getDate(6));
                usuario.setPais(rs.getString(7));
            }
            desconectarBase();
            return usuario;
        } catch (Exception e) {
            desconectarBase();
            e.printStackTrace();
        }

        return null;

    }

    public List<Usuario> listarUsuarios() throws Exception {
        try {
            String sql = "SELECT * FROM usuarios";
            consultarBase(sql);
            Usuario usuario = null;
            List<Usuario> usuarios = new ArrayList<Usuario>();
            while (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getLong(1));
                usuario.setNombre(rs.getString(2));
                usuario.setApellido(rs.getString(3));
                usuario.setEmail(rs.getString(4));
                usuario.setContrasena(rs.getString(5));
                usuario.setFechaNacimiento(rs.getDate(6));
                usuario.setPais(rs.getString(7));
                usuarios.add(usuario);
            }
            desconectarBase();
            return usuarios;
        } catch (Exception e) {
            e.printStackTrace();
            desconectarBase();
        }
        return null;
    }

}
