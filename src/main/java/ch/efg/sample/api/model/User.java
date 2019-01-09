package ch.efg.sample.api.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@AllArgsConstructor()
@NoArgsConstructor
@ToString
@EqualsAndHashCode(exclude="id")
@Getter @Setter
public class User implements IUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private String id;
    private String name;
    private String groupId;

    public User(String name, String groupId) {
        this.name = name;
        this.groupId = groupId;
    }
}
