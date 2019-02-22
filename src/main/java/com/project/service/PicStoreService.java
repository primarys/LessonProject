package com.project.service;

import com.project.base.BaseService;
import com.project.model.PicStore;

public interface PicStoreService extends BaseService<PicStore> {
    // 获取默认的teacher头像
    public PicStore getDefaultTeacherHead();
    // 获取默认的Lesson封面
    public PicStore getDefaultLessonCover();
}
