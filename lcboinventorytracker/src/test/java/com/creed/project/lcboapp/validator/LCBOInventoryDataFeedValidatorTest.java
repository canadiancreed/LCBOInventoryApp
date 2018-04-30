package com.creed.project.lcboapp.validator;

import com.creed.project.lcboapp.common.Constants;
import com.creed.project.lcboapp.domain.LCBOInventory;
import com.creed.project.lcboapp.exception.InvalidInventoryFieldValue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class LCBOInventoryDataFeedValidatorTest {

    private LCBOInventory lcboInventoryObject;

    @Before
    public void setUp() throws Exception {
        String data = "1,18,632,779,2011-01-05,2011-01-01 03:55:34,2011-01-07 09:52:27";
        String[] tokens = data.split(",");

        lcboInventoryObject = new LCBOInventory();

        lcboInventoryObject.setId(Long.valueOf(tokens[0]));
        lcboInventoryObject.setProductID(Integer.parseInt(tokens[1]));
        lcboInventoryObject.setStoreID(Integer.parseInt(tokens[2]));
        lcboInventoryObject.setQuantity(Integer.parseInt(tokens[3]));
        lcboInventoryObject.setUpdatedOn(LocalDate.parse(tokens[4]));
        lcboInventoryObject.setCreatedAt(LocalDateTime.parse(tokens[5], Constants.DATETIME_FORMAT));
        lcboInventoryObject.setUpdatedAt(LocalDateTime.parse(tokens[6], Constants.DATETIME_FORMAT));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test(expected = InvalidInventoryFieldValue.class)
    public void testValidateProductID() {
        lcboInventoryObject.setProductID(-1);
        LCBOInventoryDataFeedValidator.validateAllBizRules(lcboInventoryObject);
    }

    @Test(expected = InvalidInventoryFieldValue.class)
    public void testValidateStoreID() {
        lcboInventoryObject.setStoreID(-1);
        LCBOInventoryDataFeedValidator.validateAllBizRules(lcboInventoryObject);
    }

    @Test(expected = InvalidInventoryFieldValue.class)
    public void testValidateQuantity() {
        lcboInventoryObject.setQuantity(-1);
        LCBOInventoryDataFeedValidator.validateAllBizRules(lcboInventoryObject);
    }

    @Test(expected = DateTimeParseException.class)
    public void testValidateUpdatedOn() {
        lcboInventoryObject.setUpdatedOn(LocalDate.parse("2011"));
        LCBOInventoryDataFeedValidator.validateAllBizRules(lcboInventoryObject);
    }

    @Test(expected = DateTimeParseException.class)
    public void testValidateUpdatedAt() {
        lcboInventoryObject.setUpdatedAt(LocalDateTime.parse("2011-01-07 99:52:27", Constants.DATETIME_FORMAT));
        LCBOInventoryDataFeedValidator.validateAllBizRules(lcboInventoryObject);
    }

    @Test(expected = DateTimeParseException.class)
    public void testValidateUpdatedAtWithZeroSeconds() {
        lcboInventoryObject.setUpdatedAt(LocalDateTime.parse("2011-01-07 99:52:00", Constants.DATETIME_FORMAT));
        LCBOInventoryDataFeedValidator.validateAllBizRules(lcboInventoryObject);
    }

    @Test
    public void testValidateUpdatedAtWithZeroSecondsNoExp() {
        lcboInventoryObject.setUpdatedAt(LocalDateTime.parse("2011-01-07 09:52:00", Constants.DATETIME_FORMAT));
        LCBOInventoryDataFeedValidator.validateAllBizRules(lcboInventoryObject);
    }

    @Test(expected = DateTimeParseException.class)
    public void testValidateCreatedAt() {
        lcboInventoryObject.setCreatedAt(LocalDateTime.parse("2011-01-01 99:55:34", Constants.DATETIME_FORMAT));
        LCBOInventoryDataFeedValidator.validateAllBizRules(lcboInventoryObject);
    }

    @Test(expected = DateTimeParseException.class)
    public void testValidateCreatedAtWithZeroSeconds() {
        lcboInventoryObject.setCreatedAt(LocalDateTime.parse("2011-01-01 99:55:00", Constants.DATETIME_FORMAT));
        LCBOInventoryDataFeedValidator.validateAllBizRules(lcboInventoryObject);
    }

    @Test
    public void testValidateCreatedAtWithZeroSecondsNoExp() {
        lcboInventoryObject.setCreatedAt(LocalDateTime.parse("2011-01-01 09:55:00", Constants.DATETIME_FORMAT));
        LCBOInventoryDataFeedValidator.validateAllBizRules(lcboInventoryObject);
    }
}