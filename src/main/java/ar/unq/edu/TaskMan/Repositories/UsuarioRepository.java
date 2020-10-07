package ar.unq.edu.TaskMan.Repositories;

import ar.unq.edu.TaskMan.Services.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {
    Optional<Usuario> findById(Long id);

    List<Usuario> findAll();

    @Query(value = "select * from usuario where email like %?1", nativeQuery = true)
    Optional<Usuario> getByUsername(String username);

    @Query(value = "select * from usuario where usuario.email like  %?1 or usuario.usuario like %?1", nativeQuery = true)
    Optional<Usuario> getByUsuarioOEmail(String usuarioOEmail);
}
