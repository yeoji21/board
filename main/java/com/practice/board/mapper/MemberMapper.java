package com.practice.board.mapper;

import com.practice.board.domain.member.Member;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MemberMapper {

    @Insert("insert into member(login_id,password,name,description) values(#{member.loginId},#{member.password},#{member.name},#{member.description})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int saveMember(@Param("member") Member member);

    //update
    @Update("update member set password=#{member.password}, name=#{member.name}, description=#{member.description} where id = #{id}")
    void updateMember(@Param("id")Long id, @Param("member") Member member);

    @Select("select * from member where id = #{id}")
    @Results(id="MemberMap", value={
            @Result(property = "loginId", column="login_id"),
    })
    Member findById(@Param("id") Long id);

    @Select("select * from member")
    @ResultMap("MemberMap")
    List<Member> findAll();

}
