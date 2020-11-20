package ar.unq.edu.TaskMan.Repositories;

import ar.unq.edu.TaskMan.Model.Rol;
import ar.unq.edu.TaskMan.Model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RolRepository extends CrudRepository<Rol, Integer> {
    Optional<Rol> findById(Long id);


    @Query(value = "select  * from rol where  rol.rols = ?1 and  rol.usuario_asignado_id = ?2", nativeQuery = true)
    Optional<Rol> getRolIdByProyectAndUserID(Long idProyect,Long idUser);
}
