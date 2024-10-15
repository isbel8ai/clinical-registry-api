package org.isbel8ai.training.clinic.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patient_id_generator")
    @SequenceGenerator(name = "patient_id_generator", sequenceName = "patient_id_sequence", allocationSize = 1)
    private Long id;

    @OneToOne
    private Account account;
}
