package com.example.notebookmgt.models;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserProfile profile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Notebook> notebooks = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "village_id")
    private Village village;

    public User() {}

    public User(Long id, String username, String password, String email, UserProfile profile, Set<Notebook> notebooks, Set<Role> roles, Village village) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.profile = profile;
        this.notebooks = notebooks != null ? notebooks : new HashSet<>();
        this.roles = roles != null ? roles : new HashSet<>();
        this.village = village;
    }

    public static UserBuilder builder() { return new UserBuilder(); }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public UserProfile getProfile() { return profile; }
    public void setProfile(UserProfile profile) { this.profile = profile; }
    public Set<Notebook> getNotebooks() { return notebooks; }
    public void setNotebooks(Set<Notebook> notebooks) { this.notebooks = notebooks; }
    public Set<Role> getRoles() { return roles; }
    public void setRoles(Set<Role> roles) { this.roles = roles; }
    public Village getVillage() { return village; }
    public void setVillage(Village village) { this.village = village; }

    public static class UserBuilder {
        private Long id;
        private String username;
        private String password;
        private String email;
        private UserProfile profile;
        private Set<Notebook> notebooks;
        private Set<Role> roles;
        private Village village;

        public UserBuilder id(Long id) { this.id = id; return this; }
        public UserBuilder username(String username) { this.username = username; return this; }
        public UserBuilder password(String password) { this.password = password; return this; }
        public UserBuilder email(String email) { this.email = email; return this; }
        public UserBuilder profile(UserProfile profile) { this.profile = profile; return this; }
        public UserBuilder notebooks(Set<Notebook> notebooks) { this.notebooks = notebooks; return this; }
        public UserBuilder roles(Set<Role> roles) { this.roles = roles; return this; }
        public UserBuilder village(Village village) { this.village = village; return this; }
        public User build() { return new User(id, username, password, email, profile, notebooks, roles, village); }
    }
}
