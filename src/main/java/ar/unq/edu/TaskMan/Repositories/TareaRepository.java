package ar.unq.edu.TaskMan.Repositories;

import ar.unq.edu.TaskMan.Model.Tarea;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TareaRepository extends CrudRepository<Tarea, Integer> {
    Optional<Tarea> findById(Long id);

    List<Tarea> findAll();

}
