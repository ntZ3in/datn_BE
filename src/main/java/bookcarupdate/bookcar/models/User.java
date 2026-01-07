package bookcarupdate.bookcar.models;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tbluser")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userID")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userID;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    private String userName;
    private String phone_number;

    @Enumerated(EnumType.STRING)
    private Role role;
    private Date createdAt;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIdentityReference(alwaysAsId = true)
    @JsonManagedReference                      // Đánh dấu mối quan hệ quản lý
    private Store store;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIdentityReference(alwaysAsId = true)
    @JsonManagedReference  // Đánh dấu mối quan hệ quản lý
    @JsonIgnore
    private List<Order> orderList;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }


    @Override
    @JsonIgnore
    public String getUsername() {
        return this.userName;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

}
