package com.desafioswap.webhook.domain.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
}
