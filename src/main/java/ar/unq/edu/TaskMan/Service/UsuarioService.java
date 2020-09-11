package ar.unq.edu.TaskMan.Service;

import ar.unq.edu.TaskMan.Model.Usuario;
import ar.unq.edu.TaskMan.Repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository userDAO;


    @Transactional
    public List<Usuario> getAll(){
        return userDAO.findAll();
    }

    @Transactional
    public void setUser(Usuario user) {
        userDAO.save(user);
    }
    @Transactional
    public Usuario getById(Long id) {
        return userDAO.findById(id).get();
    }

    @Transactional
    public Optional<Usuario> getByUsername(String username) {
        return userDAO.getByUsername(username);
    }


    @Transactional
    public void update(Usuario user) {
        System.out.println("actualizando " + user.getUsuario());
        userDAO.save(user);
    }

    public Optional<Usuario> getByUsuarioOEmail(String usuarioOEmail) {
        return userDAO.getByUsuarioOEmail(usuarioOEmail);
    }
   /* @Transactional
    public List<Usuario> search(String nombre){
        return userDAO.searchUsers(nombre);
    }*/

}
