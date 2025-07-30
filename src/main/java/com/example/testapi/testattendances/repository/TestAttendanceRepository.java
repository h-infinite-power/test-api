package com.example.testapi.testattendances.repository;

import com.example.testapi.testattendances.entity.TestAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TestAttendanceRepository extends JpaRepository<TestAttendance, Long> {
    @Query(value = "SELECT ta.*, " +
           "(SELECT COUNT(*) FROM TestLike tl WHERE tl.testAttendanceId = ta.testAttendanceId) as testLikesCount, " +
           "(SELECT COUNT(*) FROM TestComment tc WHERE tc.testAttendanceId = ta.testAttendanceId) as testCommentsCount " +
           "FROM TestAttendance ta", nativeQuery = true)
    List<Object[]> findAllWithCounts();

    @Query(value = "SELECT ta.*, " +
           "tl.testLikeId, tl.testMemberId as likeTestMemberId, tm1.testMemberName as likeMemberName, " +
           "tc.testCommentId, tc.testMemberId as commentTestMemberId, tm2.testMemberName as commentMemberName, tc.testComment " +
           "FROM TestAttendance ta " +
           "LEFT JOIN TestLike tl ON ta.testAttendanceId = tl.testAttendanceId " +
           "LEFT JOIN TestMember tm1 ON tl.testMemberId = tm1.testMemberId " +
           "LEFT JOIN TestComment tc ON ta.testAttendanceId = tc.testAttendanceId " +
           "LEFT JOIN TestMember tm2 ON tc.testMemberId = tm2.testMemberId " +
           "WHERE ta.testAttendanceId = :testAttendanceId", nativeQuery = true)
    List<Object[]> findByIdWithDetails(@Param("testAttendanceId") String testAttendanceId);
}
