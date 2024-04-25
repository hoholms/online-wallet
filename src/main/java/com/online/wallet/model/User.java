package com.online.wallet.model;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "username", nullable = false, length = 50)
  @NotBlank(message = "Please provide a username")
  @Length(max = 50, message = "Username is too long")
  private String username;

  @Column(name = "password", nullable = false, length = 500)
  @NotBlank(message = "Please provide a password")
  @Length(min = 8, max = 500, message = "Password must be at least 8 characters")
  @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=\\S+$).{8,}$",
      message = "Password must have at least 1 number and 1 letter")
  private String password;

  @Column(name = "enabled", nullable = false)
  private Boolean enabled = false;

  @ElementCollection(targetClass = Authority.class, fetch = FetchType.EAGER)
  @CollectionTable(name = "authorities", joinColumns = @JoinColumn(name = "user_id"))
  @Enumerated(EnumType.STRING)
  private Set<Authority> authority;

  public boolean isAdmin() {
    return authority.contains(Authority.ADMIN);
  }

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
    User user = (User) o;
    return username != null && Objects.equals(username, user.username);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return getAuthority();
  }


  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return enabled;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return getEnabled();
  }

}