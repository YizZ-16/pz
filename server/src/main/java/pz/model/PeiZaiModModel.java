package pz.model;


import javax.persistence.*;

import org.hibernate.annotations.DynamicUpdate;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashMap;

@Entity
@Table(name = "PZ_PLANE_STATIC_MOD")
@DynamicUpdate
@NoArgsConstructor
public class PeiZaiModModel implements Serializable {

    @Transient
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    @Setter @Getter private Integer id;

    @Column(name = "PLANE_ID")
    @Setter @Getter private Integer planeId;

    @Column(name = "PLANE_REG")
    @Setter @Getter private String reg; //机号

    @Column(name = "PLANE_TYPE")
    @Setter @Getter private String type; //机型

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

    @Column(name = "INSERT_TIME")
    @Setter @Getter private java.util.Date insertTime;

    @Column(name = "OPERATION")
    @Setter @Getter private String operation;

    public PeiZaiModModel (PeiZaiModel p) {
        this.planeId = p.getId();
        this.reg = p.getReg();
        this.type = p.getType();
        this.buju = p.getBuju();
        this.cnd = p.getCnd();
        this.airlines = p.getAirlines();
        this.bow = p.getBow();
        this.boi = p.getBoi();
        this.mzfw = p.getMzfw();
        this.mzdw = p.getMzdw();
        this.mtow = p.getMtow();
        this.updateDate = p.getUpdateDate();
        this.modDate = p.getModDate();
        this.insertDate = p.getInsertDate();
        java.util.Date date = new java.util.Date();
        this.setInsertTime(date);
    }

    public HashMap<String, String> getMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("ID", this.id.toString());
        map.put("PLANE_ID", this.planeId.toString());
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
        map.put("INSERT_TIME", String.valueOf(this.insertTime.getTime()));
        map.put("OPERATION", this.operation);
        return map;
    }

}
