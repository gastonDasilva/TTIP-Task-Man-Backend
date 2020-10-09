package ar.unq.edu.TaskMan.Services;

import ar.unq.edu.TaskMan.Excepciones.UsuarioDuplicadoException;
import ar.unq.edu.TaskMan.Model.Usuario;
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
public class UsuarioServiceTest {
    @Autowired
    private UsuarioService usuarioService;

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

    }
}
