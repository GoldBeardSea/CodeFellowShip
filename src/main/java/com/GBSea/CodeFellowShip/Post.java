package com.GBSea.CodeFellowShip;


import javax.persistence.*;
import java.util.Date;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String subject;
    String body;
    Date createdOn;

    @ManyToOne
    AppUser user;

    public long getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public AppUser getUser() {
        return user;
    }
}
