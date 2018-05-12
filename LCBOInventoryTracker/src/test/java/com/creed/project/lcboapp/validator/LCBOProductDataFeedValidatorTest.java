package com.creed.project.lcboapp.validator;

import com.creed.project.lcboapp.common.Constants;
import com.creed.project.lcboapp.domain.LCBOInventory;
import com.creed.project.lcboapp.domain.LCBOProduct;
import com.creed.project.lcboapp.exception.InvalidInventoryFieldValue;
import com.creed.project.lcboapp.exception.InvalidProductFieldValue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static org.junit.Assert.*;

public class LCBOProductDataFeedValidatorTest {

    private LCBOProduct lcboProductObject;

    @Before
    public void setUp() throws Exception {
        String data = "18,Heineken Lager,1350,1350,Beer,Lager,Netherlands,Heineken's Brouwerijen Nederland Bv,2011-01-07,2011-01-07 09:52:27.094054, , , , ";
        String[] tokens = data.split(",");

        lcboProductObject = new LCBOProduct();

        lcboProductObject.setId(Long.valueOf(tokens[0]));
        lcboProductObject.setName(String.valueOf(tokens[1]));
        lcboProductObject.setPriceInCents(Double.valueOf(tokens[2]));
        lcboProductObject.setRegularPriceInCents(Double.valueOf(tokens[3]));
        lcboProductObject.setPrimaryCategory(String.valueOf(tokens[4]));
        lcboProductObject.setSecondaryCategory(String.valueOf(tokens[5]));
        lcboProductObject.setOrigin(String.valueOf(tokens[6]));
        lcboProductObject.setProducerName(String.valueOf(tokens[7]));
        lcboProductObject.setReleasedOn(LocalDate.parse(tokens[8]));
        lcboProductObject.setUpdatedAt(LocalDateTime.parse(tokens[9], Constants.DATETIME_FORMAT));
        lcboProductObject.setImageUrl(String.valueOf(tokens[10]));
        lcboProductObject.setVarietal(String.valueOf(tokens[11]));
        lcboProductObject.setStyle(String.valueOf(tokens[12]));
        lcboProductObject.setTertiaryCategory(String.valueOf(tokens[13]));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test(expected = InvalidProductFieldValue.class)
    public void testValidateID() {
        lcboProductObject.setId(-1L);
        LCBOProductDataFeedValidator.validateAllBizRules(lcboProductObject);
    }

    @Test(expected = InvalidProductFieldValue.class)
    public void testValidateName() {
        lcboProductObject.setName("κόσμε");
        LCBOProductDataFeedValidator.validateAllBizRules(lcboProductObject);
    }

    @Test(expected = InvalidProductFieldValue.class)
    public void testValidatePriceInCents() {
        lcboProductObject.setPriceInCents(-1.00);
        LCBOProductDataFeedValidator.validateAllBizRules(lcboProductObject);
    }

    @Test(expected = InvalidProductFieldValue.class)
    public void testValidateRegularPriceInCents() {
        lcboProductObject.setRegularPriceInCents(-1.0);
        LCBOProductDataFeedValidator.validateAllBizRules(lcboProductObject);
    }

    @Test(expected = InvalidProductFieldValue.class)
    public void testValidatePrimaryCategory() {
        lcboProductObject.setPrimaryCategory("κόσμε");
        LCBOProductDataFeedValidator.validateAllBizRules(lcboProductObject);
    }

    @Test(expected = InvalidProductFieldValue.class)
    public void testValidateSecondaryCategory() {
        lcboProductObject.setSecondaryCategory("κόσμε");
        LCBOProductDataFeedValidator.validateAllBizRules(lcboProductObject);
    }

    @Test(expected = InvalidProductFieldValue.class)
    public void testValidateOrigin() {
        lcboProductObject.setOrigin("κόσμε");
        LCBOProductDataFeedValidator.validateAllBizRules(lcboProductObject);
    }

    @Test(expected = InvalidProductFieldValue.class)
    public void testValidateProducerName() {
        lcboProductObject.setProducerName("κόσμε");
        LCBOProductDataFeedValidator.validateAllBizRules(lcboProductObject);
    }

    @Test(expected = DateTimeParseException.class)
    public void testValidateReleasedOn() {
        lcboProductObject.setReleasedOn(LocalDate.parse("2011", Constants.DATETIME_FORMAT));
        LCBOProductDataFeedValidator.validateAllBizRules(lcboProductObject);
    }

    @Test(expected = DateTimeParseException.class)
    public void testValidateUpdatedAt() {
        lcboProductObject.setUpdatedAt(LocalDateTime.parse("2011-01-07 99:52:00", Constants.DATETIME_FORMAT));
        LCBOProductDataFeedValidator.validateAllBizRules(lcboProductObject);
    }

    @Test(expected = InvalidProductFieldValue.class)
    public void testImageUrl() {
        lcboProductObject.setImageUrl("κόσμε");
        LCBOProductDataFeedValidator.validateAllBizRules(lcboProductObject);
    }

    @Test(expected = InvalidProductFieldValue.class)
    public void testValidateVarietal() {
        lcboProductObject.setVarietal("κόσμε");
        LCBOProductDataFeedValidator.validateAllBizRules(lcboProductObject);
    }

    @Test(expected = InvalidProductFieldValue.class)
    public void testValidateStyle() {
        lcboProductObject.setStyle("κόσμε");
        LCBOProductDataFeedValidator.validateAllBizRules(lcboProductObject);
    }

    @Test(expected = InvalidProductFieldValue.class)
    public void testValidateTertiaryCategory() {
        lcboProductObject.setTertiaryCategory("κόσμε");
        LCBOProductDataFeedValidator.validateAllBizRules(lcboProductObject);
    }
}