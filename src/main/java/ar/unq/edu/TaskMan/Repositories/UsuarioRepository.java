package ar.unq.edu.TaskMan.Repositories;

import ar.unq.edu.TaskMan.Model.Usuario;
import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {
    Optional<Usuario> findById(Long id);

    List<Usuario> findAll();

    @Query(value = "select * from usuario where email like %?1", nativeQuery = true)
    Optional<Usuario> getByUsername(String username);

    @Query(value = "select * from usuario where usuario.email like  %?1 or usuario.usuario like %?1", nativeQuery = true)
    Optional<Usuario> getByUsuarioOEmail(String usuarioOEmail);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Transactional
    @Query(value = "update usuario u set u.usuario = ?2, u.nombre = ?3, u.apellido = ?4, u.email = ?5, u.password = ?6 where u.id = ?1", nativeQuery = true)
    void update(Long id, String usuario, String nombre, String apellido, String email,String password);
}
