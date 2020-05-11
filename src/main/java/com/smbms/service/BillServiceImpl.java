package com.smbms.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smbms.dao.BillMapper;
import com.smbms.pojo.Bill;
import com.smbms.pojo.BillExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BillServiceImpl implements BillService {
    @Autowired
    private BillMapper billMapper;
    @Override
    public PageInfo<Bill> findBills(int pageNum,int pageSize,String queryProductName,String queryProviderId) {
        PageHelper.startPage(pageNum,pageSize);
        BillExample example = new BillExample();
        BillExample.Criteria criteria = example.createCriteria();
        if(queryProductName!=null&&!queryProductName.equals("")){
            criteria.andProductNameLike("%"+queryProductName+"%");
        }
        if (queryProviderId!=null&&!queryProviderId.equals("")){

            criteria.andProviderIdEqualTo(Long.parseLong(queryProviderId));
        }

        List<Bill> bills = billMapper.selectByExample(example);
        PageInfo<Bill> pageInfo = new PageInfo(bills);
        return pageInfo;
    }

    @Override
    public Bill findBillById(Long id) {
        Bill bill = billMapper.selectByPrimaryKey(id);
        return bill;
    }

    @Override
    public void modBill(Bill bill) {
        billMapper.updateByPrimaryKey(bill);
    }

    @Override
    public int delBill(Long id) {
        return billMapper.deleteByPrimaryKey(id);
    }

}
