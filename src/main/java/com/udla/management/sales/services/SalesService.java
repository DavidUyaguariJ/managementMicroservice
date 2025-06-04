package com.udla.management.sales.services;

import com.udla.management.exceptions.ManagementException;
import com.udla.management.sales.models.SaleModel;
import com.udla.management.sales.repositories.SalesRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalesService implements ISalesService {

    private final SalesRepository salesRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<SaleModel> getSales() {
        return salesRepository.findAll();
    }

    @Override
    public SaleModel getSaleById(Long id) throws ManagementException {
        return findSaleById(id);
    }

    @Override
    public void createSale(SaleModel sale) throws ManagementException {
        if (salesRepository.existsByDescription(sale.getDescription())) {
            throw new ManagementException("Ya existe el registro con ese nombre");
        }
        SaleModel newSale = modelMapper.map(sale, SaleModel.class);
        salesRepository.save(newSale);
    }

    @Override
    public void updateSale(SaleModel sale) throws ManagementException {
        SaleModel existingSale = findSaleById(sale.getIdeSale());
        modelMapper.map(sale, existingSale);
        salesRepository.save(existingSale);
    }

    @Override
    public void deleteSale(Long id) throws ManagementException {
        findSaleById(id);
        salesRepository.deleteById(id);
    }

    private SaleModel findSaleById(Long id) throws ManagementException {
        return salesRepository.findById(id)
                .orElseThrow(() -> new ManagementException(String.format("Venta con Id: %1$s no encontrada" , id)));
    }
}
