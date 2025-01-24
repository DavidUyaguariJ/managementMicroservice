package com.udla.management.sales.services;

import com.udla.management.sales.models.SaleModel;

import java.util.List;

public interface ISalesService {


    public List<SaleModel> getSales();

    public boolean saveSale(SaleModel sales);

    public boolean deleteSale(Long id);

    public SaleModel findSale(Long id);

    public boolean editSale (SaleModel inventory);
}
