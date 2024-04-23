package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseEntity {
    @CreationTimestamp //생성시 시간을 만들어줌
    @Column(updatable = false) //수정시 관여안함
    private LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(insertable = false) // 입력시 관여안함
    private LocalDateTime updatedTime;



}
