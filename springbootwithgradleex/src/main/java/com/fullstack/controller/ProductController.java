package com.fullstack.controller;

import com.fullstack.entity.Product;
import com.fullstack.exception.RecordNotFoundException;
import com.fullstack.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @PostMapping("/add-product")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.save(product));
    }

    @PostMapping("/saveall")
    public ResponseEntity<List<Product>> saveAllProduct(@RequestBody List<Product> productList) {
        return ResponseEntity.ok(productService.saveAll(productList));
    }


    @GetMapping("/findbyid/{productId}")
    public ResponseEntity<Optional<Product>> findById(@PathVariable int productId) {
        return ResponseEntity.ok(productService.findById(productId));
    }

    @GetMapping("/findbyname")
    public ResponseEntity<List<Product>> findByName(@RequestParam String productName) {
        return ResponseEntity.ok(productService.findByName(productName));
    }


    @GetMapping("/findbyany/{input}")
    public ResponseEntity<List<Product>> findByAny(@PathVariable String input) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");


        return ResponseEntity.ok(productService.findAll().stream().filter(prod -> prod.getProductName().equals(input)
                || String.valueOf(prod.getProductId()).equals(input)
                || prod.getProductCategory().equals(input)
                || simpleDateFormat.format(prod.getProductLaunchDate()).equals(input)).toList());

    }

    @GetMapping("/findall")
    public ResponseEntity<List<Product>> findAll(){
        return ResponseEntity.ok(productService.findAll());
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<Product> update(@PathVariable int productId, @RequestBody Product product){
        Product product1 = productService.findById(productId).orElseThrow(()-> new RecordNotFoundException("Product #ID Does Not Exist"));

        product1.setProductName(product.getProductName());
        product1.setProductPrice(product.getProductPrice());
        product1.setProductCategory(product.getProductCategory());
        product1.setProductLaunchDate(product.getProductLaunchDate());

        return ResponseEntity.ok(productService.update(product1));
    }


    @PatchMapping("/changeprice/{productId}/{productPrice}")
    public ResponseEntity<Product> changePrice(@PathVariable int productId, double productPrice){
        Product product = productService.findById(productId).orElseThrow(()-> new RecordNotFoundException("Product #ID Does Not Exist"));

        product.setProductPrice(productPrice);

        return ResponseEntity.ok(productService.update(product));
    }

    @DeleteMapping("/deletebyid/{productId}")
    public ResponseEntity<String> deleteById(@PathVariable int productId){
        productService.deleteById(productId);
        return ResponseEntity.ok("Product Data Deleted Successfully");
    }

    @GetMapping("/sortbyprice")
    public ResponseEntity<List<Product>> sortByPrice(){
        return ResponseEntity.ok(productService.findAll().stream().sorted(Comparator.comparing(Product::getProductPrice)).toList());
    }
}
