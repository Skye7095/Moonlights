package com.moonlight.moonlights.post.like.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeDAO {

	public int insertLike(
			@Param("userId") int userId
			, @Param("postId") int postId);
	
	public int selectLikeNumber(@Param("postId") int postId);
	
	public int selectLikeCountByUserId(@Param("userId") int userId, @Param("postId") int postId);
	
	public int deleteLike(
			@Param("postId") int postId
			, @Param("userId") int userId);
	
	public int deleteLikeByPostId(@Param("postId") int postId);
}
