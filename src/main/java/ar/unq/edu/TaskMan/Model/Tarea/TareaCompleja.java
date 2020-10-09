package ar.unq.edu.TaskMan.Model.Tarea;

import ar.unq.edu.TaskMan.Model.Estado;
import ar.unq.edu.TaskMan.Model.Prioridad;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.Date;

@Entity(name = "TareaCompleja")
@DiscriminatorValue("TareaCompleja")
public class TareaCompleja extends Tarea {
    private LocalDate fecha_creacion;
    private LocalDate fecha_estimada;
    private Prioridad prioridad;

    public TareaCompleja(){}
    public TareaCompleja(String titulo, String descripcion, LocalDate fecha_estimada, Prioridad prioridad) {
        super(titulo, descripcion);
        this.fecha_creacion = LocalDate.now();
        this.fecha_estimada = fecha_estimada;
        this.prioridad = prioridad;
        this.setEstado(Estado.CREADA);
    }

    public LocalDate getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(LocalDate fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public LocalDate getFecha_estimada() {
        return fecha_estimada;
    }

    public void setFecha_estimada(LocalDate fecha_estimada) {
        this.fecha_estimada = fecha_estimada;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }

    @Override
    public Estado getEstado(){
        if(this.esCritica()){
            setEstado(Estado.CRITICA);
        }
        return super.getEstado();
    }
    private Boolean esCritica(){
        return false;
    }
}
