package com.example.testapi.testcomments.entity;

import com.example.testapi.testattendances.entity.TestAttendance;
import com.example.testapi.testmembers.entity.TestMember;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TestComments")
@Getter
@NoArgsConstructor
public class TestComment {
    @Id
    private String testCommentId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "testAttendanceId", nullable = false)
    private TestAttendance testAttendance;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "testMemberId", nullable = false)
    private TestMember testMember;
    
    @Column(nullable = false, length = 255)
    private String testComment;
}
