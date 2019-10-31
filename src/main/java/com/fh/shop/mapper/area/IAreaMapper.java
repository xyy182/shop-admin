package com.fh.shop.mapper.area;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.po.area.Area;

import java.util.List;

public interface IAreaMapper extends BaseMapper<Area> {

    List<Area> areaList();

   /* void addArea(Area area);

    void deleteArea(List<Integer> list);

    void updateArea(Area area);*/
}
