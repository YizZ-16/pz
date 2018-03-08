package pz.model;


import javax.persistence.*;

import org.hibernate.annotations.DynamicUpdate;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.HashMap;

@Entity
@Table(name = "PZ_PLANE_STATIC")
@DynamicUpdate
@NoArgsConstructor
public class PeiZaiModel {

    @Transient
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PLANE_ID", unique = true)
    @Setter @Getter private Integer id;


    @Column(name = "PLANE_REG")
    @Setter @Getter private String reg;

    @Column(name = "PLANE_TYPE")
    @Setter @Getter private String type;

    @Column(name = "PLANE_BUJU")
    @Setter @Getter private String buju;

    @Column(name = "PLANE_CND")
    @Setter @Getter private String cnd;

    @Column(name = "PLANE_AIRLINES")
    @Setter @Getter private String airlines;

    @Column(name = "PLANE_BOW")
    @Setter @Getter private String bow;

    @Column(name = "PLANE_BOI")
    @Setter @Getter private String boi;

    @Column(name = "PLANE_MZFW")
    @Setter @Getter private Integer mzfw;

    @Column(name = "PLANE_MZDW")
    @Setter @Getter private Integer mzdw;

    @Column(name = "PLANE_MTOW")
    @Setter @Getter private Integer mtow;

    @Column(name = "PLANE_UPDATE_DATE")
    @Setter @Getter private Date updateDate;

    @Column(name = "PLANE_INSERT_DATE")
    @Setter @Getter private java.util.Date insertDate;
    @Column(name = "PLANE_MOD_DATE")
    @Setter @Getter private java.util.Date modDate;


    public HashMap<String, String> getMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("PLANE_ID", this.id.toString());
        map.put("PLANE_REG", this.reg);
        map.put("PLANE_TYPE", this.type);
        map.put("PLANE_BUJU", this.buju);
        map.put("PLANE_CND", this.cnd);
        map.put("PLANE_AIRLINES", this.airlines);
        map.put("PLANE_BOW", this.bow);
        map.put("PLANE_BOI", this.boi);
        map.put("PLANE_MZFW", this.mzfw.toString());
        map.put("PLANE_MZDW", this.mzdw.toString());
        map.put("PLANE_MTOW", this.mtow.toString());
        map.put("PLANE_UPDATE_DATE", this.updateDate.toString());
        map.put("PLANE_INSERT_DATE", String.valueOf(this.insertDate.getTime()));
        map.put("PLANE_MOD_DATE", String.valueOf(this.modDate.getTime()));
        return map;
    }

}
