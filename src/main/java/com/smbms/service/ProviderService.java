package com.smbms.service;

import com.smbms.pojo.Provider;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ProviderService {
    List<Provider> findPros(String queryProCode,String queryProName);
    Provider findProById(Long id);
}
