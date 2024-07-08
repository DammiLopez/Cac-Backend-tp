package com.ar.apiMovies;

import java.util.List;

public class UsuarioService {

    private UsuarioDAO usuarioDao;

    public UsuarioService() {
        this.usuarioDao = new UsuarioDAO();
    }

    public void guardarUsuario(Usuario usuario) throws Exception {
        this.usuarioDao.guardarUsuario(usuario);
    }

    public void modificarUsuario(Usuario usuario) throws Exception {
        this.usuarioDao.modificarUsuario(usuario);
    }

    public void elimiarUsuario(int id) throws Exception {
        this.usuarioDao.elimiarUsuario(id);
    }

    public Usuario buscarUsuarioPorID(int id) throws Exception {
        return this.usuarioDao.buscarUsuarioPorId(id);
    }

    public List<Usuario> listarUsuarios() {
        try {
            return this.usuarioDao.listarUsuarios();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
