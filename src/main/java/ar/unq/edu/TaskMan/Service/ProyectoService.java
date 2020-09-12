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

    @Transactional
    public List<Proyecto> getAll(){
        return proyectoDAO.findAll();
    }
    @Transactional
    public long setProyecto(Proyecto proyectoNuevo){

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
        proyectoDAO.delete(this.getById(id).get());
    }


}
