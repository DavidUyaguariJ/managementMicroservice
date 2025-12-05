package com.udla.management.sales.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;

import com.udla.management.exceptions.ManagementException;
import com.udla.management.sales.models.SaleModel;
import com.udla.management.sales.repositories.SalesRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

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
        MockitoAnnotations.openMocks(this);
        saleModel = new SaleModel();
        saleModel.setIdeSale(1L);
        saleModel.setDescription("Test Sale");
        saleModel.setQuantity(5);
    }

    // --------------------------------------------------------------------
    // GET ALL SALES
    // --------------------------------------------------------------------
    @Test
    void shouldReturnAllSales() {
        when(salesRepository.findAll()).thenReturn(Arrays.asList(saleModel));

        var result = salesService.getSales();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(salesRepository).findAll();
    }

    // --------------------------------------------------------------------
    // GET SALE BY ID
    // --------------------------------------------------------------------
    @Test
    void shouldReturnSaleById() throws ManagementException {
        when(salesRepository.findById(1L)).thenReturn(Optional.of(saleModel));

        SaleModel result = salesService.getSaleById(1L);

        assertNotNull(result);
        assertEquals("Test Sale", result.getDescription());
        verify(salesRepository).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenSaleNotFoundById() {
        when(salesRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ManagementException.class, () -> salesService.getSaleById(99L));
    }

    // --------------------------------------------------------------------
    // CREATE SALE
    // --------------------------------------------------------------------
    @Test
    void shouldCreateSaleWhenDescriptionIsUnique() throws ManagementException {
        SaleModel mappedSale = new SaleModel();
        mappedSale.setDescription("Test Sale");

        when(salesRepository.existsByDescription("Test Sale")).thenReturn(false);
        when(modelMapper.map(saleModel, SaleModel.class)).thenReturn(mappedSale);

        salesService.createSale(saleModel);

        verify(salesRepository).save(mappedSale);
    }

    @Test
    void shouldThrowExceptionWhenDescriptionExists() {
        when(salesRepository.existsByDescription("Test Sale")).thenReturn(true);

        assertThrows(
                ManagementException.class,
                () -> salesService.createSale(saleModel)
        );

        verify(salesRepository, never()).save(any());
    }

    // --------------------------------------------------------------------
    // UPDATE SALE
    // --------------------------------------------------------------------
    @Test
    void shouldUpdateSale() throws ManagementException {
        SaleModel existing = new SaleModel();
        existing.setIdeSale(1L);
        existing.setDescription("Old Description");

        when(salesRepository.findById(1L)).thenReturn(Optional.of(existing));

        doAnswer(invocation -> {
            SaleModel source = invocation.getArgument(0);
            SaleModel target = invocation.getArgument(1);

            if (source.getDescription() != null) {
                target.setDescription(source.getDescription());
            }
            return null;
        }).when(modelMapper).map(any(SaleModel.class), any(SaleModel.class));

        salesService.updateSale(saleModel);

        verify(salesRepository).save(existing);
        assertEquals("Test Sale", existing.getDescription());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingSale() {
        when(salesRepository.findById(99L)).thenReturn(Optional.empty());

        SaleModel input = new SaleModel();
        input.setIdeSale(99L);

        assertThrows(
                ManagementException.class,
                () -> salesService.updateSale(input)
        );
    }

    // --------------------------------------------------------------------
    // DELETE SALE
    // --------------------------------------------------------------------
    @Test
    void shouldDeleteSale() throws ManagementException {
        when(salesRepository.findById(1L)).thenReturn(Optional.of(saleModel));

        salesService.deleteSale(1L);

        verify(salesRepository).deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistingSale() {
        when(salesRepository.findById(100L)).thenReturn(Optional.empty());

        assertThrows(ManagementException.class, () -> salesService.deleteSale(100L));

        verify(salesRepository, never()).deleteById(anyLong());
    }

    // --------------------------------------------------------------------
    // PRIVATE METHOD: findSaleById (covered indirectly)
    // --------------------------------------------------------------------
    @Test
    void findSaleByIdShouldReturnSale() throws ManagementException {
        when(salesRepository.findById(1L)).thenReturn(Optional.of(saleModel));

        SaleModel result = salesService.getSaleById(1L);

        assertEquals("Test Sale", result.getDescription());
    }

    @Test
    void findSaleByIdShouldThrowException() {
        when(salesRepository.findById(50L)).thenReturn(Optional.empty());

        assertThrows(ManagementException.class, () -> salesService.getSaleById(50L));
    }
}
