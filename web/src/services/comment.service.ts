import type { Comment, CreateCommentRequest, UpdateCommentRequest } from "@/types/comment";
import { api } from "@/utils/api";

class CommentService {
  async getCommentsForTicket(ticketId: number) {
    return await api.get<Comment[]>(`/tickets/${ticketId}/comments`);
  }

  async createComment(ticketId: number, request: CreateCommentRequest) {
    return await api.post<Comment>(`/tickets/${ticketId}/comments`, request);
  }

  async updateComment(ticketId: number, commentId: number, request: UpdateCommentRequest) {
    return await api.put<Comment>(`/tickets/${ticketId}/comments/${commentId}`, request);
  }

  async deleteComment(ticketId: number, commentId: number) {
    await api.delete<void>(`/tickets/${ticketId}/comments/${commentId}`);
  }
}

export const commentService = new CommentService();
