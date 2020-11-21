package ar.unq.edu.TaskMan.Model;

import ar.unq.edu.TaskMan.Model.Tarea.Tarea;

import java.util.ArrayList;
import java.util.List;

public class Buscador {
    private List<Proyecto> proyectos = new ArrayList<>();
    private List<Tarea> tareas = new ArrayList<>();
    public Buscador(){}

    public List<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
    }

    public List<Proyecto> getProyectos() {
        return proyectos;
    }

    public void setProyectos(List<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }
}
