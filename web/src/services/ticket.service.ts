import type {
  CreateTicketRequest,
  Ticket,
  TicketStatus,
  UpdateStatusRequest,
  UpdateTicketRequest,
} from "@/types/ticket";
import { api } from "@/utils/api";

class TicketService {
  async getAllTickets() {
    return await api.get<Ticket[]>("/tickets");
  }

  async getMyTickets() {
    return await api.get<Ticket[]>("/tickets/my");
  }

  async getTicketById(id: number) {
    return await api.get<Ticket>(`/tickets/${id}`);
  }

  async createTicket(request: CreateTicketRequest) {
    return await api.post<Ticket>("/tickets", request);
  }

  async updateTicketAsAdmin(id: number, request: UpdateTicketRequest) {
    return await api.put<Ticket>(`/tickets/${id}`, request);
  }

  async updateTicketAsUser(id: number, request: UpdateTicketRequest) {
    return await api.patch<Ticket>(`/tickets/${id}`, request);
  }

  async updateTicketStatus(id: number, status: TicketStatus) {
    const request: UpdateStatusRequest = { status };
    return await api.patch<Ticket>(`/tickets/${id}/status`, request);
  }

  async deleteTicket(id: number) {
    await api.delete<void>(`/tickets/${id}`);
  }
}

export const ticketService = new TicketService();
