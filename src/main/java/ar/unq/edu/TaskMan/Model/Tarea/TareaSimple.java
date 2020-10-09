package ar.unq.edu.TaskMan.Model.Tarea;

import ar.unq.edu.TaskMan.Model.Estado;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "TareaSimple")
@DiscriminatorValue("TareaSimple")
public class TareaSimple extends Tarea {

    public TareaSimple(){}
    public TareaSimple(String titulo, String descripcion) {
        super(titulo, descripcion);
        setEstado(Estado.CREADA);
    }
}
