package com.harsahaat.service;

import com.harsahaat.model.Deal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DealService  {
    List<Deal> getDeal();
    Deal createDeal(Deal deal);
    Deal updateDeal(Deal deal,Long id) throws Exception;
    void deleteDeal(Long id) throws Exception;

}
