package com.udla.management.sales.services;

import com.udla.management.sales.models.SaleModel;
import com.udla.management.sales.repositories.SalesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalesService implements ISalesService{

    private final SalesRepository salesRepository;

    @Override
    public List<SaleModel> getSales(){
        return salesRepository.findAll();
    };

    @Override
    public boolean saveSale(SaleModel sale){
        salesRepository.save(sale);
        return true;
    };

    @Override
    public boolean deleteSale(Long id){
        if(salesRepository.existsById(id)){
            salesRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public SaleModel findSale(Long id){
        return salesRepository.findById(id).orElse(null);
    }

    @Override
    public boolean editSale(SaleModel sale){
            if(salesRepository.existsById(sale.getIdeSale())){
                salesRepository.save(sale);
                return true;
            }
        return false;
    }
}
