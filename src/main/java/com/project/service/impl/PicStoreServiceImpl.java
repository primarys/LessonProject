package com.project.service.impl;

import com.project.base.BaseDaoImpl;
import com.project.base.BaseServiceImpl;
import com.project.model.PicStore;
import com.project.service.PicStoreService;
import com.project.utils.Const;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class PicStoreServiceImpl extends BaseServiceImpl<PicStore> implements PicStoreService {

    @Resource(name = "picStoreDaoImpl")
    public void setBaseDao(BaseDaoImpl<PicStore> baseDao) {
        super.setBaseDao(baseDao);
    }

    // 获取默认的teacher头像
    public PicStore getDefaultTeacherHead() {
        String hql = "FROM PicStore WHERE url=:url";
        Map<String, Object> map = new HashMap<>();
        map.put("url", Const.IMG_DEFAULT_TEACHER_HEAD);
        return findUniqueByHql(hql, map);
    }

    // 获取默认的lesson封面
    public PicStore getDefaultLessonCover() {
        String hql = "FROM PicStore WHERE url=:url";
        Map<String, Object> map = new HashMap<>();
        map.put("url", Const.IMG_DEFAULT_LESSON_COVER);
        return findUniqueByHql(hql, map);
    }

}