package com.example.amazinggamesbackend.core.baseclasses;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    @Id
    @GeneratedValue
    private int id;
    private String uuid = UUID.randomUUID().toString();

    public int hashCode(){
        return Objects.hash(uuid);
    }
    public boolean equals(Object that){
        return this == that || that instanceof BaseEntity
                && Objects.equals(uuid,((BaseEntity) that).uuid);
    }

}
