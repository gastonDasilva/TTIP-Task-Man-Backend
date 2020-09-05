package ar.unq.edu.TaskMan.Repositories;

import ar.unq.edu.TaskMan.Model.Proyecto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.util.pattern.PathPatternRouteMatcher;

import java.util.List;
import java.util.Optional;

public interface ProyectoRepository extends CrudRepository<Proyecto, Integer> {
    Optional<Proyecto> findById(Long id);

    List<Proyecto> findAll();
}
