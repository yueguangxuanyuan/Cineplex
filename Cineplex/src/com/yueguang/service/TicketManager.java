package com.yueguang.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.yueguang.dao.TicketDao;
import com.yueguang.dao.VedioHallDao;
import com.yueguang.model.Member;
import com.yueguang.model.Plan;
import com.yueguang.model.Ticket;

public class TicketManager {
	TicketDao ticketDao;
	VedioHallDao vedioHallDao;

	public void setTicketDao(TicketDao ticketDao) {
		this.ticketDao = ticketDao;
	}

	public void setVedioHallDao(VedioHallDao vedioHallDao) {
		this.vedioHallDao = vedioHallDao;
	}

	// 返回已经卖掉的座位号（辅助买票）
	public List<Integer> getSoldSeatsByPlanid(int planid) {
		try {
			List<Ticket> tickets = ticketDao.getTicketsByPlanid(planid);
			ArrayList<Integer> soldseats = new ArrayList<Integer>(100);
			if (tickets != null) {
                for(Ticket ticket:tickets){
                	soldseats.add(ticket.getSeat());
                }
                soldseats.trimToSize();
                return soldseats;
			} else {
				return null;
			}

		} catch (Exception e) {
			return null;
		}
	}

	// 返回可以供买票的总个数
	public int getTotalTickets(String hallname) {
		try {
			return vedioHallDao.getVedioHallByName(hallname).getSeatcount();
		} catch (Exception e) {
			return -1;
		}
	}

	// 插入用户的买票信息
	public int buyTicket(String memberid, int planid, int seat, String payType) {
		try {
			//对并发操作的一个简单的处理
			List<Ticket> tickets = ticketDao.getTicketsByPlanid(planid);
			for(Ticket ticket: tickets){
				if(ticket.getSeat() == seat){
					return -1;
				}
			}
			
			Member member = new Member();
			member.setMemberid(memberid);
			Plan plan = new Plan();
			plan.setPlanid(planid);
			Ticket ticket = new Ticket();
			ticket.setMember(member);
			ticket.setPayType(payType);
			ticket.setPlan(plan);
			ticket.setSeat(seat);
			ticket.setBuytime(new Timestamp(new Date().getTime()));
			ticketDao.insertTicket(ticket);
			return ticket.getTicketid();
		} catch (Exception e) {
			return -1;
		}

	}
	
    //返回用户买的所有的票
	public List<Ticket> getTicketsByMemberid(String memberid){
		return ticketDao.getTicketsByMemberid(memberid);
	}
	
	//通过ticketid查找到票
	public Ticket getTicketByTicketid(int ticketid){
		return ticketDao.getTicketByTicketid(ticketid);
	}
}
