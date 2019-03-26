package models;

import lombok.Data;

@Data
public class Player {
    String name;

    @Override
    public String toString() {
        return name;
    }
}
