package cg.formatter;

import cg.model.Category;
import cg.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;
import java.util.Optional;

public class CategoryFormatter implements Formatter<Category> {

    private ICategoryService iCategoryService;

    @Autowired
    public CategoryFormatter (ICategoryService iCategoryService){
        this.iCategoryService = iCategoryService;
    }

    @Override
    public Category parse(String id, Locale locale) throws ParseException {
        Category categoryOptional = iCategoryService.findById(Long.parseLong(id));
        return categoryOptional;
    }

    @Override
    public String print(Category object, Locale locale) {
        return "[" + object.getIdCategory() + ", " + object.getNameCategory() + "]";
    }
}
