package com.harsahaat.service;

import com.harsahaat.model.Seller;
import com.harsahaat.model.SellerReport;

public interface SellerReportService {
    SellerReport getSellerReport(Seller seller);
    SellerReport updateSellerReport(SellerReport sellerReport);

}
