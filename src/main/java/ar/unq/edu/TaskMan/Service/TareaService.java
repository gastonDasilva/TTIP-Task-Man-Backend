package ar.unq.edu.TaskMan.Service;

import ar.unq.edu.TaskMan.Model.Tarea.Tarea;
import ar.unq.edu.TaskMan.Repositories.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TareaService {
    @Autowired
    private TareaRepository<Tarea> tareaDao;

    @Transactional
    public void save(Tarea task) {
        tareaDao.save( task);
    }
    @Transactional
    public void update(Tarea task) {
        tareaDao.save(task);
    }
    @Transactional
    public void delete(Long id) {
        tareaDao.delete(this.getById(id).get());
    }

    @Transactional
    public Optional<Tarea> getById(Long id) {
        return tareaDao.findById(id);
    }

    @Transactional
    public List<Tarea> getAll() {
        return (List<Tarea>) tareaDao.findAll();
    }

    @Transactional
    public Optional<List<Tarea>> getAsignadas(Long id){ return tareaDao.getAsignadas(id);}
    @Transactional
    public void deleteAll() {
        tareaDao.deleteAll();
    }
    @Transactional
    public Optional<Tarea> getByTitulo(String titulo){
        return tareaDao.getByTitulo(titulo);
    }
}
