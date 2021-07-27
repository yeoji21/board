package com.practice.board.mapper;

import com.practice.board.domain.post.Post;
import com.practice.board.domain.post.form.PostSaveForm;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PostMapper {

    //글 저장
    @Insert("insert into post(member_id,name,title,content) values(#{post.memberId},#{post.name},#{post.title},#{post.content})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
//    @Options(useGeneratedKeys = true, keyProperty = "postDate")
    void savePost(@Param("post") Post post);


    //글 목록 불러오기
    @Select("select * from post")
    @Results(id="PostMapper", value={
            @Result(property = "memberId", column="member_id"),
            @Result(property = "postDate", column="post_date")
    })
    List<Post> postList();

    @Select("select * from post where id = #{id}")
    @ResultMap("PostMapper")
    Post getPost(@Param("id") Long id);

    //글 삭제
    @Delete("delete from post where id=#{id}")
    void deletePost(@Param("id") Long id);


    @Update("update post set title=#{post.title},content=#{post.content} where id=#{id}")
    void updatePost(@Param("id") Long id, @Param("post")PostSaveForm post);

    //글 검색 <- 나중에 추가

}
