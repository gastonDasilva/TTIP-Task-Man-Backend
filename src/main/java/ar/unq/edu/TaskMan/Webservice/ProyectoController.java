package ar.unq.edu.TaskMan.Webservice;

import ar.unq.edu.TaskMan.Model.Proyecto;
import ar.unq.edu.TaskMan.Model.Rol;
import ar.unq.edu.TaskMan.Model.Usuario;
import ar.unq.edu.TaskMan.Service.ProyectoService;
import ar.unq.edu.TaskMan.Service.RolService;
import ar.unq.edu.TaskMan.Service.TareaService;
import ar.unq.edu.TaskMan.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*",methods = {RequestMethod.GET,RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ProyectoController {
    @Autowired
    ProyectoService proyectService;

    @Autowired
    UsuarioService userService;

    @Autowired
    TareaService tareaService ;

    @Autowired
    RolService rolService;


    @RequestMapping(value = "/proyectos", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Proyecto>> getProyectos() {
        return new ResponseEntity<>(this.proyectService.getAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/proyecto/{userId}", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Proyecto> nuevoProyecto(@PathVariable("userId") long id, @RequestBody Proyecto proyecto){
        Optional<Usuario> usuarioOptional = userService.getById(id);
        if(usuarioOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
        Rol rol = new Rol("Propietario", usuarioOptional.get());
        rolService.save(rol);
        proyecto.addRol(rol);
        proyecto.setId(this.proyectService.save(proyecto));
        return new ResponseEntity<Proyecto>(proyecto, HttpStatus.OK);
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
            proyect.setRoles(proyecto.getRoles());
            System.out.println(proyect.getRoles());
            proyect.setTareas(proyecto.getTareas());
//			proyect.getRoles().stream().forEach(u -> u.actualizarProyectos(proyecto));
//			proyect.getRoles().stream().forEach(usuario -> this.userService.update(usuario));
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
            proyect.setRoles(proyecto.getRoles());
            proyect.setTareas(proyecto.getTareas());
            proyect.addRol(miembro);
            miembro.agregarProyecto(proyect);
            this.proyectService.updateProyecto(proyect);
            this.userService.update(miembro);
            return new ResponseEntity<Proyecto>(proyect, HttpStatus.OK);
        }
    }

*/
    @RequestMapping(value = "/proyectos/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Proyecto>> getProyectosDeUsuario(@PathVariable("id") Long id){
        List<Proyecto> proyectos = proyectService.getAll();
        Optional<Usuario> usuarioOptional = userService.getById(id);
        if(usuarioOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
        return new ResponseEntity<>(proyectos.stream()
                                        .filter(proyecto -> proyecto.incluyeUsuario(usuarioOptional.get()))
                                        .collect(Collectors.toCollection(()-> new ArrayList<>())), HttpStatus.OK);
    }
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

    @RequestMapping(value = "/proyecto/{id}", method = RequestMethod.DELETE, consumes = "application/json")
    public ResponseEntity<Void> eliminarProyecto(@PathVariable("id") long id){
        Optional<Proyecto> proyecto = this.proyectService.getById(id);
        if (proyecto.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Proyecto no encontrado");
        }
        else{
            this.proyectService.delete(proyecto.get().getId());
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
    }
}
