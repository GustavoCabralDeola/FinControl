package br.com.fincontrol.domain.category;

import br.com.fincontrol.domain.enums.CategoryPurpose;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
import jakarta.persistence.Column;

@Table(name = "category")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, length = 400)
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryPurpose purpose;

}
