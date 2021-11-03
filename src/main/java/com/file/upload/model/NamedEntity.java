package com.file.upload.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
public class NamedEntity extends BaseEntity{
    @Column(name = "name")
    private String name;
}
