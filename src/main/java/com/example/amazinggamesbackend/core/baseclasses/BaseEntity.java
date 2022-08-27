package com.example.amazinggamesbackend.core.baseclasses;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
@EqualsAndHashCode(of = "uuid")
public abstract class BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String uuid = UUID.randomUUID().toString();

}
