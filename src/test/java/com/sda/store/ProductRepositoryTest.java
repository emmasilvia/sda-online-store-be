package com.sda.store;

import com.sda.store.model.Category;
import com.sda.store.model.Product;
import com.sda.store.model.ProductType;
import com.sda.store.repository.CategoryRepository;
import com.sda.store.repository.ProductRepository;
import com.sda.store.repository.ProductSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void cleanup(){
        productRepository.findAll().forEach(product -> productRepository.delete(product));
        categoryRepository.findAll().forEach(category -> categoryRepository.delete(category));
    }

    @Test
    public void testSave(){
        Category cosmetics = new Category();
        cosmetics.setName("Cosmetics");

        cosmetics = categoryRepository.save(cosmetics);

        Product cocaCola = new Product();
        cocaCola.setName("Bioderma");
        cocaCola.setPrice(2.0);
        cocaCola.setCategory(cosmetics);
        cocaCola.setDescription("Descriere");
        cocaCola.setProductType(ProductType.COSMETICS);

        cocaCola = productRepository.save(cocaCola);

        Assertions.assertNotNull(cocaCola.getId());
        Assertions.assertNotNull(cocaCola.getCategory());
        Assertions.assertNotNull(cocaCola.getName());
    }

    @Test
    public void searchProductByName(){
        Product tv = new Product();
        tv.setName("LG");

        Product samsung = new Product();
        samsung.setName("Samsung");

        productRepository.save(tv);
        productRepository.save(samsung);

        Assertions.assertEquals(2,productRepository.count());
        Assertions.assertEquals(1,productRepository.findAll(ProductSpecification.withNameLike("LG")).size());
        Assertions.assertEquals(1,productRepository.findAll(ProductSpecification.withNameLike("Samsung")).size());
        Assertions.assertEquals(0,productRepository.findAll(ProductSpecification.withNameLike("Philips")).size());
    }

    @Test
    public void searchProductByProductType(){
        Product appliance = new Product();
        appliance.setProductType(ProductType.ELECTRONICS);

        Product cosmetics = new Product();
        cosmetics.setProductType(ProductType.COSMETICS);

        productRepository.save(appliance);
        productRepository.save(cosmetics);

        Assertions.assertEquals(1,productRepository.findAll(ProductSpecification.ofType(ProductType.ELECTRONICS)).size());
        Assertions.assertEquals(1,productRepository.findAll(ProductSpecification.ofType(ProductType.COSMETICS)).size());
        Assertions.assertEquals(0,productRepository.findAll(ProductSpecification.ofType(ProductType.CLEANING_PRODUCTS)).size());
    }

    @Test
    public void searchProductsByProductTypeAndName(){
        Product samsungPhone = new Product();
        samsungPhone.setName("Samsung");
        samsungPhone.setProductType(ProductType.ELECTRONICS);

        Product samsungTv = new Product();
        samsungTv.setName("Samsung");
        samsungTv.setProductType(ProductType.CLEANING_PRODUCTS);

        productRepository.save(samsungPhone);
        productRepository.save(samsungTv);

        Assertions.assertEquals(2, productRepository.findAll(ProductSpecification.withNameLike("Samsung")).size());
        Assertions.assertEquals(1,
                productRepository.findAll(ProductSpecification.withNameLike("Samsung").and(ProductSpecification.ofType(ProductType.ELECTRONICS))).size());

    }

    @Test
    public void searchProductsByPriceRange(){
        Product phone1 = new Product();
        phone1.setPrice(150.0);

        Product phone2 = new Product();
        phone2.setPrice(300.0);

        Product phone3 = new Product();
        phone3.setPrice(190.0);

        productRepository.save(phone1);
        productRepository.save(phone2);
        productRepository.save(phone3);

        Assertions.assertEquals(2, productRepository.findAll(ProductSpecification.withPriceInRange(0.0,200.0)).size());
        Assertions.assertEquals(1,productRepository.findAll(ProductSpecification.withPriceInRange(300.0,500.0)).size());
    }

    @Test
    public void searchProductsByCategoryId() {
        Category applianceCategory = new Category();
        applianceCategory.setName("Appliance_category");

        Category phonesCategory = new Category();
        phonesCategory.setName("Phone_category");

        categoryRepository.save(phonesCategory);
        categoryRepository.save(applianceCategory);

        Product appliance = new Product();
        appliance.setName("Appliance_1");
        appliance.setCategory(applianceCategory);

        productRepository.save(appliance);

        Product phone = new Product();
        phone.setName("Phone_1");
        phone.setCategory(phonesCategory);

        productRepository.save(phone);

        Product phone2 = new Product();
        phone2.setName("Phone_2");
        phone2.setCategory(phonesCategory);

        productRepository.save(phone2);

        Assertions.assertEquals(1,
                productRepository.findAll(ProductSpecification.withCategoryId(applianceCategory.getId())).size());

        Assertions.assertEquals(2,
                productRepository.findAll(ProductSpecification.withCategoryId(phonesCategory.getId())).size());
    }

}
