package com.smbms.controller;

import com.github.pagehelper.PageInfo;
import com.smbms.pojo.Bill;
import com.smbms.pojo.Provider;
import com.smbms.pojo.User;
import com.smbms.service.BillService;
import com.smbms.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/bill")
public class BillController {
    @Autowired
    private BillService billService;
    @Autowired
    private ProviderService providerService;
    @RequestMapping("/list")
    public String getbills(Model model,String pageIndex,String queryProductName,String queryProviderId){
        int parseInt=1;
        if (pageIndex!=null){
             parseInt = Integer.parseInt(pageIndex);
        }
        PageInfo<Bill> bills = billService.findBills(parseInt, 5,queryProductName,queryProviderId);
        model.addAttribute("billpageinfo",bills);
        List<Provider> pros = providerService.findPros(null,null);
        model.addAttribute("providerList",pros);

        return "billlist";
    }
    @RequestMapping("/view/{method}/{id}")
    public String billview(Model model,@PathVariable("id") String id,@PathVariable("method") String method){

        Bill billById = billService.findBillById(Long.parseLong(id));
        billById.setProvider(providerService.findProById(billById.getProviderId()));

        model.addAttribute("bill",billById);
        if(method.equals("mod")){
            return "billmodify";
        }else {
            return "billview";
        }

    }

    @RequestMapping("/modify")
    public String modify(String id,String billCode,String productName,String productUnit,String productCount ,String totalPrice,String providerId,String isPayment){
        Bill bill = new Bill();
        bill.setId(Long.parseLong(id));
        bill.setBillCode(billCode);
        bill.setProductName(productName);
        bill.setProductUnit(productUnit);
        bill.setProductCount(new BigDecimal(productCount));
        bill.setTotalPrice(new BigDecimal(totalPrice));
        bill.setProviderId(Long.parseLong(providerId));
        bill.setIsPayment(Integer.parseInt(isPayment));
        billService.modBill(bill);
        return "billlist";
    }
    @RequestMapping(value = "/del/{id}")
    @ResponseBody
    public Map<String, String> delUser(@PathVariable("id") String id){

        int i = billService.delBill(Long.parseLong(id));
        Map<String,String> map=new HashMap<>();

        if(i>0){
            map.put("delResult","true");
        }else {
            map.put("delResult","false");
        }
        return map;

    }
}
