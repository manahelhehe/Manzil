package manzil.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Entity
@Data
public class Admin extends ManzilUser
{
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role roles;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
    name = "admin_permissions",
    joinColumns = @JoinColumn(name = "admin_id")
    )
    @Enumerated(EnumType.STRING)
    private List<Permission> permissions;
}
