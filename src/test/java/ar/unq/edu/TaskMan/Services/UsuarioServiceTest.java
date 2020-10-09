package ar.unq.edu.TaskMan.Services;

import ar.unq.edu.TaskMan.Excepciones.UsuarioDuplicadoException;
import ar.unq.edu.TaskMan.Model.Prioridad;
import ar.unq.edu.TaskMan.Model.Tarea.Tarea;
import ar.unq.edu.TaskMan.Model.Tarea.TareaCompleja;
import ar.unq.edu.TaskMan.Model.Tarea.TareaSimple;
import ar.unq.edu.TaskMan.Model.Usuario;
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

import java.time.LocalDate;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class UsuarioServiceTest {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private TareaService tareaService;

    @Before
    public void setUp(){
        Usuario usuario_1 = new Usuario(
                "leadiaz",
                "Leandro",
                "Diaz",
                "diazleandro4@gmail.com",
                "123456");
        usuarioService.save(usuario_1);
        Usuario usuario_2 = new Usuario(
                "ldiaz",
                "Ezequiel",
                "Diaz",
                "leadiaz94@gmail.com",
                "123456");
        usuarioService.save(usuario_2);
    }
    @After
    public void tearDown(){
        usuarioService.deleteAll();
    }
    @Test
    public void getAllUsers(){
        Assert.assertEquals(usuarioService.getAll().size(), 2);
    }
    @Test
    public void saveUser(){
        Usuario usuarioTest = new Usuario(
                "prueba",
                "LeandroTest",
                "DiazTest",
                "diazleandro4Test@gmail.com",
                "123456");
        usuarioService.save(usuarioTest);
        Assert.assertEquals(usuarioService.getAll().size(), 3);
    }
    @Test(expected = UsuarioDuplicadoException.class)
    public void noPuedeCrearUsuario(){
        Usuario usuario_2 = new Usuario(
                "ldiaz",
                "Ezequiel",
                "Diaz",
                "leadiaz94@gmail.com",
                "123456");
        usuarioService.save(usuario_2);
    }
    @Test
    public void getTareasAsignadas(){
        Usuario usuario = usuarioService.getByUsuarioOEmail("leadiaz").get();
        Tarea tarea = new TareaSimple("Test", "Testear los servicios");
        tareaService.save(tarea);
        tarea.setAsignado(usuario);
        tareaService.update(tarea);
        Tarea tareaCompleja = new TareaCompleja("Tarea compleja",
                "Test de tarea con fecha estimada",
                LocalDate.of(2020,10,13),
                Prioridad.BAJA);
        tareaService.save(tareaCompleja);
        tareaCompleja.setAsignado(usuario);
        tareaService.update(tareaCompleja);

        Assert.assertEquals(tareaService.getAsignadas(usuario.getId()).size(), 2 );
    }
}
