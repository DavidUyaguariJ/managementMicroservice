package com.udla.management.sales.services;

import com.udla.management.exceptions.ManagementException;
import com.udla.management.sales.models.SaleModel;
import com.udla.management.sales.repositories.SalesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SalesServiceTest {

    @Mock
    private SalesRepository salesRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private SalesService salesService;

    private SaleModel saleModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        saleModel = new SaleModel();
        saleModel.setDescription("test");
        saleModel.setQuantity(10);
        saleModel.setIdeSale(11L);
    }

    @Test
    void getSales() {
        when(salesRepository.findAll()).thenReturn(Arrays.asList(saleModel));
        assertNotNull(salesService.getSales());
    }


    @Test
    public void whenSaleDescriptionIsUnique() throws ManagementException {
        SaleModel inputSale = new SaleModel();
        inputSale.setDescription("Venta única");

        SaleModel mappedSale = new SaleModel();
        mappedSale.setDescription("Venta única");

        when(salesRepository.existsByDescription("Venta única")).thenReturn(false);

        when(modelMapper.map(inputSale, SaleModel.class)).thenReturn(mappedSale);

        salesService.createSale(inputSale);

        verify(salesRepository).save(mappedSale);
    }

    @Test
    public void whenSaleIsNull_thenThrowException() {
        assertThrows(NullPointerException.class, () -> {
            salesService.createSale(null);
        });
    }

    @Test
    public void whenUpdateSale() throws ManagementException {

        Long saleId = 1L;
        SaleModel inputSale = new SaleModel();
        inputSale.setIdeSale(saleId);
        inputSale.setDescription("Nueva descripción");

        SaleModel existingSale = new SaleModel();
        existingSale.setIdeSale(saleId);
        existingSale.setDescription("Descripción antigua");

        when(salesRepository.findById(saleId)).thenReturn(Optional.of(existingSale));

        doAnswer(invocation -> {
            SaleModel source = invocation.getArgument(0);
            SaleModel target = invocation.getArgument(1);
            if (source.getDescription() != null) {
                target.setDescription(source.getDescription());
            }
            return null;
        }).when(modelMapper).map(any(SaleModel.class), any(SaleModel.class));

        salesService.updateSale(inputSale);

        verify(salesRepository).save(existingSale);
        assertEquals("Nueva descripción", existingSale.getDescription());
    }

}