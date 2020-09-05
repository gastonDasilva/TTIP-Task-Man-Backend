package ar.unq.edu.TaskMan.Webservice;

import ar.unq.edu.TaskMan.Model.Proyecto;
import ar.unq.edu.TaskMan.Model.Tarea;
import ar.unq.edu.TaskMan.Service.ProyectoService;
import ar.unq.edu.TaskMan.Service.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*",methods = {RequestMethod.GET,RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class TareaController {

    @Autowired
    TareaService service ;
    @Autowired
    ProyectoService proyectoService;

    @RequestMapping(value = "/tareas", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Tarea>> getTareas() {
        List<Tarea> tareas = this.service.getAll();
        if (tareas == null) {
            return new ResponseEntity<List<Tarea>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Tarea>>(tareas, HttpStatus.OK);
    }

    @RequestMapping(value = "/tarea/{id}", method = RequestMethod.GET)
    public ResponseEntity<Tarea> getTarea(@PathVariable("id") Long id) {
        Tarea tarea= this.service.getById(id);
        if (tarea == null) {
            return new ResponseEntity<Tarea>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Tarea>(tarea, HttpStatus.OK);
    }
    /*@RequestMapping(value = "/tareas/{idUsuario}", method = RequestMethod.GET)
    public ResponseEntity<List<Tarea>> getAllUsuario(@PathVariable ("idUsuario") Long idUsuario){
        List<Tarea> tareas = this.service.getAsignadas(idUsuario);
        if(tareas.isEmpty()){
            return new ResponseEntity<List<Tarea>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Tarea>>(tareas, HttpStatus.OK);
    }*/
   /* @RequestMapping(value = "/tarea/{idProyecto}", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Tarea> create(@RequestBody Tarea input, @PathVariable ("idProyecto") long idProyecto) throws Exception{
        Proyecto proyecto=this.proyectoService.getById(idProyecto);
        if(proyecto == null) {
            return new ResponseEntity<Tarea>(HttpStatus.NOT_ACCEPTABLE);
        }
        else {
            Tarea tarea = new Tarea(input.getTitulo(),input.getDescripcion());
            this.service.save(tarea);

            proyecto.addTarea(tarea);
            this.proyectoService.updateProyecto(proyecto);
            return new ResponseEntity<Tarea>(tarea,HttpStatus.OK);

        }
    }
    @RequestMapping(value = "/tarea/{id}/{idProyecto}", method = RequestMethod.DELETE, consumes = "application/json")
    public ResponseEntity<Tarea> deleteTask(@PathVariable("id") long id,@PathVariable("idProyecto") long idProy) {
        System.out.println("Fetching & Deleting Issue with id " + id);

        Tarea task = service.getById(id);
        Proyecto proyecto = proyectoService.getById(idProy);
        if (task == null || proyecto == null) {
            System.out.println("Unable to delete. Task with id " + id + " not found");
            return new ResponseEntity<Tarea>(HttpStatus.NOT_FOUND);
        }

        proyecto.eliminarTarea(task);
        proyectoService.updateProyecto(proyecto);
        this.service.delete(task.getId());

        return new ResponseEntity<Tarea>(HttpStatus.OK);
    }

    @RequestMapping(value = "/tarea/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Tarea> updateTask(@PathVariable("id") long id, @RequestBody Tarea task ){
        Tarea tarea = service.getById(id);

        if (tarea==null) {
            return new ResponseEntity<Tarea>(HttpStatus.NOT_FOUND);
        }
        tarea.setTitulo(task.getTitulo());
       // tarea.setAsignado(task.getAsignado());
        tarea.setDescripcion(task.getDescripcion());
        tarea.setEstado(task.getEstado());
        tarea.setFecha_creacion(task.getFecha_creacion());
        tarea.setFecha_estimada(task.getFecha_estimada());
        tarea.setPrioridad(task.getPrioridad());

        service.update(tarea);
        return new ResponseEntity<Tarea>(tarea, HttpStatus.OK);
    }*/
}
