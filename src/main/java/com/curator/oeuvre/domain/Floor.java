//package com.curator.oeuvre.domain;
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.ToString;
//import org.hibernate.annotations.ColumnDefault;
//import org.hibernate.annotations.DynamicInsert;
//import org.hibernate.annotations.DynamicUpdate;
//
//import javax.persistence.*;
//
//@ToString
//@Getter
//@Setter
//@Entity
//@Table(name = "Floor")
//@NoArgsConstructor
//@DynamicUpdate
//@DynamicInsert
//public class Floor extends AbstractTimestamp {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long no;
//
//    @Column(nullable = false)
//    private Integer queue;
//
//    @Column(nullable = false)
//    private String name;
//
//    @Column(nullable = false)
//    private String color;
//
//    @Column(nullable = false)
//    @ColumnDefault(value = "0")
//    private Integer texture;
//
//    @Column(nullable = false)
//    @ColumnDefault(value = "true")
//    private Boolean isPublic;
//
//
//}
