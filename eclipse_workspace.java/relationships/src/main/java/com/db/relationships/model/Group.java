package com.db.relationships.model;

import jakarta.persistence.Entity;

import java.util.*;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_group")
public class Group {
 @Id
 @Column(name = "group_id")
 private Long groupId;

 @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
 @JoinTable(
     name = "tbl_user_group",
     joinColumns = @JoinColumn(name = "group_id"),
     inverseJoinColumns = @JoinColumn(name = "user_id")
 )
 private List<User> members;

 public void addMember(User user) {
     if (members == null) {
         members = new ArrayList<>();
     }
     members.add(user);
 }
}