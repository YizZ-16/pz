package pz.model;


import javax.persistence.*;

import org.hibernate.annotations.DynamicUpdate;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;

@Entity
@Table(name = "PZ_USERS")
@DynamicUpdate
@NoArgsConstructor
public class UserModel implements Serializable {

    @Transient
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACCOUNT", unique = true)
    @Setter @Getter private String account;


    @Column(name = "NAME")
    @Setter @Getter private String name;

    @Column(name = "PASSWORD")
    @Setter @Getter private String password;

    @Column(name = "AIRPORT")
    @Setter @Getter private String airport;

    @Column(name = "TYPE")
    @Setter @Getter private String type;


    public HashMap<String, String> getMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("ACCOUNT", this.account);
        map.put("NAME", this.name);
        map.put("PASSWORD", this.password);
        map.put("AIRPORT", this.airport);
        map.put("TYPE", this.type);
        return map;
    }

}

