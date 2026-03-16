package com.sethy.service.ticket.dto;

import com.sethy.service.ticket.model.TicketPriority;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request object for creating a new ticket")
public class CreateTicketRequest {

    @Schema(description = "Title of the ticket", example = "Login issue")
    private String title;

    @Schema(description = "Detailed description of the ticket", example = "Unable to login to the dashboard")
    private String description;

    @Schema(description = "Priority level of the ticket", example = "MEDIUM")
    private TicketPriority priority;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TicketPriority getPriority() {
        return priority;
    }

    public void setPriority(TicketPriority priority) {
        this.priority = priority;
    }
}
