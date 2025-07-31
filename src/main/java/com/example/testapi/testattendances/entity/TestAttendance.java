package com.example.testapi.testattendances.entity;

import com.example.testapi.testcomments.entity.TestComment;
import com.example.testapi.testlikes.entity.TestLike;
import com.example.testapi.testmembers.entity.TestMember;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "TestAttendances")
@Getter
@NoArgsConstructor
@Setter
public class TestAttendance {
    @Id
    private Long testAttendanceId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "testMemberId", nullable = false)
    private TestMember testMember;
    
    @Column(nullable = false)
    private LocalDate testAttendanceDate;

    @OneToMany(mappedBy = "testAttendance", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("testLikeId")
    private Set<TestLike> testLikes = new LinkedHashSet<>();

    @OneToMany(mappedBy = "testAttendance", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("testCommentId")
    private Set<TestComment> testComments = new LinkedHashSet<>();
}
