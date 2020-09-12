package ar.unq.edu.TaskMan.Service;

import ar.unq.edu.TaskMan.Model.Tarea;
import ar.unq.edu.TaskMan.Repositories.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TareaService {
    @Autowired
    private TareaRepository tareaDao;

    @Transactional
    public void save(Tarea task) {
        tareaDao.save(task);
    }

    @Transactional
    public void update(Tarea task) {
        tareaDao.save(task);
    }

    @Transactional
    public void delete(Long id) {
        tareaDao.delete(this.getById(id));
    }

    @Transactional
    public Tarea getById(Long id) {
        return tareaDao.findById(id).get();
    }

    @Transactional
    public List<Tarea> getAll() {
        return tareaDao.findAll();
    }

    @Transactional
    public Optional<List<Tarea>> getAsignadas(Long id){ return tareaDao.getAsignadas(id);}
}
