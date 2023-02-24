package com.moonlight.moonlights.post.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.moonlight.moonlights.post.model.Post;
import com.moonlight.moonlights.post.model.PostDetail;

@Repository
public interface PostDAO {
	
	public int insertPost(
			@Param("userId") int userId
			, @Param("content") String content
			, @Param("imagePath") String imagePath);
	
	public List<Post> selectPostList();
	
	public Post selectPost(@Param("postId") int postId);

	public int deletePost(
			@Param("postId") int postId
			, @Param("userId") int userId);
}
