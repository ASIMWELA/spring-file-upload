package com.file.upload.user;

import com.file.upload.image.Image;
import com.file.upload.model.Person;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="user")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class User extends Person {
    @OneToMany(mappedBy = "user")
    Set<Image> images =  new HashSet();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(images, user.images);
    }

    @Override
    public int hashCode() {
        return Objects.hash(images);
    }
}
