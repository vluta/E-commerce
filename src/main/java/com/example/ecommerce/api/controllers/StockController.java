package com.example.ecommerce.api.controllers;

import com.example.ecommerce.api.mappers.CategoryMapper;
import com.example.ecommerce.api.mappers.ProductMapper;
import com.example.ecommerce.api.mappers.StockMapper;
import com.example.ecommerce.config.ExchangeRatesConfig;
import com.example.ecommerce.exceptions.ApiRequestException;
import com.example.ecommerce.models.DTO.*;
import com.example.ecommerce.models.entities.Product;
import com.example.ecommerce.models.entities.Stock;
import com.example.ecommerce.services.CategoryService;
import com.example.ecommerce.services.ProductService;
import com.example.ecommerce.services.StockService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/stocks", produces = "application/json")
@CrossOrigin(origins = "*")
public class StockController {

    @Autowired
    private StockService stockService;
    private StockMapper stockMapper;
    private ProductService productService;
    private ProductMapper productMapper;

    public StockController(StockService stockService, StockMapper stockMapper, ProductService productService, ProductMapper productMapper) {
        this.stockService = stockService;
        this.stockMapper = stockMapper;
        this.productService = productService;
        this.productMapper = productMapper;
    }

    //get a stock by product-id
    @GetMapping("{product-id}")
    public ResponseEntity<List<StockDTO>> getStocksByProductId(@PathVariable("product-id") String id) throws IOException {
        List<Stock> stocks = stockService.findByProduct_IdOrderByCreationDateAsc(UUID.fromString(id));

        if(!stocks.isEmpty()) {

            // iterative implementation
            /*List<StockDTO> stockDTOS = null;
            StockDTO stockDTO;
            for (Stock stock : stocks) {
                stockDTO = stockMapper.toDto(stock);
                stockDTOS.add(stockDTO);
            }*/

            List<StockDTO> stockDTOS = stockService.findByProduct_IdOrderByCreationDateAsc(UUID.fromString(id)).stream()
                    .map(stock -> stockMapper.toDto(stock)).collect(Collectors.toList());

            return new ResponseEntity<>(stockDTOS, HttpStatus.OK);
        }
        else {
            throw new ApiRequestException(ApiRequestException.Exceptions.getDescription(ApiRequestException.Exceptions.ID_NOT_FOUND, id.toString()));
        }

    }

    //get the number of available products by product id
    @GetMapping("{product-id}/available")
    public ResponseEntity<Integer> getAvailableProductsByProductId(@PathVariable("product-id") String id) throws IOException {
        int nrOfAvailableProducts = stockService.findNrOfAvailableProductsById(UUID.fromString(id));

        // not sure if id not found scenarios can lead to other nrOfAvailableProducts default values
        if(nrOfAvailableProducts != 0) {
            return new ResponseEntity<>(nrOfAvailableProducts, HttpStatus.OK);
        }
        else {
            throw new ApiRequestException(ApiRequestException.Exceptions.getDescription(ApiRequestException.Exceptions.ID_NOT_FOUND, id.toString()));
        }
    }

    //create a stock
    @PostMapping(value = "{product-id}", consumes = "application/json")
    public ResponseEntity<StockDTO> createStock(@PathVariable("product-id") String id, @Valid @RequestBody StockCreationDTO stockCreationDTO, Errors errors) {

        if (errors.hasFieldErrors()) {
            throw new ApiRequestException(ApiRequestException.Exceptions.getDescription(ApiRequestException.Exceptions.BAD_INPUT));
        }

        //Product product = productMapper.toProduct(productCreationDTO);

        //ProductDTO productDTO = productMapper.toDto(product);
        //productService.save(product);
        //return new ResponseEntity<>(productDTO, HttpStatus.CREATED);

        Stock stock = stockMapper.toStock(stockCreationDTO);
        stock.setCreationDate(LocalDate.now());

        Optional<Stock> optStock = stockService.findById(UUID.fromString(id));
        if(optStock.isPresent()) {
            stock.setProduct(productService.findById(UUID.fromString(id)).get());
        }
        else {
            throw new ApiRequestException(ApiRequestException.Exceptions.getDescription(ApiRequestException.Exceptions.ID_NOT_FOUND, id.toString()));
        }

        stockService.save(stock);

        StockDTO stockDTO = stockMapper.toDto(stock);
        return new ResponseEntity<>(stockDTO, HttpStatus.CREATED);
    }

    //place an order and update stock
    @PatchMapping("/order")
    public ResponseEntity placeOrder(@Valid @RequestBody Order order, Errors errors) {

        //check if response statuses are ok
        try {
            stockService.updateStock(order);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {

            System.out.println("An exception occured.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            //throw new ApiRequestException(ApiRequestException.Exceptions.getDescription(ApiRequestException.Exceptions.ID_NOT_FOUND, id.toString()));
        }
    }

    @DeleteMapping("{stock-id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteStock (@PathVariable("stock-id") String id) {
        try {
            stockService.deleteById(UUID.fromString(id));
        } catch (EmptyResultDataAccessException e) {
            throw new ApiRequestException(ApiRequestException.Exceptions.getDescription(ApiRequestException.Exceptions.ID_NOT_FOUND, id.toString()));
        }
    }
}
