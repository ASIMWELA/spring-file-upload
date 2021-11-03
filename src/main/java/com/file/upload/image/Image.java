package com.file.upload.image;

import com.file.upload.model.NamedEntity;
import com.file.upload.user.User;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Image extends NamedEntity {
    @Column(name="image_type", nullable = false)
    String imageType;
    @Column(name="image_url", nullable = false)
    String imageUrl;
    @Column(name="on_display")
    boolean onDisplay;
    @ManyToOne(targetEntity = User.class,optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    User user;
}
