package com.danbai.ys.service;

import com.danbai.ys.entity.Ysb;
import com.github.pagehelper.PageInfo;

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
}
