package ar.unq.edu.TaskMan.Model.Tarea;

import ar.unq.edu.TaskMan.Model.Estado;
import ar.unq.edu.TaskMan.Model.Prioridad;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;

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
//        if(this.esCritica()){
//            setEstado(Estado.CRITICA);
//        }
        return super.getEstado();
    }

    @Override
    public boolean isCompleja() {
        return true;
    }

    private Boolean esCritica(){
        return getEstado().compareTo(Estado.CRITICA) == 0;
    }

    @Override
    public int compareTo(Tarea tarea) {
        if(tarea.isCompleja()){
            TareaCompleja tareaCompleja = (TareaCompleja) tarea;
            int result = tareaCompleja.getEstado().compareTo(this.getEstado());
            if(result == 0){
                return tareaCompleja.getPrioridad().compareTo(this.getPrioridad());
            }else {
                return result;
            }
        }else{
            return -1;
        }
    }
    @Override
    public void verificarEstado(){
        if(!esCritica() && fechaVencida(this.fecha_estimada)){
            verificarPrioridad();
        }

    }

    private void verificarPrioridad() {
        switch (this.getPrioridad()){
            case BAJA:
                setPrioridad(Prioridad.MEDIO);
                this.setFecha_estimada(posponerfechaVencimiento(15));
                break;
            case MEDIO:
                this.setFecha_estimada(posponerfechaVencimiento(10));
                setPrioridad(Prioridad.ALTA);
                break;
            case ALTA:
                setEstado(Estado.CRITICA);
        }
    }
    private LocalDate verificarMes(int limiteMes,int masDias, int diaDelMes, int mesSiguente){
        LocalDate now = LocalDate.now();
        while (diaDelMes< limiteMes && masDias > 0){
            diaDelMes +=1;
            masDias--;
        }
        if(masDias > 0){
            now = LocalDate.of(now.getYear(), mesSiguente, masDias);
        }else{
            now = LocalDate.of(now.getYear(), now.getMonthValue(),diaDelMes );
        }
        return now;
    }
    private LocalDate posponerfechaVencimiento(int masDias) {
        LocalDate now = LocalDate.now();
        int day = now.getDayOfMonth();
        switch (now.getMonth()){
            case JANUARY:
                now = verificarMes(31,masDias,day, 2);
                break;
            case FEBRUARY:
                int limiteMes = 28;
                if((now.getYear() % 4) == 0){
                    limiteMes = 29;
                }
                now = verificarMes(limiteMes,masDias,day, 3);
                break;
            case MARCH:
                now = verificarMes(31,masDias,day, 4);
                break;
            case APRIL:
                now = verificarMes(30,masDias,day, 5);
                break;
            case MAY:
                now = verificarMes(31, masDias,day, 6);
                break;
            case JUNE:
                now = verificarMes(30,masDias,day, 7);
                break;
            case JULY:
                now = verificarMes(31,masDias,day, 8);
                break;
            case AUGUST:
                now = verificarMes(31,masDias,day, 9);
                break;
            case SEPTEMBER:
                now = verificarMes(30,masDias,day, 10);
                break;
            case OCTOBER:
                now = verificarMes(31,masDias,day, 11);
                break;
            case NOVEMBER:
                now = verificarMes(30,masDias,day, 12);
                break;
            case DECEMBER:
                now = verificarMes(31, masDias,day, 1);
                break;
        }
        return now;
    }

    private Boolean fechaVencida(LocalDate fecha1){
        LocalDate now = LocalDate.now();
        Boolean result = false;
        if(fecha1.getYear() <= now.getYear()){
            if(fecha1.getMonthValue() <= now.getMonthValue()){
                result = fecha1.getDayOfMonth() < now.getDayOfMonth();
            }
        }
        return result;
    }
}
