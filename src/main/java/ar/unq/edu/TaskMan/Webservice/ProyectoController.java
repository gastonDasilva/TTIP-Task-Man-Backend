package ar.unq.edu.TaskMan.Webservice;

import ar.unq.edu.TaskMan.Model.Proyecto;
import ar.unq.edu.TaskMan.Model.Usuario;
import ar.unq.edu.TaskMan.Service.ProyectoService;
import ar.unq.edu.TaskMan.Service.TareaService;
import ar.unq.edu.TaskMan.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*",methods = {RequestMethod.GET,RequestMethod.POST, RequestMethod.PUT})
public class ProyectoController {
    @Autowired
    ProyectoService proyectService;

    @Autowired
    UsuarioService userService;

    @Autowired
    TareaService tareaService ;


    @RequestMapping(value = "/proyectos", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Proyecto>> getProyectos() {

        List<Proyecto> lproyect = this.proyectService.getAll();
        lproyect.stream().forEach((p)->{
            System.out.println(p.getNombre());
            //System.out.println(p.getCreador());
           // System.out.println(p.getMiembros());
        });
        return new ResponseEntity<List<Proyecto>>(lproyect, HttpStatus.OK);
    }

    @RequestMapping(value = "/proyecto/{userId}", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Proyecto> nuevoProyecto(@PathVariable("userId") long id, @RequestBody Proyecto proyecto){
        Usuario user = userService.getById(id);
        if(user == null) {
            return new ResponseEntity<Proyecto>(HttpStatus.NOT_FOUND);
        }
        else {
            proyecto.setCreador(user);
//            proyecto.addMiembro(user);
            user.agregarProyecto(proyecto);
            long idP=this.proyectService.setProyecto(proyecto);
            this.userService.update(user);
            proyecto.setId(idP);
            return new ResponseEntity<Proyecto>(proyecto, HttpStatus.OK);
        }
    }

/*
    @RequestMapping(value = "/user/proyecto/{id}", method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity<Proyecto> actualizarProyecto(@PathVariable("id") long id,@RequestBody Proyecto proyecto){
        System.out.println("peticion de put");
        Proyecto proyect = proyectService.getById(id);
        if(proyect == null) {
            System.out.println("no se pudo actualizar");
            return new ResponseEntity<Proyecto>(HttpStatus.NOT_FOUND);
        }else {

            proyect.setNombre(proyecto.getNombre());
            proyect.setMiembros(proyecto.getMiembros());
            System.out.println(proyect.getMiembros());
            proyect.setTareas(proyecto.getTareas());
//			proyect.getMiembros().stream().forEach(u -> u.actualizarProyectos(proyecto));
//			proyect.getMiembros().stream().forEach(usuario -> this.userService.update(usuario));
            this.proyectService.updateProyecto(proyect);
            return new ResponseEntity<Proyecto>(proyect, HttpStatus.OK);
        }

    }
/*
    @RequestMapping(value = "/proyecto/add={id}", method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity<Proyecto> agregarMiembro(@PathVariable("id") long id, @RequestBody Proyecto proyecto){

        Proyecto proyect = this.proyectService.getById(proyecto.getId());
        Usuario miembro = this.userService.getById(id);
        System.out.println("usuario Agregado " + miembro.getNombre());
        if(proyect == null) {
            return new ResponseEntity<Proyecto>(HttpStatus.NOT_FOUND);
        }else {
            proyect.setNombre(proyecto.getNombre());
            proyect.setMiembros(proyecto.getMiembros());
            proyect.setTareas(proyecto.getTareas());
            proyect.addMiembro(miembro);
            miembro.agregarProyecto(proyect);
            this.proyectService.updateProyecto(proyect);
            this.userService.update(miembro);
            return new ResponseEntity<Proyecto>(proyect, HttpStatus.OK);
        }
    }

*/
    @RequestMapping(value = "/proyecto/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Proyecto> getProyecto(@PathVariable("id") long id){
        Optional<Proyecto> proyect=this.proyectService.getById(id);
        if(proyect.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
        else {
            return new ResponseEntity<>(proyect.get(),HttpStatus.OK);

        }
    }

    /*@RequestMapping(value = "/proyecto/{id}", method = RequestMethod.DELETE, consumes = "application/json")
    public ResponseEntity<Void> eliminarProyecto(@PathVariable("id") long id){
        Proyecto proyecto = this.proyectService.getById(id);
        if (proyecto == null){
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        else{
            proyecto.getTareas().forEach(tarea -> this.tareaService.delete(tarea.getId()));
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
    }*/
}
