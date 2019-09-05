package com.danbai.ys.service;

import com.danbai.ys.entity.Gkls;
import com.danbai.ys.entity.VideoTime;
import com.danbai.ys.entity.Ysb;
import com.github.pagehelper.PageInfo;

import java.util.HashMap;
import java.util.List;
public interface YsService {
    List<Ysb> page(int page, int pagenum);
    int contYs();
    Ysb selectYsById(int id);
    List<Ysb> selectYsByPm(String pm);
    List<Ysb> selectYsByIdlist(int id);
    boolean addYs(Ysb ysb);
    boolean update(Ysb ysb);
    boolean delYs(Ysb ysb);
    PageInfo getYs(String lx, int page, int size);
    void addYsTime(VideoTime videoTime);
    float getYsTime(VideoTime videoTime);
    List<Gkls> getGkls(String username);
    HashMap getYsLs(String username, int ysid);
}
