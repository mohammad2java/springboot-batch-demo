package com.amir.batch.demo.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MYUSER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private Long id;
    private String name;
    private String dept;
    private Integer salary;
    private Date time;
}
