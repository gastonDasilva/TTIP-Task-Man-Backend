package ar.unq.edu.TaskMan.Controllers;

import ar.unq.edu.TaskMan.Model.Proyecto;
import ar.unq.edu.TaskMan.Model.Tarea.Tarea;
import ar.unq.edu.TaskMan.Model.Tarea.TareaSimple;
import ar.unq.edu.TaskMan.Service.ProyectoService;
import ar.unq.edu.TaskMan.Service.RolService;
import ar.unq.edu.TaskMan.Service.TareaService;
import ar.unq.edu.TaskMan.Service.UsuarioService;
import ar.unq.edu.TaskMan.Webservice.ProyectoController;
import ar.unq.edu.TaskMan.Webservice.TareaController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(TareaController.class)
public class TareaControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private ProyectoService proyectoService;
    @MockBean
    private TareaService tareaService;


    @Test
    public void testGetAllTareas() throws Exception {
        Tarea tarea = new TareaSimple("Test", "Una tarea de prueba");
        List<Tarea> tareas = Arrays.asList(tarea);
        when(tareaService.getAll()).thenReturn(tareas);
        mvc.perform(get("/tareas").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void testGetTarea() throws Exception {
        Tarea tarea = new TareaSimple("Test", "Una tarea de prueba");
        when(tareaService.getById(Long.valueOf(1))).thenReturn(Optional.of(tarea));
        mvc.perform(get("/tarea/{id}", 1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }
    @Test
    public void testAsignadas() throws Exception{
        Tarea tarea = new TareaSimple("Test", "Una tarea de prueba");
        Tarea asignada = new TareaSimple("asignada", "Una tarea de prueba");
        List<Tarea> tareas = Arrays.asList(tarea, asignada);
        when(tareaService.getAsignadas(Long.valueOf(1))).thenReturn(tareas);
        mvc.perform(get("/tareas/{idUsuario}", 1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

    }
    @Test
    public void testCrearTarea() throws Exception{
        Tarea tarea = new TareaSimple("Test", "Una tarea de prueba");
        Proyecto proyecto = new Proyecto();
        proyecto.setNombre("Test controller");
        when(proyectoService.getById(Long.valueOf(1))).thenReturn(Optional.of(proyecto));

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(tarea);

        mvc.perform(post("/tarea/{idProyecto}", 1).contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isOk());

    }
    @Test
    public void testCrearTareaError() throws Exception{
        Tarea tarea = new TareaSimple("Test", "Una tarea de prueba");
        when(proyectoService.getById(Long.valueOf(1))).thenReturn(Optional.empty());

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(tarea);

        mvc.perform(post("/tarea/{idProyecto}", 1).contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isNotAcceptable());

    }
}
