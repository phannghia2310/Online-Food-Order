package com.example.backendjavaspring.controller.implement;

import com.example.backendjavaspring.controller.ProductController;
import com.example.backendjavaspring.model.compositekey.ProductCategoryKey;
import com.example.backendjavaspring.model.dto.ProductBodyRequestDTO;
import com.example.backendjavaspring.model.dto.ProductDTO;
import com.example.backendjavaspring.model.dto.ResponseDTO;
import com.example.backendjavaspring.model.entity.Category;
import com.example.backendjavaspring.model.entity.Image;
import com.example.backendjavaspring.model.entity.Product;
import com.example.backendjavaspring.model.entity.ProductCategory;
import com.example.backendjavaspring.service.*;
import com.example.backendjavaspring.service.implement.ImageServiceImp;
import com.example.backendjavaspring.service.implement.ProductCategoryServiceImp;
import com.example.backendjavaspring.service.implement.UpLoadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static com.example.backendjavaspring.model.Constants.EXTENSION_IMAGE;


@RestController
@RequestMapping("/api/products")
public class ProductControllerImp implements ProductController {

    private final ProductService productService;
    private final UpLoadFileService upLoadFileService;
    private final ImageService imageService;
    private final CategoryService categoryService;
    private final ProductCategoryService productCategoryService;

    @Autowired
    public ProductControllerImp(ProductService productService, UpLoadFileService upLoadFileService, ImageService imageService, CategoryService categoryService, ProductCategoryService productCategoryService) {
        this.productService = productService;
        this.upLoadFileService = upLoadFileService;
        this.imageService = imageService;
        this.categoryService = categoryService;
        this.productCategoryService = productCategoryService;
    }


    @Override
    public ResponseEntity<?> getAllProduct() {
        List<Product> productList = productService.getAllProduct();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(200, "THÀNH CÔNG!", productList));
    }

    @Override
    @PreAuthorize("hasPermission('ADMIN','ADMIN')")
    public ResponseEntity<?> addProduct(ProductBodyRequestDTO productBodyRequest) {
        List<Product> productResult = new ArrayList<>();
        try {
            boolean isNullOrEmpty = productBodyRequest.getImageFiles() == null || productBodyRequest.getImageFiles().length == 0;
            boolean isEmage = checkFileExtension(productBodyRequest.getImageFiles());
            if (!isEmage || isNullOrEmpty) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(404, "ĐỊNH DẠNG FILE KHÔNG ĐÚNG HOẶC RỖNG!", null));
            }

            List<Category> categories = categoryService.findCategoriesById(productBodyRequest.getCategoryId());
            if (categories == null) throw new Exception();

            List<String> urls = upLoadFileService.upLoadFile(productBodyRequest.getImageFiles());
            List<Image> images = new ArrayList<>();
            for (String url : urls) {
                images.add(new Image(url));
            }
            Product product = new Product(productBodyRequest.getName(), productBodyRequest.getPrice(), productBodyRequest.getDes(), productBodyRequest.getAmount(), images);
            Product result = productService.createProduct(product);

            for (Category category : categories) {
                ProductCategoryKey key = new ProductCategoryKey(result.getProductId(), category.getCategoryId());
                ProductCategory productCategory = new ProductCategory(key, result, category);
                productCategoryService.createProductCategory(productCategory);
            }
            productResult.add(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(404, "KHÔNG TÌM THẤY LOẠI SẢN PHẨM! ", productResult));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(200, "THÊM SẢN PHẨM THÀNH CÔNG!", productResult));
    }

    @Override
    @PreAuthorize("hasPermission('ADMIN','ADMIN')")
    public ResponseEntity<?> updateProduct(ProductBodyRequestDTO productBodyRequest, long id) {
        List<Product> productResult = new ArrayList<>();
        try {
            Product productDB = productService.findProductById(id);
            if (productDB == null) throw new Exception();

            long[] newImageId = productBodyRequest.getImageId();
            MultipartFile[] newImageFile = productBodyRequest.getImageFiles();

            Set<Image> newImages = new HashSet<>();
            if (newImageId != null && newImageId.length != 0) {
                for (long imageId : newImageId) {
                    Image image = imageService.findImageById(imageId);
                    if (image != null) {
                        newImages.add(image);
                    }
                }
            }
            if (newImageFile != null && newImageFile.length != 0 && newImageFile[0].getSize() != 0) {
                List<String> urls = upLoadFileService.upLoadFile(newImageFile);
                for (String url : urls) {
                    Image image = new Image(url);
                    newImages.add(image);
                }
            }

            List<Image> oldImages = productDB.getImages();
            for (Image image : oldImages) {
                imageService.deleteImage(image.getImageId());
            }

            productDB.setImages(new ArrayList<>(newImages));
            productDB.setProductPrice(productBodyRequest.getPrice());
            productDB.setProductDes(productBodyRequest.getDes());
            productDB.setProductName(productBodyRequest.getName());
            productDB.setProductAmount(productBodyRequest.getAmount());
            Product result = productService.updateProduct(productDB);

            if (productBodyRequest.getCategoryId() != null && productBodyRequest.getCategoryId().length != 0) {
                List<ProductCategory> productCategories = productCategoryService.findProductCategoryByProductId(id);
                for (ProductCategory pc : productCategories) {
                    productCategoryService.deleteProductCategoryById(pc.getProductCategoryKey());
                }
                List<Category> categories = categoryService.findCategoriesById(productBodyRequest.getCategoryId());
                if (categories == null) throw new Exception();
                for (Category category : categories) {
                    ProductCategoryKey key = new ProductCategoryKey(result.getProductId(), category.getCategoryId());
                    ProductCategory productCategory = new ProductCategory(key, result, category);
                    productCategoryService.updateProductCategory(productCategory);
                }
            }

            productResult.add(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(404, "CẬP NHẬT THẤT BẠI!", productResult));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(200, "CẬP NHẬT THÀNH CÔNG!", productResult));
    }

    @Override
    @PreAuthorize("hasPermission('ADMIN','ADMIN')")
    public ResponseEntity<?> deleteProduct(long id) {
        try {
            List<ProductCategory> productCategories = productCategoryService.findProductCategoryByProductId(id);
            for (ProductCategory productCategory : productCategories) {
                productCategoryService.deleteProductCategoryById(productCategory.getProductCategoryKey());
            }
            productService.deleteProduct(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(404, "XOÁ SẢN PHẨM KHÔNG THÀNH CÔNG!", new ArrayList<>()));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(200, "XOÁ SẢN PHẨM THÀNH CÔNG!", new ArrayList<>()));
    }

    @Override
    public ResponseEntity<?> findProductByFields(HashMap<String, String> map) {
        List<Product> productResult = productService.findProductsByFields(map);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(200, "THÀNH CÔNG!", productResult));
    }

    @Override
    public ResponseEntity<?> findProductById(long id) {
        Product product = productService.findProductById(id);
        List<ProductDTO> productResult = new ArrayList<>();
        if (product != null) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductId(product.getProductId());
            productDTO.setProductDes(product.getProductDes());
            productDTO.setProductPrice(product.getProductPrice());
            productDTO.setProductAmount(product.getProductAmount());
            productDTO.setProductName(product.getProductName());
            productDTO.setAmountSold(product.getAmountSold());
            productDTO.setCreateDate(product.getCreateDate());
            productDTO.setImages(product.getImages());
            productDTO.setProductMass(product.getProductMass());
            List<ProductCategory> productCategories = productCategoryService.findProductCategoryByProductId(product.getProductId());
            List<Category> categories = new ArrayList<>();
            for (ProductCategory productCategory : productCategories) {
                categories.add(productCategory.getCategory());
            }
            productDTO.setCategories(categories);
            productResult.add(productDTO);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(200, "THÀNH CÔNG!", productResult));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(404, "KHÔNG TÌM THẤY SẢN PHẨM CÓ ID = " + id, productResult));
    }

    @Override
    public ResponseEntity<?> getProductNew() {
        try {
            List<Product> productResult = productService.getProductsNew();

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(200, "THÀNH CÔNG!", productResult));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(200, "THÀNH CÔNG!", null));

        }
    }

    @Override
    public ResponseEntity<?> getProductHot() {
        List<Product> productResult = productService.getProductsHot();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(200, "THÀNH CÔNG!", productResult));
    }

    @Override
    public ResponseEntity<?> getProductByCategory(long id) {
        List<ProductCategory> productCategories = productCategoryService.findProductCategoryByCategoryId(id);
        List<Product> productResult = new ArrayList<>();
        for (ProductCategory productCategory : productCategories) {
            productResult.add(productCategory.getProduct());
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(200, "SUCCESSFULLY!", productResult));
    }

    private boolean checkFileExtension(MultipartFile[] files) {
        for (MultipartFile file : files) {
            String fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            if (fileExtension != null) {
                if (!EXTENSION_IMAGE.contains(fileExtension)) {
                    return false;
                }
            }
        }
        return true;
    }


}
