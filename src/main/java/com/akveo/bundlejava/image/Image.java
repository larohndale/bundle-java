package com.akveo.bundlejava.image;

import com.akveo.bundlejava.user.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.MapsId;

@Entity
@Table(name = "image")
public class Image {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @MapsId
    private User user;

    public Image() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Lob
    @Column(name = "image", columnDefinition = "BLOB")
    private byte[] image;

    public Image(byte[] image) {
        this.image = image;
    }

    public byte[] getImageBytes() {
        return image;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.image = imageBytes;
    }
}
