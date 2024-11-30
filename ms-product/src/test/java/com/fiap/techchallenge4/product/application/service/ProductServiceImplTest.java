package com.fiap.techchallenge4.product.application.service;

import com.fiap.techchallenge4.product.application.controller.mapper.ProductMapper;
import com.fiap.techchallenge4.product.application.exception.NotFoundException;
import com.fiap.techchallenge4.product.application.exception.ValidationException;
import com.fiap.techchallenge4.product.application.service.impl.ProductServiceImpl;
import com.fiap.techchallenge4.product.core.entity.ProductData;
import com.fiap.techchallenge4.product.core.model.Product;
import com.fiap.techchallenge4.product.core.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setup() {
        ModelMapper modelMapper = new ModelMapper();
        productMapper = new ProductMapper(modelMapper);

        productService = new ProductServiceImpl(productRepository, productMapper);
    }

    @Test
    void findAll_deveRetornarListaDeProdutos() {
        List<Product> models = List.of(
                Product.builder().id(1L).name("Produto 1").stock(10).build(),
                Product.builder().id(2L).name("Produto 2").stock(5).build()
        );
        List<ProductData> dataModels = models.stream()
                .map(productMapper::toEntity)
                .toList();

        when(productRepository.findAll()).thenReturn(dataModels);

        List<Product> result = productService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Produto 1", result.get(0).getName());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void save_deveSalvarProdutoERetornarModelo() {
        Product model = Product.builder().id(1L).name("Produto 1").stock(10).build();
        ProductData dataModel = productMapper.toEntity(model);
        when(productRepository.save(any(ProductData.class))).thenReturn(dataModel);

        Product result = productService.save(model);

        assertNotNull(result);
        assertEquals("Produto 1", result.getName());
        verify(productRepository, times(1)).save(any());
    }

    @Test
    void saveAll_deveSalvarProdutoERetornarModelo() {
        List<Product> model = List.of(Product.builder()
                .id(1L)
                .name("Produto 1")
                .stock(10)
                .build());


        productService.saveAll(model);

        verify(productRepository, times(1)).saveAllAndFlush(anyList());
    }

    @Test
    void findBySkuId_deveRetornarProdutoExistente() {
        Product model = Product.builder().id(1L).skuId(123L).name("Produto 1").stock(3).build();
        ProductData dataModel = productMapper.toEntity(model);

        when(productRepository.findBySkuId(123L)).thenReturn(Optional.of(dataModel));

        Product result = productService.findBySkuId(123L);

        assertNotNull(result);
        assertEquals(123L, result.getSkuId());
        assertEquals("Produto 1", result.getName());
        verify(productRepository, times(1)).findBySkuId(123L);
    }

    @Test
    void findBySkuId_deveLancarNotFoundException() {
        when(productRepository.findBySkuId(999L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> productService.findBySkuId(999L));
        assertEquals("Produto com SKU ID 999 não encontrado", exception.getMessage());
        verify(productRepository, times(1)).findBySkuId(999L);
    }

    @Test
    void findByd_deveLancarNotFoundException() {
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> productService.findById(999L));
        assertEquals("Produto com ID 999 não encontrado", exception.getMessage());
        verify(productRepository, times(1)).findById(999L);
    }

    @Test
    void decreaseStock_deveLancarNotFoundException() {
        when(productRepository.findBySkuId(999L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> productService.decreaseStock(999L,15));
        assertEquals("Produto com SKU ID 999 não encontrado", exception.getMessage());
        verify(productRepository, times(1)).findBySkuId(999L);
    }



    @Test
    void decreaseStock_deveDiminuirEstoque() {
        Product product = new Product();
        product.setSkuId(1L);
        product.setStock(10);

        when(productRepository.findBySkuId(1L)).thenReturn(Optional.of(productMapper.toEntity(product)));

        doAnswer(invocation -> {
            ProductData savedProduct = invocation.getArgument(0);
            product.setStock(savedProduct.getStock());
            return savedProduct;
        }).when(productRepository).save(any(ProductData.class));

        productService.decreaseStock(1L, 5);

        assertEquals(5, product.getStock());
        verify(productRepository).save(any(ProductData.class));
    }

    @Test
    void decreaseStock_deveLancarValidationExceptionQuandoEstoqueInsuficiente() {
        Product model = Product.builder().id(1L).skuId(123L).name("Produto 1").stock(3).build();
        ProductData dataModel = productMapper.toEntity(model);
        when(productRepository.findBySkuId(123L)).thenReturn(Optional.of(dataModel));

        ValidationException exception = assertThrows(ValidationException.class, () -> productService.decreaseStock(123L, 5));
        assertEquals("Produto com SKU ID 123 estoque insuficiente para realizar a diminuição", exception.getMessage());
        verify(productRepository, times(1)).findBySkuId(123L);
    }

    @Test
    void addStock_deveAdicionarEstoque() {
        Product model = Product.builder().id(1L).skuId(123L).name("Produto 1").stock(10).build();

        when(productRepository.findBySkuId(1L)).thenReturn(Optional.of(productMapper.toEntity(model)));

        doAnswer(invocation -> {
            ProductData savedProduct = invocation.getArgument(0);
            model.setStock(savedProduct.getStock());
            return savedProduct;
        }).when(productRepository).save(any(ProductData.class));

        productService.addStock(1L, 5);

        assertEquals(15, model.getStock());
        verify(productRepository).save(any(ProductData.class));
    }

    @Test
    void addStock_deveLancarValidationExceptionQuandoQuantidadeInvalida() {
        Product model = Product.builder().id(1L).skuId(123L).name("Produto 1").stock(10).build();
        ProductData dataModel = productMapper.toEntity(model);

        when(productRepository.findBySkuId(123L)).thenReturn(Optional.of(dataModel));

        ValidationException exception = assertThrows(ValidationException.class, () -> productService.addStock(123L, -5));
        assertEquals("Produto com SKU ID 123 a quantidade para adicionar deve ser maior que zero", exception.getMessage());
        verify(productRepository, times(1)).findBySkuId(123L);
    }

    @Test
    void addStock_deveLancarValidationExceptionQuandoNaoEncontraroProduto (){

        when(productRepository.findBySkuId(123L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> productService.addStock(123L, -5));
        assertEquals("Produto com SKU ID 123 não encontrado", exception.getMessage());
        verify(productRepository, times(1)).findBySkuId(123L);
    }
}
