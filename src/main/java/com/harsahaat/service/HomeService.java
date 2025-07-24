package com.harsahaat.service;

import com.harsahaat.model.Home;
import com.harsahaat.model.HomeCategory;

import java.util.List;

public interface HomeService {
    public Home createHomePageData(List<HomeCategory> allCategories);
}
