package ar.unq.edu.TaskMan.Services;

import ar.unq.edu.TaskMan.Service.TareaService;
import ar.unq.edu.TaskMan.Service.UsuarioService;
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

        Tarea tarea = new Tarea("Test", "Testear los servicios");
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
}
