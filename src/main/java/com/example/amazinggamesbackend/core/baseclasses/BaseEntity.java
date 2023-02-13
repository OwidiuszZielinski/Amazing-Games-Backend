package com.example.amazinggamesbackend.core.baseclasses;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id;
    protected String uuid = UUID.randomUUID().toString();

    public int hashCode() {
        return Objects.hash(uuid);
    }

    public boolean equals(Object that) {
        return this == that || that instanceof BaseEntity
                && Objects.equals(uuid, ((BaseEntity) that).uuid);
    }

}
