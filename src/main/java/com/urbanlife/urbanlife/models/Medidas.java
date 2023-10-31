package com.urbanlife.urbanlife.models;

import jakarta.persistence.*;
import lombok.*;

@Table
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medidas {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idMedida;
    private String talle;

}
