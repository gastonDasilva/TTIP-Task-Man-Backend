package ar.unq.edu.TaskMan.Model.Tarea;

import ar.unq.edu.TaskMan.Model.Estado;
import ar.unq.edu.TaskMan.Model.Prioridad;

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

    @Override
    public boolean isCompleja() {
        return false;
    }

    @Override
    public int compareTo(Tarea tarea) {
        if(tarea.isCompleja()){
            return 1;
        }else {
            return tarea.getEstado().compareTo(this.getEstado());
        }

    }
}
