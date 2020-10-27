package ar.unq.edu.TaskMan.Service;

import ar.unq.edu.TaskMan.Model.Proyecto;
import ar.unq.edu.TaskMan.Repositories.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProyectoService {
    @Autowired
    private ProyectoRepository proyectoDAO;
    @Autowired
    private TareaService tareaService;

    @Transactional
    public List<Proyecto> getAll(){
        return proyectoDAO.findAll();
    }
    @Transactional
    public long save(Proyecto proyectoNuevo){

        return (proyectoDAO.save(proyectoNuevo)).getId();
    }

    @Transactional
    public void updateProyecto(Proyecto proyect) {
        proyectoDAO.save(proyect);

    }

    public Optional<Proyecto> getById(long id) {
        return proyectoDAO.findById(id);
    }

    @Transactional
    public void delete(Long id) {
        Proyecto proyecto = this.getById(id).get();
        proyecto.getTareas().forEach(tarea -> tareaService.delete(tarea.getId()));
        proyectoDAO.delete(proyecto);
    }


//    public List<Proyecto> getProyectosDeUsuario(Long id) {
//        return proyectoDAO.findAllByUserID(id);
//    }
}
