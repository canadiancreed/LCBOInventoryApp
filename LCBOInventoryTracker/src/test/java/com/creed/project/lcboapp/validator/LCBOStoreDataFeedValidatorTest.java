package com.creed.project.lcboapp.validator;

import com.creed.project.lcboapp.common.Constants;
import com.creed.project.lcboapp.domain.LCBOInventory;
import com.creed.project.lcboapp.domain.LCBOStore;
import com.creed.project.lcboapp.exception.InvalidInventoryFieldValue;
import com.creed.project.lcboapp.exception.InvalidStoreFieldValue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static org.junit.Assert.*;

public class LCBOStoreDataFeedValidatorTest {

    private LCBOStore lcboStoreObject;

    @Before
    public void setUp() throws Exception {
        String data = "742,f,Queens Quay & Jarvis,queens quay jarvis 10 lower toronto central torontocentral m5e1z2," +
                        "10 Lower Jarvis,,Toronto-Central,M5E1Z2,(416) 364-9114,(416) 364-3945,43.6441,-79.3697,1083," +
                        "21279,27147074,14645539,f,t,f,f,f,f,t,t,t,720,1020,570,1200,570,1200,570,1200,570,1260,570," +
                        "1260,540,1200,2011-01-07 02:29:13.273965";
        String[] tokens = data.split(",");

        lcboStoreObject = new LCBOStore();

        lcboStoreObject.setId(Long.valueOf(tokens[0]));
        lcboStoreObject.setAddressLineOne(String.valueOf(tokens[4]));
        lcboStoreObject.setAddressLineTwo(String.valueOf(tokens[5]));
        lcboStoreObject.setCity(String.valueOf(tokens[6]));
        lcboStoreObject.setPostalCode(String.valueOf(tokens[7]));
        lcboStoreObject.setLatitude(String.valueOf(tokens[10]));
        lcboStoreObject.setLongitude(String.valueOf(tokens[11]));
        lcboStoreObject.setUpdatedAt(LocalDateTime.parse(tokens[39], Constants.DATETIME_FORMAT));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test(expected = InvalidStoreFieldValue.class)
    public void testValidateID() {
        lcboStoreObject.setId(-1L);
        LCBOStoreDataFeedValidator.validateAllBizRules(lcboStoreObject);
    }

    @Test(expected = InvalidStoreFieldValue.class)
    public void testValidateAddressLineOne() {
        lcboStoreObject.setAddressLineOne("");
        LCBOStoreDataFeedValidator.validateAllBizRules(lcboStoreObject);
    }

    @Test(expected = InvalidStoreFieldValue.class)
    public void testValidateAddressLineTwo() {
        lcboStoreObject.setAddressLineTwo("κόσμε");
        LCBOStoreDataFeedValidator.validateAllBizRules(lcboStoreObject);
    }

    @Test(expected = InvalidStoreFieldValue.class)
    public void testValidateCity() {
        lcboStoreObject.setCity("");
        LCBOStoreDataFeedValidator.validateAllBizRules(lcboStoreObject);
    }

    @Test(expected = InvalidStoreFieldValue.class)
    public void testValidatePostalCode() {
        lcboStoreObject.setPostalCode("");
        LCBOStoreDataFeedValidator.validateAllBizRules(lcboStoreObject);
    }

    @Test(expected = InvalidStoreFieldValue.class)
    public void testValidateLatitude() {
        lcboStoreObject.setLatitude("");
        LCBOStoreDataFeedValidator.validateAllBizRules(lcboStoreObject);
    }

    @Test(expected = InvalidStoreFieldValue.class)
    public void testValidateLongitude() {
        lcboStoreObject.setLongitude("");
        LCBOStoreDataFeedValidator.validateAllBizRules(lcboStoreObject);
    }

    @Test(expected = DateTimeParseException.class)
    public void testValidateUpdatedAt() {
        lcboStoreObject.setUpdatedAt(LocalDateTime.parse("2011-01-01 99:55:34", Constants.DATETIME_FORMAT));
        LCBOStoreDataFeedValidator.validateAllBizRules(lcboStoreObject);
    }

    @Test(expected = DateTimeParseException.class)
    public void testValidateUpdatedAtWithZeroSeconds() {
        lcboStoreObject.setUpdatedAt(LocalDateTime.parse("2011-01-01 99:55:00", Constants.DATETIME_FORMAT));
        LCBOStoreDataFeedValidator.validateAllBizRules(lcboStoreObject);
    }

    @Test
    public void testValidateUpdatedAtWithZeroSecondsNoExp() {
        lcboStoreObject.setUpdatedAt(LocalDateTime.parse("2011-01-01 09:55:00", Constants.DATETIME_FORMAT));
        LCBOStoreDataFeedValidator.validateAllBizRules(lcboStoreObject);
    }
}