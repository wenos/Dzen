package edu.mirea.myinvest.domain.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "ref_user_security")
public class UserSecurityRel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_security_id_seq")
    @SequenceGenerator(name = "user_security_id_seq", sequenceName = "user_security_id_seq", allocationSize = 1)
    private Long id;

    /**
     * Пост, к которому относится файл
     */
    @ManyToOne
    @JoinColumn(name = "security_id")
    private Security security;

    /**
     * Файл, который относится к посту
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
