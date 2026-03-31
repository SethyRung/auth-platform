package com.sethy.service.comment.service.impl;

import com.sethy.service.comment.dto.CommentResponse;
import com.sethy.service.comment.dto.CreateCommentRequest;
import com.sethy.service.comment.dto.UpdateCommentRequest;
import com.sethy.service.comment.entity.Comment;
import com.sethy.service.comment.repository.CommentRepository;
import com.sethy.service.comment.service.CommentService;
import com.sethy.service.ticket.entity.Ticket;
import com.sethy.service.ticket.repository.TicketRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final TicketRepository ticketRepository;

    public CommentServiceImpl(CommentRepository commentRepository, TicketRepository ticketRepository) {
        this.commentRepository = commentRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public CommentResponse createComment(Long ticketId, CreateCommentRequest request, String username) {
        Ticket ticket = findTicketByIdOrThrow(ticketId);
        
        // Check if user is ticket owner or admin
        if (!canAccessTicket(ticket, username)) {
            throw new IllegalArgumentException("You do not have permission to comment on this ticket");
        }

        Comment comment = new Comment();
        comment.setTicket(ticket);
        comment.setAuthor(username);
        comment.setContent(request.getContent());

        Comment savedComment = commentRepository.save(comment);
        return CommentResponse.fromEntity(savedComment);
    }

    @Override
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<CommentResponse> getCommentsForTicket(Long ticketId, String username) {
        Ticket ticket = findTicketByIdOrThrow(ticketId);
        
        // Check if user is ticket owner or admin
        if (!canAccessTicket(ticket, username)) {
            throw new IllegalArgumentException("You do not have permission to view comments on this ticket");
        }

        List<Comment> comments = commentRepository.findByTicketIdOrderByCreatedAtAsc(ticketId);
        return comments.stream()
                .map(CommentResponse::fromEntity)
                .toList();
    }

    @Override
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public CommentResponse updateComment(Long ticketId, Long commentId, UpdateCommentRequest request, String username) {
        Comment comment = findCommentByIdOrThrow(commentId);

        // Verify comment belongs to ticket
        if (!comment.getTicket().getId().equals(ticketId)) {
            throw new IllegalArgumentException("Comment does not belong to this ticket");
        }

        // Check if user is comment author or admin
        if (!canModifyComment(comment, username)) {
            throw new IllegalArgumentException("You do not have permission to update this comment");
        }

        comment.setContent(request.getContent());
        Comment savedComment = commentRepository.save(comment);
        return CommentResponse.fromEntity(savedComment);
    }

    @Override
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public void deleteComment(Long ticketId, Long commentId, String username) {
        Comment comment = findCommentByIdOrThrow(commentId);

        // Verify comment belongs to ticket
        if (!comment.getTicket().getId().equals(ticketId)) {
            throw new IllegalArgumentException("Comment does not belong to this ticket");
        }

        // Check if user is comment author or admin
        if (!canModifyComment(comment, username)) {
            throw new IllegalArgumentException("You do not have permission to delete this comment");
        }

        commentRepository.delete(comment);
    }

    private Ticket findTicketByIdOrThrow(Long id) {
        Optional<Ticket> ticket = ticketRepository.findById(id);
        return ticket.orElseThrow(() -> new IllegalArgumentException("Ticket not found with id: " + id));
    }

    private Comment findCommentByIdOrThrow(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        return comment.orElseThrow(() -> new IllegalArgumentException("Comment not found with id: " + id));
    }

    private boolean canAccessTicket(Ticket ticket, String username) {
        return isAdmin() || ticket.getCreatedBy().equals(username);
    }

    private boolean canModifyComment(Comment comment, String username) {
        return isAdmin() || comment.getAuthor().equals(username);
    }

    private boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));
    }
}
