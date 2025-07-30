package com.example.testapi.testattendances.entity;

import com.example.testapi.testmembers.entity.TestMember;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "TestAttendances")
@Getter
@NoArgsConstructor
@Setter
public class TestAttendance {
    @Id
    private String testAttendanceId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "testMemberId", nullable = false)
    private TestMember testMember;
    
    @Column(nullable = false)
    private LocalDate testAttendanceDate;
}
