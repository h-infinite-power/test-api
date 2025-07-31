package com.example.testapi.testattendances.repository;

import com.example.testapi.testattendances.dto.TestAttendanceDetailDto;
import com.example.testapi.testattendances.dto.TestAttendanceDto;
import com.example.testapi.testattendances.dto.TestAttendanceWithCountsDto;
import com.example.testapi.testattendances.entity.TestAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TestAttendanceRepository extends JpaRepository<TestAttendance, Long> {
    @Query(value = "SELECT ta.testAttendanceId, ta.testMember.testMemberId, ta.testAttendanceDate, " +
           "(SELECT COUNT(*) FROM TestLike tl WHERE tl.testAttendance.testAttendanceId = ta.testAttendanceId) as testLikesCount, " +
           "(SELECT COUNT(*) FROM TestComment tc WHERE tc.testAttendance.testAttendanceId = ta.testAttendanceId) as testCommentsCount " +
           "FROM TestAttendance ta")
    List<TestAttendanceWithCountsDto> findAllWithCounts();

    @Query(value = """
           SELECT DISTINCT ta 
                FROM TestAttendance ta
                LEFT JOIN FETCH ta.testMember
                LEFT JOIN FETCH ta.testLikes 
                LEFT JOIN FETCH ta.testLikes.testMember
                LEFT JOIN FETCH ta.testComments
                LEFT JOIN FETCH ta.testComments.testMember
                WHERE ta.testAttendanceId = :testAttendanceId 
           """)
    List<TestAttendance> findByIdWithDetails(@Param("testAttendanceId") Long testAttendanceId);

    @Query(value = """
           SELECT ta  
           FROM TestAttendance ta
           WHERE ta.testMember.testMemberId = :testMemberId AND ta.testAttendanceDate = :testAttendanceDate
           """)
    List<TestAttendance> findByMemberIdAndDate(Long testMemberId, LocalDate testAttendanceDate);

}
