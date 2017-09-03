package com.railway.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.railway.bean.Category;
import com.railway.dao.CategoryMapper;
import com.railway.service.CategoryService;

@Service("typeService") 
public class CategoryServiceImpl implements CategoryService{
	@Autowired
    private CategoryMapper typeDao;  
	@Override
	public List<Category> selectAll() {
		return typeDao.selectAll();
	}

}
