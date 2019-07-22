package com.akveo.bundlejava.settings;

import com.akveo.bundlejava.user.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.persistence.GenerationType;
import java.io.Serializable;

@Entity
@Table(name = "settings")
public class Settings implements Serializable {

    private static final long serialVersionUID = 2168089762510982363L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "theme_name", nullable = false)
    private String themeName;

    @OneToOne(mappedBy = "settings")
    private User user;

    public Settings(String themeName) {
        this.themeName = themeName;
    }

    public Settings() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }
}
