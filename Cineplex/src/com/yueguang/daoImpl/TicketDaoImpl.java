package com.yueguang.daoImpl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.yueguang.dao.BaseDao;
import com.yueguang.dao.TicketDao;
import com.yueguang.model.Ticket;

public class TicketDaoImpl implements TicketDao{
    BaseDao  baseDao;
    
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public void insertTicket(Ticket ticket) {
		baseDao.save(ticket);
	}

	public void deleteTicketByTicketId(int ticketid) {
        baseDao.delete(Ticket.class, ticketid);		
	}

	public void deleteTicketByMemberId(String memberid) {
        Session  session = baseDao.getNewSession();
        String  hql = "delete from  Ticket where  memberid='"+memberid+"'";
        session.createQuery(hql).executeUpdate();
        session.close();
	}

	public void updateTicket(Ticket ticket) {
		baseDao.update(ticket);
	}

	public Ticket getTicketByTicketid(int ticketid) {
        return  (Ticket) baseDao.load(Ticket.class, ticketid);		
	}

	public List<Ticket> getTicketsByMemberid(String memberid) {
        HashMap<String,String> condition= new HashMap<String,String>();
		condition.put("memberid", memberid);
        return baseDao.find(Ticket.class, condition);
	}

	public List<Ticket> getTicketsByPlanid(int planid) {
        HashMap<String,String> condition= new HashMap<String,String>();
		condition.put("planid", String.valueOf(planid));
        return baseDao.find(Ticket.class, condition);
	}

	public List<Ticket> getTicketsofCertainMonth(int year, int month) {
		List<Ticket> tickets = baseDao.getAllList(Ticket.class);
	
		//筛选掉不是本月的票
		Calendar calendar = Calendar.getInstance();
		for(int i = 0 ; i < tickets.size();i++){
			Ticket ticket = tickets.get(i);
			calendar.setTime(ticket.getBuytime());
			if(calendar.get(Calendar.YEAR)!=year || calendar.get(Calendar.MONTH)!=month){
				tickets.remove(i);
				i--;
				continue;
			}
		}
		return tickets;
	}

}
