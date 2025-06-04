package com.udla.management.sales.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udla.management.exceptions.ManagementException;
import com.udla.management.sales.models.SaleModel;
import com.udla.management.sales.services.SalesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SalesController.class)
class SalesControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private SalesService salesService;

    @Autowired
    private ObjectMapper objectMapper;

    private SaleModel saleModel;

    @BeforeEach
    void setUp() {
        saleModel = new SaleModel();
        saleModel.setIdeSale(1L);
        saleModel.setDescription("Test Sale");
        saleModel.setQuantity(10);
    }

    @Test
    void getAllSales() throws Exception {
        List<SaleModel> allSales = Arrays.asList(saleModel);

        given(salesService.getSales()).willReturn(allSales);

        mvc.perform(get("/sales")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].description", is(saleModel.getDescription())));
    }

    @Test
    void getSaleById_WhenSaleExists() throws Exception {
        given(salesService.getSaleById(1L)).willReturn(saleModel);

        mvc.perform(get("/sales/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ideSale", is(saleModel.getIdeSale().intValue())))
                .andExpect(jsonPath("$.description", is(saleModel.getDescription())));
    }

    @Test
    void getSaleById_WhenSaleNotExists() throws Exception {
        given(salesService.getSaleById(anyLong()))
                .willThrow(new ManagementException("Sale not found"));

        mvc.perform(get("/sales/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createSale_WithValidData() throws Exception {
        willDoNothing().given(salesService).createSale(any(SaleModel.class));

        mvc.perform(post("/sales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saleModel)))
                .andExpect(status().isCreated());

        then(salesService).should().createSale(any(SaleModel.class));
    }

    @Test
    void createSale_WithInvalidData() throws Exception {
        SaleModel invalidSale = new SaleModel();

        mvc.perform(post("/sales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidSale)))
                .andExpect(status().isUnprocessableEntity());

        then(salesService).shouldHaveNoInteractions();
    }

    @Test
    void updateSale_WithValidData() throws Exception {
        willDoNothing().given(salesService).updateSale(any(SaleModel.class));

        mvc.perform(put("/sales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saleModel)))
                .andExpect(status().isOk());

        then(salesService).should().updateSale(any(SaleModel.class));
    }

    @Test
    void updateSale_WhenSaleNotExists() throws Exception {
        willThrow(new ManagementException("Sale not found"))
                .given(salesService).updateSale(any(SaleModel.class));

        mvc.perform(put("/sales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saleModel)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteSale_WhenSaleExists() throws Exception {
        willDoNothing().given(salesService).deleteSale(anyLong());

        mvc.perform(delete("/sales/1"))
                .andExpect(status().isNoContent());

        then(salesService).should().deleteSale(1L);
    }

    @Test
    void deleteSale_WhenSaleNotExists() throws Exception {
        willThrow(new ManagementException("Sale not found"))
                .given(salesService).deleteSale(anyLong());

        mvc.perform(delete("/sales/99"))
                .andExpect(status().isNotFound());
    }
}