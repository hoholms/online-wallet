package com.online.wallet.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "profiles")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "first_name", nullable = false, length = 50)
  @NotBlank(message = "Please provide your first name")
  @Length(max = 50, message = "First name is too long")
  private String firstName;

  @Column(name = "last_name", nullable = false, length = 50)
  @NotBlank(message = "Please provide your last name")
  @Length(max = 50, message = "Last name is too long")
  private String lastName;

  @Column(name = "email", nullable = false)
  @NotBlank(message = "Please provide an email")
  @Email(message = "Please provide a valid email")
  private String email;

  @Column(name = "balance", nullable = false)
  private BigDecimal balance;

  @Column(name = "created_date", nullable = false)
  private Instant createdDate;

  @OneToOne(optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  @ToString.Exclude
  private User user;

  @Column(name = "activation_code")
  private String activationCode;

  @Column(name = "currency")
  private String currency;

  @OneToMany(mappedBy = "profile")
  @ToString.Exclude
  private Set<Transaction> transactions = new LinkedHashSet<>();

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Profile profile = (Profile) o;
    return id != null && Objects.equals(id, profile.id);
  }

}