package ar.unq.edu.TaskMan.Services;

import ar.unq.edu.TaskMan.Model.Prioridad;
import ar.unq.edu.TaskMan.Model.Tarea.Tarea;
import ar.unq.edu.TaskMan.Model.Tarea.TareaCompleja;
import ar.unq.edu.TaskMan.Model.Tarea.TareaSimple;
import ar.unq.edu.TaskMan.Model.Usuario;
import ar.unq.edu.TaskMan.Service.TareaService;
import ar.unq.edu.TaskMan.Service.UsuarioService;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.Date;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class TareaServiceTest {

    @Autowired
    UsuarioService USUARIO_SERVICE;
    @Autowired
    TareaService TAREA_SERVICE;

    @Before
    public void setUp(){
        Usuario usuario_1 = new Usuario(
                "leadiaz",
                "Leandro",
                "Diaz",
                "diazleandro4@gmail.com",
                "123456");
        USUARIO_SERVICE.save(usuario_1);

        Tarea tarea = new TareaSimple("Test", "Testear los servicios");
        TAREA_SERVICE.save(tarea);
    }
    @After
    public void tearDown(){
        TAREA_SERVICE.deleteAll();
        USUARIO_SERVICE.deleteAll();
    }
    @Test
    public void getAllTareas(){
        Assert.assertEquals(TAREA_SERVICE.getAll().size(), 1);
    }
    @Test
    public void asignarUsuario(){
        Usuario usuario = USUARIO_SERVICE.getByUsuarioOEmail("leadiaz").get();
        Tarea tarea = TAREA_SERVICE.getById(Long.valueOf(1)).get();
        tarea.setAsignado(usuario);
        TAREA_SERVICE.update(tarea);
        Usuario usuario_updateado = TAREA_SERVICE.getById(Long.valueOf(1)).get().getAsignado();
        Assert.assertEquals(usuario_updateado.getUsuario(),"leadiaz" );
    }
    @Test
    public void crearTareaConFechaEstimada(){
        Tarea tareaCompleja = new TareaCompleja("Tarea compleja",
                                            "Test de tarea con fecha estimada",
                                                        LocalDate.of(2020,10,13),
                                                        Prioridad.BAJA);
        TAREA_SERVICE.save(tareaCompleja);
        TareaCompleja tarea = (TareaCompleja) TAREA_SERVICE.getByTitulo("Tarea compleja").get();
        Assert.assertEquals(tarea.getFecha_estimada().getYear(), 2020);
        Assert.assertEquals(tarea.getFecha_estimada().getMonthValue(), 10);
        Assert.assertEquals(tarea.getFecha_estimada().getDayOfMonth(), 13);
    }
}
