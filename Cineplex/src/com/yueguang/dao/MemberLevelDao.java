package com.yueguang.dao;

import java.util.List;

import com.yueguang.model.MemberLevel;

public interface MemberLevelDao {
   public List<MemberLevel>   getAll();
   
   public float  getDisCountByLevel(int level);
   
   public  MemberLevel getBylevel(int level);
}
