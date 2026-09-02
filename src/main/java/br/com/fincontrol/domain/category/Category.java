package br.com.fincontrol.domain.category;

import br.com.fincontrol.domain.enums.CategoryPurpose;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Table(name = "category")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue
    private UUID id;
    private String description;
    @Enumerated(EnumType.STRING)
    private CategoryPurpose purpose;

}
