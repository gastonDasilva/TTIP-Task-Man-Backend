package ar.unq.edu.TaskMan.Services;

import ar.unq.edu.TaskMan.Model.Proyecto;
import ar.unq.edu.TaskMan.Model.Tarea.Tarea;
import ar.unq.edu.TaskMan.Model.Tarea.TareaSimple;
import ar.unq.edu.TaskMan.Service.ProyectoService;
import ar.unq.edu.TaskMan.Service.TareaService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class ProyectoServiceTest {
    @Autowired
    ProyectoService proyectoService;
    @Autowired
    TareaService tareaService;

    @Before
    public void setUp(){
        Tarea tarea = new TareaSimple("Test", "Testear los servicios");
        tareaService.save(tarea);
        Proyecto proyecto = new Proyecto();
        proyecto.setNombre("Test");
        proyecto.addTarea(tarea);
        proyectoService.save(proyecto);
    }
    @After
    public void tearDown(){
        tareaService.deleteAll();
    }

    @Test
    public void getProyecto(){
        Assert.assertEquals(proyectoService.getAll().size(), 1);
    }

    @Test
    public void deleteProyecto(){
        Proyecto proyecto = proyectoService.getAll().get(0);
        proyectoService.delete(proyecto.getId());

        proyecto.getTareas().forEach(tarea -> Assert.assertTrue(tareaService.getById(tarea.getId()).isEmpty()));
        Assert.assertTrue(proyectoService.getById(proyecto.getId()).isEmpty());
    }
}
