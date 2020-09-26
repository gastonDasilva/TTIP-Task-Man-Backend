package ar.unq.edu.TaskMan.Webservice;

import ar.unq.edu.TaskMan.Model.Proyecto;
import ar.unq.edu.TaskMan.Model.Rol;
import ar.unq.edu.TaskMan.Repositories.RolRepository;
import ar.unq.edu.TaskMan.Service.ProyectoService;
import ar.unq.edu.TaskMan.Service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

public class RolController {
    @Autowired
    RolService rolService;
    @Autowired
    ProyectoService proyectoService;
    @RequestMapping(value = "/proyectos", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Rol>> getProyectos() {
        return new ResponseEntity<>(this.rolService.getAll(), HttpStatus.OK);
    }
    @RequestMapping(value = "/rol/{idProy}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Proyecto> create (@PathVariable("idProy") long id, @RequestBody Rol rol){
        Optional<Proyecto> proyectoOptional = this.proyectoService.getById(id);
        if(proyectoOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Proyecto no encontrado");
        }
        this.rolService.save(rol);
        proyectoOptional.get().addRol(rol);
        this.proyectoService.updateProyecto(proyectoOptional.get());
        return new ResponseEntity<>(proyectoOptional.get(), HttpStatus.OK);
    }
}
