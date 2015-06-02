package com.yueguang.dao;

import java.util.List;

import com.yueguang.model.Ticket;

public interface TicketDao {
	public void insertTicket(Ticket ticket);

	public void deleteTicketByTicketId(int ticketid);

	public void deleteTicketByMemberId(String memberid);

	public void updateTicket(Ticket ticket);

	public Ticket getTicketByTicketid(int ticketid);

	public List<Ticket> getTicketsByMemberid(String memberid);
	
	public List<Ticket> getTicketsByPlanid(int planid);

	public List<Ticket> getTicketsofCertainMonth(int year,int month);
}
