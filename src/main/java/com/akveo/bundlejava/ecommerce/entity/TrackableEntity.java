package com.akveo.bundlejava.ecommerce.entity;

import com.akveo.bundlejava.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class TrackableEntity {

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "created_by_user_id", referencedColumnName = "id")
    private User createdByUserId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "updated_by_user_id", referencedColumnName = "id")
    private User updatedByUserId;

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public User getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(User createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public User getUpdatedByUserId() {
        return updatedByUserId;
    }

    public void setUpdatedByUserId(User updatedByUserId) {
        this.updatedByUserId = updatedByUserId;
    }
}
