package com.project.dao.impl;

import com.project.base.BaseDaoImpl;
import com.project.dao.PicStoreDao;
import com.project.model.PicStore;
import org.springframework.stereotype.Repository;

@Repository("picStoreDaoImpl")
public class PicStoreDaoImpl extends BaseDaoImpl<PicStore> implements PicStoreDao{

}
