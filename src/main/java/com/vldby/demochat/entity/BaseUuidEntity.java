package com.vldby.demochat.entity;

import javax.persistence.*;
import java.util.UUID;

@MappedSuperclass
public class BaseUuidEntity extends BaseGenericIdEntity<UUID>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    protected UUID id;

    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }
}
