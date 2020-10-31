package ar.unq.edu.TaskMan.Models;

import ar.unq.edu.TaskMan.Model.Estado;
import ar.unq.edu.TaskMan.Model.Prioridad;
import ar.unq.edu.TaskMan.Model.Proyecto;
import ar.unq.edu.TaskMan.Model.Rol;
import ar.unq.edu.TaskMan.Model.Tarea.Tarea;
import ar.unq.edu.TaskMan.Model.Tarea.TareaCompleja;
import ar.unq.edu.TaskMan.Model.Tarea.TareaSimple;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class ProyectoModelTest {
    @Test
    public void tareasOrdenadasPrioridad(){
        Tarea tarea1 = new TareaCompleja("Tarea compleja 1",
                "primera tarea compleja",
                LocalDate.of(2020,10,13),
                Prioridad.BAJA);
        Tarea tarea2 = new TareaCompleja("Tarea compleja 2",
                "segunda tarea compleja",
                LocalDate.of(2020,10,13),
                Prioridad.MEDIO);
        Tarea tarea3 = new TareaCompleja("Tarea compleja 3",
                "tercera tarea compleja",
                LocalDate.of(2020,10,13),
                Prioridad.BAJA);

        Proyecto proyecto = new Proyecto("Test", new Rol("Tester"));
        proyecto.addTarea(tarea1);
        proyecto.addTarea(tarea2);
        proyecto.addTarea(tarea3);
        Assert.assertEquals(proyecto.getTareas().size(), 3);
        Assert.assertEquals(proyecto.getTareas().get(0).getTitulo(), "Tarea compleja 2");
        Assert.assertEquals(proyecto.getTareas().get(1).getTitulo(), "Tarea compleja 1");
        Assert.assertEquals(proyecto.getTareas().get(2).getTitulo(), "Tarea compleja 3");
    }
    @Test
    public void tareasOrdenadasEstado(){
        Tarea tarea1 = new TareaCompleja("Tarea compleja 1",
                "primera tarea compleja",
                LocalDate.of(2020,10,13),
                Prioridad.MEDIO);
        tarea1.setEstado(Estado.EN_PROCESO);
        Tarea tarea2 = new TareaCompleja("Tarea compleja 2",
                "segunda tarea compleja",
                LocalDate.of(2020,10,13),
                Prioridad.MEDIO);
        tarea2.setEstado(Estado.CRITICA);
        Tarea tarea3 = new TareaCompleja("Tarea compleja 3",
                "tercera tarea compleja",
                LocalDate.of(2020,10,13),
                Prioridad.MEDIO);
        tarea3.setEstado(Estado.CRITICA);
        Proyecto proyecto = new Proyecto("Test", new Rol("Tester"));
        proyecto.addTarea(tarea1);
        proyecto.addTarea(tarea2);
        proyecto.addTarea(tarea3);
        Assert.assertEquals(proyecto.getTareas().size(), 3);
        Assert.assertEquals(proyecto.getTareas().get(0).getTitulo(), "Tarea compleja 2");
        Assert.assertEquals(proyecto.getTareas().get(1).getTitulo(), "Tarea compleja 3");
        Assert.assertEquals(proyecto.getTareas().get(2).getTitulo(), "Tarea compleja 1");
    }
    @Test
    public void tareasOrdenadasSimplesYComplejas(){
        Tarea tarea1 = new TareaCompleja("Tarea compleja 1",
                "primera tarea compleja",
                LocalDate.of(2020,10,13),
                Prioridad.MEDIO);
        tarea1.setEstado(Estado.EN_PROCESO);
        Tarea tarea2 = new TareaSimple("Tarea simple",
                "primera tarea simple");
        tarea2.setEstado(Estado.CRITICA);
        Tarea tarea3 = new TareaCompleja("Tarea compleja 2",
                "segunda tarea compleja",
                LocalDate.of(2020,10,13),
                Prioridad.MEDIO);
        tarea3.setEstado(Estado.CRITICA);
        Proyecto proyecto = new Proyecto("Test", new Rol("Tester"));
        proyecto.addTarea(tarea1);
        proyecto.addTarea(tarea2);
        proyecto.addTarea(tarea3);
        Assert.assertEquals(proyecto.getTareas().size(), 3);
        Assert.assertEquals(proyecto.getTareas().get(0).getTitulo(), "Tarea compleja 2");
        Assert.assertEquals(proyecto.getTareas().get(1).getTitulo(), "Tarea compleja 1");
        Assert.assertEquals(proyecto.getTareas().get(2).getTitulo(), "Tarea simple");
    }
    @Test
    public void tareasOrdenadasSimples(){
        Tarea tarea1 = new TareaSimple("Tarea simple 1",
                "primera tarea simple");
        tarea1.setEstado(Estado.EN_PROCESO);
        Tarea tarea2 = new TareaSimple("Tarea simple 2",
                "segunda tarea simple");
        tarea2.setEstado(Estado.CRITICA);
        Tarea tarea3 = new TareaSimple("Tarea simple 3",
                "tercera tarea simple");
        tarea3.setEstado(Estado.CRITICA);
        Proyecto proyecto = new Proyecto("Test", new Rol("Tester"));
        proyecto.addTarea(tarea1);
        proyecto.addTarea(tarea2);
        proyecto.addTarea(tarea3);
        Assert.assertEquals(proyecto.getTareas().size(), 3);
        Assert.assertEquals(proyecto.getTareas().get(0).getTitulo(), "Tarea simple 2");
        Assert.assertEquals(proyecto.getTareas().get(1).getTitulo(), "Tarea simple 3");
        Assert.assertEquals(proyecto.getTareas().get(2).getTitulo(), "Tarea simple 1");
    }
}
