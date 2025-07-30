package com.example.testapi.testmembers.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TestMembers")
@Getter
@NoArgsConstructor
public class TestMember {
    @Id
    private String testMemberId;
    @Column(nullable = false, length = 100)
    private String testMemberName;
}
