package ar.unq.edu.TaskMan.Webservice;

import ar.unq.edu.TaskMan.Model.Estado;
import ar.unq.edu.TaskMan.Model.Proyecto;
import ar.unq.edu.TaskMan.Model.Tarea.Tarea;
import ar.unq.edu.TaskMan.Model.Tarea.TareaCompleja;
import ar.unq.edu.TaskMan.Model.Tarea.TareaSimple;
import ar.unq.edu.TaskMan.Service.ProyectoService;
import ar.unq.edu.TaskMan.Service.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
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
        return new ResponseEntity<>(this.tareaService.getAsignadas(idUsuario), HttpStatus.OK);
    }
    @RequestMapping(value = "/tarea/{idProyecto}", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Tarea> create(@RequestBody Tarea tarea, @PathVariable ("idProyecto") long idProyecto) throws Exception{
        Optional<Proyecto> proyectoOptional=this.proyectoService.getById(idProyecto);
        if(proyectoOptional.isEmpty()) {
            throw  new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "No existe el proyecto");
        }
        else {
            if(tarea instanceof TareaCompleja){
                ((TareaCompleja) tarea).setFecha_creacion(LocalDate.now());
            }
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

    @RequestMapping(value = "/tarea", method = RequestMethod.PUT)
    public ResponseEntity<Tarea> updateTask(@RequestBody Tarea tarea ){
        tareaService.update(tarea);
        return new ResponseEntity<>(tarea, HttpStatus.OK);
    }

    @RequestMapping(value = "/tarea/actualizarEstado", method = RequestMethod.PUT)
    public ResponseEntity<Tarea> updateTaskEstado(@RequestBody Tarea tarea,@RequestParam String estadoStr ){
        tarea.setEstado(Estado.fromText(estadoStr));
        tareaService.update(tarea);
        return new ResponseEntity<>(tarea, HttpStatus.OK);
    }
}
