package ar.unq.edu.TaskMan.Repositories;

import ar.unq.edu.TaskMan.Services.Tarea;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TareaRepository extends CrudRepository<Tarea, Integer> {
    Optional<Tarea> findById(Long id);

    List<Tarea> findAll();
    @Query(value = "select * from tarea where tarea.asignado_id = ?1", nativeQuery = true)
    Optional<List<Tarea>>getAsignadas(Long id);
}
