package org.urlMonitor.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.validator.constraints.Email;

import com.google.common.collect.Sets;

/**
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 */
@Entity
@Access(AccessType.FIELD)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class User extends ModelBase implements Serializable {
    public User(String name, String email, boolean enabled) {
        this.name = name;
        this.email = email;
        this.enabled = enabled;
    }

    @NotNull
    @Size(max = 255)
    private String name;

    @Size(max = 100)
    @Email
    @Column(unique = true)
    private String email;

    @NotNull
    private boolean enabled;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user",
            cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter(AccessLevel.NONE)
    private Set<UserRole> roles = Sets.newHashSet();

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "maintainers",
            cascade = CascadeType.ALL)
    private Set<Monitor> maintainerMonitor = Sets.newHashSet();

    public boolean addRole(String role) {
        for (UserRole userRole : roles) {
            if (userRole.getRole().equals(role)) {
                return false;
            }
        }
        roles.add(new UserRole(this, role));
        return true;
    }

    public boolean removeRole(String role) {
        for (UserRole userRole : roles) {
            if (userRole.getRole().equals(role)) {
                roles.remove(userRole);
                return true;
            }
        }
        return false;
    }
}
