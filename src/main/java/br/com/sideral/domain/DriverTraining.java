package br.com.sideral.domain;
import java.util.Date; import javax.persistence.*;
@Entity
public class DriverTraining {
 @Id @GeneratedValue private Long id; private String driverName; private String vehicle; private String course; @Temporal(TemporalType.DATE) private Date expiration; private String status;
 public Long getId(){return id;} public void setId(Long v){id=v;} public String getDriverName(){return driverName;} public void setDriverName(String v){driverName=v;} public String getVehicle(){return vehicle;} public void setVehicle(String v){vehicle=v;} public String getCourse(){return course;} public void setCourse(String v){course=v;} public Date getExpiration(){return expiration;} public void setExpiration(Date v){expiration=v;} public String getStatus(){return status;} public void setStatus(String v){status=v;}
}