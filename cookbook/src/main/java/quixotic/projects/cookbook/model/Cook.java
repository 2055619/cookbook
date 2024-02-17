package quixotic.projects.cookbook.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import quixotic.projects.cookbook.model.enums.RecipeType;
import quixotic.projects.cookbook.security.Role;
import quixotic.projects.cookbook.model.enums.Unit;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Cook implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(nullable = false)
    private String firstname;
    @Column(nullable = false)
    private String lastname;
    @Enumerated(EnumType.STRING)
    private Unit powderUnit;
    @Enumerated(EnumType.STRING)
    private Unit liquidUnit;
    @Enumerated(EnumType.STRING)
    private Unit solidUnit;
    @Enumerated(EnumType.STRING)
    private Unit otherUnit;

    @OneToMany(mappedBy = "cook")
    private Set<Publication> publications = new HashSet<>();
//    @OneToMany(mappedBy = "cook")
//    private Set<Reaction> reactions = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }


    //	Non implémenté car non utilisé (Pour le moment) 2024-02-11
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
