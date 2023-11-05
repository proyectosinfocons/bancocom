package com.example.bancocom.model;

import com.example.bancocom.audit.Auditable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "USUARIOS")
public class Usuario extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cellphone;
    private String name;
    private String lastName;
    private String username;
    private String password;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Post> posts = new HashSet<>();

    public void addPost(Post post) {
        posts.add(post);
        post.setUsuario(this);
    }

    public void removePost(Post post) {
        posts.remove(post);
        post.setUsuario(null);
    }

}

