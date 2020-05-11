package com.smbms.service;

import com.github.pagehelper.PageInfo;
import com.smbms.pojo.Bill;

public interface BillService {
PageInfo<Bill> findBills(int pageNum,int pageSize,String queryProductName,String queryProviderId);
Bill findBillById(Long id);
void modBill(Bill bill);
int delBill(Long id );

}
