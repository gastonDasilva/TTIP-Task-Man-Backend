package ar.unq.edu.TaskMan.Model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Estado {
    CANCELADA("CANCELADA"),
    TERMINADA("TERMINADA"),
    CREADA("CREADA"),
    EN_PROCESO("EN_PROCESO"),
    CRITICA("CRITICA");

    private final String name;
    Estado(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }


    @Override public String toString() { return name; }

    @JsonCreator
    public static Estado fromText(String text){
        for(Estado est : Estado.values()){
            if(est.getName().equals(text)){
                return est;
            }
        }
        throw new IllegalArgumentException();
    }

}
