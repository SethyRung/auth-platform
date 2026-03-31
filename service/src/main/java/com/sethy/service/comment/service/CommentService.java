package com.sethy.service.comment.service;

import com.sethy.service.comment.dto.CommentResponse;
import com.sethy.service.comment.dto.CreateCommentRequest;
import com.sethy.service.comment.dto.UpdateCommentRequest;

import java.util.List;

public interface CommentService {

    CommentResponse createComment(Long ticketId, CreateCommentRequest request, String username);

    List<CommentResponse> getCommentsForTicket(Long ticketId, String username);

    CommentResponse updateComment(Long ticketId, Long commentId, UpdateCommentRequest request, String username);

    void deleteComment(Long ticketId, Long commentId, String username);
}
