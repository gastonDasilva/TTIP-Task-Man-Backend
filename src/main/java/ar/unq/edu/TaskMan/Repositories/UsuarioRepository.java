package ar.unq.edu.TaskMan.Repositories;

import ar.unq.edu.TaskMan.Model.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {
    Optional<Usuario> findById(Long id);

    List<Usuario> findAll();
}
