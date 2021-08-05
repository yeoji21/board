package com.practice.board.mapper;

import com.practice.board.domain.comment.Comment;
import com.practice.board.domain.comment.form.CommentEditForm;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {
    //댓글 달기
    @Insert("insert into comment(member_id,post_id,comment) values(#{comment.memberId},#{comment.postId},#{comment.comment})")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    void saveComment(@Param("comment") Comment comment);

    //게시물에 모든 댓글 가져오기
    @Select("select * from comment where post_id=#{postId}")
    @Results(id="CommentMap", value = {
            @Result(property="memberId", column="member_id"),
            @Result(property = "postId", column = "post_id"),
    })
    List<Comment> getComment(@Param("postId")Long postId);

    //어떤 게시물에 특정 사용자가 작성한 댓글 가져오기
    @Select("select * from comment where post_id=#{postId} and member_id=#{memberId}")
    @ResultMap("CommentMap")
    List<Comment> getCommentByMemberAndPost(@Param("postId")Long postId, @Param("memberId")Long memberId);

    @Select("select * from comment where id=#{id}")
    @ResultMap("CommentMap")
    Comment getCommentById(@Param("id")Long id);

    //선택한 댓글 하나 삭제
    @Delete("delete from comment where id=#{id}")
    void deleteComment(@Param("id") Long id);

    //해당 게시물의 모든 댓글 삭제
    @Delete("delete from comment where post_id=#{postId}")
    void deleteCommentByPostId(@Param("postId") Long postId);

    @Update("update comment set comment=#{comment.comment}, date=#{comment.date} where id=#{comment.cid}")
    void updateCommentAndDate(@Param("comment")CommentEditForm comment);

    //해당 게시물의 댓글 수
    @Select("select count(id) count from comment where post_id=#{postId}")
    int getNumByPostId(@Param("postId") Long postId);
}
