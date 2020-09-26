package ar.unq.edu.TaskMan.Repositories;

import ar.unq.edu.TaskMan.Model.Rol;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RolRepository extends CrudRepository<Rol, Integer> {
    Optional<Rol> findById(Long id);
}
