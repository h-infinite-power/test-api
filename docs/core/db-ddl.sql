-- TestMember 테이블: 구성원 정보를 저장하는 테이블
CREATE TABLE TestMembers (
    testMemberId bigint NOT NULL PRIMARY KEY,  -- 구성원 고유 ID
    testMemberName VARCHAR(100) NOT NULL             -- 구성원 이름
);

-- TestAttendance 테이블: 출석 체크 정보를 저장하는 테이블
CREATE TABLE TestAttendances (
    testAttendanceId bigint NOT NULL PRIMARY KEY,  -- 출석 체크 고유 ID
    testMemberId bigint NOT NULL,                  -- 구성원 ID (TestMember 외래 키)
    testAttendanceDate DATE NOT NULL                    -- 출석 체크 날짜
);

-- TestLike 테이블: 출석 체크에 대한 좋아요 정보를 저장하는 테이블
CREATE TABLE TestLikes (
    testLikeId bigint NOT NULL PRIMARY KEY,        -- 좋아요 고유 ID
    testAttendanceId bigint NOT NULL,              -- 출석 체크 ID (TestAttendance 외래 키)
    testMemberId bigint NOT NULL                 -- 좋아요를 누른 구성원 ID (TestMember 외래 키)
);

-- TestComments 테이블: 출석 체크에 대한 댓글 정보를 저장하는 테이블
CREATE TABLE TestComments (
    testCommentId bigint NOT NULL PRIMARY KEY,     -- 댓글 고유 ID
    testAttendanceId bigint NOT NULL,              -- 출석 체크 ID (TestAttendance 외래 키)
    testMemberId bigint NOT NULL,                  -- 댓글을 작성한 구성원 ID (TestMember 외래 키)
    testComment VARCHAR(255) NOT NULL                  -- 댓글 내용
)