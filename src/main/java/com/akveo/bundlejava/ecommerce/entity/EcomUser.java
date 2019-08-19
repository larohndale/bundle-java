package com.akveo.bundlejava.ecommerce.entity;

import com.akveo.bundlejava.user.User;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

//public class EcomUser extends User {
//
//    @OneToMany
//    @JoinColumn
//    private List<Order> ordersUpdatedBy;
//
//    @OneToMany
//    @JoinColumn
//    private List<Order> ordersCreatedBy;
//
//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    private List<UserActivity> userActivities;
//}
