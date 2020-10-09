package ar.unq.edu.TaskMan.Model.Tarea;

import ar.unq.edu.TaskMan.Model.Estado;

import javax.persistence.Entity;

@Entity
public class TareaSimple extends Tarea {

    public TareaSimple(){}
    public TareaSimple(String titulo, String descripcion) {
        super(titulo, descripcion);
        setEstado(Estado.CREADA);
    }
}
