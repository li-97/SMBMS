package com.smbms.service;

import com.smbms.dao.ProviderMapper;
import com.smbms.pojo.Provider;
import com.smbms.pojo.ProviderExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProviderServiceImpl implements ProviderService {
    @Autowired
    private ProviderMapper providerMapper;

    @Override
    public List<Provider> findPros(String queryProCode,String queryProName) {
        ProviderExample example = new ProviderExample();
        ProviderExample.Criteria criteria = example.createCriteria();
        if (queryProCode!=null&&!queryProCode.equals("")){
            criteria.andProCodeLike("%"+queryProCode+"%");
        }
        if (queryProName!=null&&!queryProName.equals("")){
            criteria.andProNameLike("%"+queryProName+"%");
        }

        return providerMapper.selectByExample(example);
    }

    @Override
    public Provider findProById(Long id) {
        return providerMapper.selectByPrimaryKey(id);
    }

}
