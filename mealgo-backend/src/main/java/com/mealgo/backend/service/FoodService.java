package com.mealgo.backend.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mealgo.backend.entity.Category;
import com.mealgo.backend.entity.Food;
import com.mealgo.backend.repository.CategoryRepository;
import com.mealgo.backend.repository.FoodRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;
    private final CategoryRepository categoryRepository;

    // lấy toàn bộ food
    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    public Food getFoodById(Long id) {
        return foodRepository.findById(id).orElse(null);
    }

    // search food
    public List<Food> searchFood(String keyword) {
        return foodRepository.findByNameContainingIgnoreCase(keyword);
    }

    // filter category
    public List<Food> filterByCategory(Long categoryId) {
        return foodRepository.findByCategoryId(categoryId);
    }

    // create food
    public Food createFood(
            String name,
            String description,
            Double price,
            Long categoryId,
            MultipartFile image) throws IOException {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Food food = new Food();

        food.setName(name);
        food.setDescription(description);
        food.setPrice(price);
        food.setCategory(category);

        // chỉ set ảnh nếu có upload
        if (image != null && !image.isEmpty()) {
            food.setImage_url(saveImage(image));
        }

        return foodRepository.save(food);
    }

    // update food
    public Food updateFood(
            Long id,
            String name,
            String description,
            Double price,
            Long categoryId,
            MultipartFile image) throws IOException {

        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food not found"));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        food.setName(name);
        food.setDescription(description);
        food.setPrice(price);
        food.setCategory(category);

        // nếu admin upload ảnh mới thì update ảnh
        if (image != null && !image.isEmpty()) {
            food.setImage_url(saveImage(image));
        }

        return foodRepository.save(food);
    }

    // delete food
    public void deleteFood(Long id) {
        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food not found"));
    
        String imageUrl = food.getImage_url();
    
        // chỉ xóa ảnh upload
        if (imageUrl != null && imageUrl.startsWith("/uploads")) {
    
            String filePath =
                    System.getProperty("user.dir")
                    + imageUrl.replace("/", File.separator);
    
            File file = new File(filePath);
    
            System.out.println("Delete image path: " + filePath);
    
            if (file.exists()) {
                boolean deleted = file.delete();
    
                if (deleted) {
                    System.out.println("Image deleted successfully");
                } else {
                    System.out.println("Failed to delete image");
                }
            } else {
                System.out.println("Image file not found");
            }
        }
    
        foodRepository.delete(food);
    }

    // save image local
    private String saveImage(MultipartFile file) throws IOException {

        if (file == null || file.isEmpty()) {
            return null;
        }
    
        String uploadDir =
                System.getProperty("user.dir")
                + File.separator
                + "uploads"
                + File.separator
                + "foods"
                + File.separator;
    
        File dir = new File(uploadDir);
    
        if (!dir.exists()) {
            dir.mkdirs();
        }
    
        String fileName =
                UUID.randomUUID()
                + "_"
                + file.getOriginalFilename();
    
        File destination =
                new File(uploadDir + fileName);
    
        file.transferTo(destination);
    
        return "/uploads/foods/" + fileName;
    }
}
