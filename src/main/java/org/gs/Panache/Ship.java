package org.gs.Panache;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Ship extends PanacheEntity {

    @Column
    public String name;

    @Column
    public String direction;

    @Column
    public  String size;

    @OneToMany(targetEntity = Pirate.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Pirate> listOfPirates = new ArrayList<>();

}
