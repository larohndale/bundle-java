package com.akveo.bundlejava.image;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "image")
public class Image implements Serializable {
    private static final long serialVersionUID = -8696224341195777678L;

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Image() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Lob
    @Column(name = "image", columnDefinition = "BLOB")
    private byte[] image;

    public Image(byte[] imageBytes) {
        this.image = imageBytes == null ? null : imageBytes.clone();
    }

    public byte[] getImageBytes() {
        return image == null ? null : image.clone();
    }

    public void setImageBytes(byte[] imageBytes) {
        this.image = imageBytes == null ? null : imageBytes.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Image image1 = (Image) o;
        return Objects.equals(id, image1.id) &&
                Arrays.equals(image, image1.image);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id);
        result = 31 * result + Arrays.hashCode(image);
        return result;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", image=" + Arrays.toString(image) +
                '}';
    }
}
