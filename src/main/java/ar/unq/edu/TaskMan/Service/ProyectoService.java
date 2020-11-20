package ar.unq.edu.TaskMan.Service;

import ar.unq.edu.TaskMan.Constantes.ModelCTE;
import ar.unq.edu.TaskMan.Model.Proyecto;
import ar.unq.edu.TaskMan.Model.Rol;
import ar.unq.edu.TaskMan.Model.Usuario;
import ar.unq.edu.TaskMan.Repositories.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
    @Autowired
    private SendMailService sendMailService;

    @Value("${sendMailConfig:NONE}")
    private String sendMailConfig;

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

    public Proyecto agregarMiembroAUnProyecto(Proyecto proyectoUpdate, String userNameOrEmail,Integer eliminarOrAgregarUser){
        /*Me encargo de agregar un miembro a un proyecto. SI el usuario ya esta asignado como miembro -> entonces no hago nada.*/
        Proyecto proyect = this.getById(proyectoUpdate.getId()).get();
        Usuario miembro = this.usuarioService.getByUsuarioOEmail(userNameOrEmail).get();

        if(miembro != null && proyect != null ){
            eliminarOrAgregarUsuario(proyect,miembro,eliminarOrAgregarUser);
        }
        return proyect;
    }


    private void eliminarOrAgregarUsuario(Proyecto proyecto, Usuario user,Integer eliminarOrAgregarUser){
        if(eliminarOrAgregarUser.equals(1)){
            /*Agrego miembro*/
            agregarROl(proyecto,user);
        }else{
            /*Elimino miembro*/
            eliminarROl(proyecto,user);
        }
        gestionarMail(user,proyecto);
    }

    private void agregarROl(Proyecto proyecto, Usuario user){
        boolean miembroYaIncluido = proyecto.verificarMiembroAAgregar(user);
        if(!miembroYaIncluido){
            Rol roluser = new Rol(ModelCTE.TIPO_ROL_MIEMBROASIGNADO, user);
            rolService.save(roluser);
            proyecto.addRol(roluser);
            this.updateProyecto(proyecto);
            System.out.println("usuario Agregado " + user.getNombre());
        }
    }

    private void eliminarROl(Proyecto proyecto, Usuario user){
        Rol roluser = rolService.getByUserAndProyectID(proyecto.getId(),user.getId()).get();
        proyecto.deleteROl(roluser);
        this.updateProyecto(proyecto);
        rolService.delete(roluser.getId());
        System.out.println("usuario  " + user.getNombre() +" ha sido Eliminado del proyecto.");
    }

    private void gestionarMail(Usuario usuarioAEnviarMail,Proyecto proyecto){
        if(sendMailConfig.equals("true")){
            String body = "Has sido asignado como miembro del proyecto ".concat(proyecto.getNombre()).concat(". \n") ;
            String titulo = "Taskman";
            sendMailService.sendMail("taskman.app.corp@gmail.com",usuarioAEnviarMail.getEmail(),titulo,body);
        }
    }
}
