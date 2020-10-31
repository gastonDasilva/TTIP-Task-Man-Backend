package ar.unq.edu.TaskMan.Service;

import ar.unq.edu.TaskMan.Constantes.ModelCTE;
import ar.unq.edu.TaskMan.Model.Proyecto;
import ar.unq.edu.TaskMan.Model.Rol;
import ar.unq.edu.TaskMan.Model.Usuario;
import ar.unq.edu.TaskMan.Repositories.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProyectoService {
    @Autowired
    private ProyectoRepository proyectoDAO;
    @Autowired
    private TareaService tareaService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private RolService rolService;

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

    public Proyecto agregarMiembroAUnProyecto(Proyecto proyectoUpdate, String userNameOrEmail){
        /*Me encargo de agregar un miembro a un proyecto. SI el usuario ya esta asignado como miembro -> entonces no hago nada.*/
        Proyecto proyect = this.getById(proyectoUpdate.getId()).get();
        Usuario miembro = this.usuarioService.getByUsuarioOEmail(userNameOrEmail).get();
        boolean miembroYaIncluido = proyect.verificarMiembroAAgregar(miembro);
        if(miembro != null && proyect != null && !miembroYaIncluido){
            Rol roluser = new Rol(ModelCTE.TIPO_ROL_MIEMBROASIGNADO, miembro);
            rolService.save(roluser);
            proyect.addRol(roluser);
            this.updateProyecto(proyect);
            System.out.println("usuario Agregado " + miembro.getNombre());
        }
        return proyect;
    }

//    public List<Proyecto> getProyectosDeUsuario(Long id) {
//        return proyectoDAO.findAllByUserID(id);
//    }
}
