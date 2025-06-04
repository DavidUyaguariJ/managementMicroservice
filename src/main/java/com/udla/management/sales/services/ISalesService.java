package com.udla.management.sales.services;

import com.udla.management.exceptions.ManagementException;
import com.udla.management.sales.models.SaleModel;

import java.util.List;

public interface ISalesService {


    public List<SaleModel> getSales();

    public void createSale(SaleModel sales) throws ManagementException;

    public void deleteSale(Long id) throws ManagementException;

    public SaleModel getSaleById(Long id) throws ManagementException;

    public void updateSale (SaleModel inventory) throws ManagementException;
}
