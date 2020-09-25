package ar.unq.edu.TaskMan.Webservice;

import ar.unq.edu.TaskMan.Model.Estado;
import ar.unq.edu.TaskMan.Model.Proyecto;
import ar.unq.edu.TaskMan.Model.Tarea;
import ar.unq.edu.TaskMan.Service.ProyectoService;
import ar.unq.edu.TaskMan.Service.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*",methods = {RequestMethod.GET,RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class TareaController {

    @Autowired
    TareaService tareaService;
    @Autowired
    ProyectoService proyectoService;

    @RequestMapping(value = "/tareas", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Tarea>> getTareas() {
        List<Tarea> tareas = this.tareaService.getAll();
        if (tareas == null) {
            return new ResponseEntity<List<Tarea>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Tarea>>(tareas, HttpStatus.OK);
    }

    @RequestMapping(value = "/tarea/{id}", method = RequestMethod.GET)
    public ResponseEntity<Tarea> getTarea(@PathVariable("id") Long id) {
        Optional<Tarea> tarea= this.tareaService.getById(id);
        if (tarea.isEmpty()) {
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarea no encontrada");
        }
        return new ResponseEntity<>(tarea.get(), HttpStatus.OK);
    }
    @RequestMapping(value = "/tareas/{idUsuario}", method = RequestMethod.GET)
    public ResponseEntity<List<Tarea>> getAllUsuario(@PathVariable ("idUsuario") Long idUsuario){
        Optional<List<Tarea>> tareas = this.tareaService.getAsignadas(idUsuario);
        if(tareas.isEmpty()){
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
        return new ResponseEntity<>(tareas.get(), HttpStatus.OK);
    }
    @RequestMapping(value = "/tarea/{idProyecto}", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Tarea> create(@RequestBody Tarea tarea, @PathVariable ("idProyecto") long idProyecto) throws Exception{
        Optional<Proyecto> proyectoOptional=this.proyectoService.getById(idProyecto);
        if(proyectoOptional.isEmpty()) {
            throw  new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "No existe el proyecto");
        }
        else {
            tarea.setEstado(Estado.CREADA);
            this.tareaService.save(tarea);
            proyectoOptional.get().addTarea(tarea);
            this.proyectoService.updateProyecto(proyectoOptional.get());
            return new ResponseEntity<>(tarea,HttpStatus.OK);
        }
    }
    @RequestMapping(value = "/tarea/{idProyecto}/{id}", method = RequestMethod.DELETE, consumes = "application/json")
    public ResponseEntity<Tarea> deleteTask(@PathVariable("id") long id,@PathVariable("idProyecto") long idProy) {
        Optional<Tarea> task = tareaService.getById(id);
        Optional<Proyecto> proyecto = proyectoService.getById(idProy);
        if (task.isEmpty() || proyecto.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        proyecto.get().eliminarTarea(task.get());
        proyectoService.updateProyecto(proyecto.get());
        this.tareaService.delete(task.get().getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/tarea/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Tarea> updateTask(@PathVariable("id") long id, @RequestBody Tarea task ){
        Optional<Tarea> tarea = tareaService.getById(id);

        if (tarea.isEmpty()) {
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarea no encontrada");
        }
//        tarea.setTitulo(task.getTitulo());
//       // tarea.setAsignado(task.getAsignado());
//        tarea.setDescripcion(task.getDescripcion());
//        tarea.setEstado(task.getEstado());
//        tarea.setFecha_creacion(task.getFecha_creacion());
//        tarea.setFecha_estimada(task.getFecha_estimada());
//        tarea.setPrioridad(task.getPrioridad());

        tareaService.update(tarea.get());
        return new ResponseEntity<>(tarea.get(), HttpStatus.OK);
    }
}
