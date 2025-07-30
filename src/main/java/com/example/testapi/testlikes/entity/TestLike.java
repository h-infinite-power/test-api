package com.example.testapi.testlikes.entity;

import com.example.testapi.testattendances.entity.TestAttendance;
import com.example.testapi.testmembers.entity.TestMember;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TestLikes")
@Getter
@NoArgsConstructor
public class TestLike {
    @Id
    private String testLikeId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "testAttendanceId", nullable = false)
    private TestAttendance testAttendance;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "testMemberId", nullable = false)
    private TestMember testMember;
}
