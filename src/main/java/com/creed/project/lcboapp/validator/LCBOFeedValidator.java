//package com.creed.project.lcboapp.validator;
//
//import org.apache.commons.lang3.StringUtils;
//
//import java.util.Set;
//
//public class LCBOFeedValidator {
//    private static final int APP_MODEL_CODE_LEN = 13;
//    private static final int OPTION_CODE_LEN = 5;
//    private static final int COST_MODEL_CODE_LEN = 4;
//    private static final int COUNTRY_CODE_LEN = 3;
//    private static final int EIM_CODE_LEN = 18;
//    private static final int EIM_CODE_DIV_POS = 13;
//
//    /**
//     * Default Constructor
//     */
//    private LCBOFeedValidator() {
//        throw new IllegalAccessError("Cost Feed Validation Policy Class");
//    }
//
//    /**
//     * Apply business rule to validate OCS Cost
//     *
//     * @param modelCode the cost model code
//     * @param entity    to validate
//     */
//    public static void validateAllBizRules(String modelCode, OCS entity) {
//        if (StringUtils.isNotBlank(entity.getAppliedModel())) {
//            validateAppliedModelCode(entity.getAppliedModel());
//            validateModelCodeWithAppliedModelCode(modelCode, entity.getAppliedModel());
//        }
//        if (StringUtils.isNotBlank(entity.getOption())) {
//            validateOptionCode(entity.getOption(), true);
//            validateModelCodeWithOptionCode(modelCode, entity.getOption());
//        }
//        if (StringUtils.isNotBlank(entity.getEim())) {
//            validateEIM(entity.getEim());
//            validateModelCodeWithEim(modelCode, entity.getEim());
//        }
//        validateModelCode(modelCode);
//        validateCountryCode(entity.getGeoRestriction(), true);
//        validateInfoType(entity.getInfoType());
//        validateGeoCode(entity.getGeoRestriction(), true);
//    }
//
//    /**
//     * Apply business rule to validate MFG Cost
//     *
//     * @param modelCode the cost model code
//     * @param entity    to validate
//     */
//    public static void validateAllBizRules(String modelCode, Manufacturing entity) {
//        validateModelCode(modelCode);
//        validateAppliedModelCode(entity.getAppliedModelCode());
//        validateAppliedModelNumber(entity.getAppliedModelNumber());
//    }
//
//    /**
//     * DPT-RULE-52 Validate: Applied Model Code is Structurally Valid
//     * The system expects the Applied Model Code to be present and composed of up to 13 alphanumeric characters
//     *
//     * @param appliedModelCode the applied model code to validate
//     */
//    private static void validateAppliedModelCode(String appliedModelCode) {
//        if (StringUtils.isAlphanumeric(appliedModelCode) && appliedModelCode.length() == APP_MODEL_CODE_LEN) {
//            return;
//        }
//        throw new InvalidAppliedModelCode(appliedModelCode);
//    }
//
//    private static void validateAppliedModelNumber(String appliedModelNumber) {
//        if (StringUtils.isNumeric(appliedModelNumber)) {
//            return;
//        }
//        throw new InvalidAppliedModelNumber(appliedModelNumber);
//    }
//
//    /**
//     * DPT-RULE-55 Validate: Option Code
//     * An Option code will be a 5-character alphanumeric code.
//     *
//     * @param optionCode the option code to validate
//     */
//    private static void validateOptionCode(String optionCode, boolean required) {
//        if (!required && StringUtils.isBlank(optionCode)) {
//            return;
//        }
//
//        if (StringUtils.isAlphanumeric(optionCode) && optionCode.length() == OPTION_CODE_LEN) {
//            return;
//        }
//
//        throw new InvalidOptionCode(optionCode);
//    }
//
//    public static void validateOptionCode(Set<String> optionCodes, boolean required) {
//        for (String code : optionCodes) {
//            validateOptionCode(code, required);
//        }
//    }
//
//    /**
//     * DPT-RULE-58 Validate: EIM Structure (18)
//     * An EIM (18 character version) will have eighteen alphanumeric characters.
//     * Hyphens (-) are also valid in the last five (5) characters.
//     *
//     * @param eim the EIM to validate
//     */
//    private static void validateEIM(String eim) {
//        if (StringUtils.isNotBlank(eim) && eim.length() == EIM_CODE_LEN) {
//            String part1 = eim.substring(0, EIM_CODE_DIV_POS);
//            String part2 = eim.substring(EIM_CODE_DIV_POS);
//
//            if (StringUtils.isAlphanumeric(part1) && part2.matches("^[a-zA-Z0-9\\-]{5}$")) {
//                return;
//            }
//        }
//        throw new InvalidEIM(eim);
//    }
//
//    /**
//     * DPT-RULE-62 Validate: Cost Model Code
//     * The system expects a Model Code to be present and composed of four (4) alphanumeric characters
//     *
//     * @param modelCode the cost model code to validate
//     */
//    private static void validateModelCode(String modelCode) {
//        if (StringUtils.isAlphanumeric(modelCode) && modelCode.length() == COST_MODEL_CODE_LEN) {
//            return;
//        }
//        throw new InvalidModelCode(modelCode);
//    }
//
//    /**
//     * DPT-RULE-63 Validate: OCS Cost Applied Model
//     * The system expects the Applied Model Code to be present only if a Model Code is provided.
//     *
//     * @param modelCode
//     */
//    private static void validateModelCodeWithAppliedModelCode(String modelCode, String appliedModelCode) {
//        if (StringUtils.isNotBlank(appliedModelCode) && StringUtils.isBlank(modelCode)) {
//            throw new MatchingModelCodeNotFound("Applied Model", appliedModelCode);
//        }
//    }
//
//    /**
//     * DPT-RULE-64 Validate: OCS Cost Spec code
//     * The system expects the Spec code to be present only if a Model Code is provided.
//     *
//     * @param modelCode
//     */
//    private static void validateModelCodeWithOptionCode(String modelCode, String optionCode) {
//        if (StringUtils.isNotBlank(optionCode) && StringUtils.isBlank(modelCode)) {
//            throw new MatchingModelCodeNotFound("Option Code", optionCode);
//        }
//    }
//
//    /**
//     * DPT-RULE-65 Validate: OCS Cost EIM code
//     * The system expects the EIM code to be present only if a Model Code is provided.
//     *
//     * @param modelCode
//     */
//    private static void validateModelCodeWithEim(String modelCode, String eim) {
//        if (StringUtils.isNotBlank(eim) && StringUtils.isBlank(modelCode)) {
//            throw new MatchingModelCodeNotFound("EIM", eim);
//        }
//    }
//
//    /**
//     * DPT-RULE-66 Validate: Cost Value
//     * The system expects a value to be present and in the form of a number with optional decimal places
//     * The associated record is discarded.
//     *
//     * @param costValue the cost value to validate
//     */
//    public static void validateCostValue(String costValue) {
//        if (StringUtils.isBlank(costValue)
//                || StringUtils.equalsIgnoreCase(costValue, Constants.COST_NO_COST)) {
//            return;
//        } else {
//            if (costValue.matches("^[\\+\\-]{0,1}[0-9]+[\\.]{0,1}[0-9]*$")) {
//                return;
//            }
//        }
//        throw new InvalidCostValue(costValue);
//    }
//
//    /**
//     * DPT-RULE-68 Validate: Country Code
//     * Status: Nissan to provide feedback
//     * A Country code will be a 3-character code.
//     *
//     * @param countryCode the country code to validate
//     */
//    private static void validateCountryCode(String countryCode, boolean required) {
//        if (!required && StringUtils.isBlank(countryCode)) {
//            return;
//        }
//
//        if (StringUtils.isAlpha(countryCode) && countryCode.length() == COUNTRY_CODE_LEN) {
//            return;
//        }
//
//        throw new InvalidCostCountryCode(countryCode);
//    }
//
//    public static void validateCountryCode(Set<String> countryCodes, boolean required) {
//        for (String code : countryCodes) {
//            validateCountryCode(code, required);
//        }
//    }
//
//    /**
//     * @param geoRestriction the given geo code - Region or Destination or Country
//     */
//    private static void validateGeoCode(String geoRestriction, boolean required) {
//        if (!required && StringUtils.isBlank(geoRestriction)) {
//            return;
//        }
//
//        for (GeoCode item : GeoCode.values()) {
//            if (StringUtils.equals(item.name(), geoRestriction)) {
//                return;
//            }
//        }
//
//        throw new GeoCodeNotFound(geoRestriction);
//    }
//
//    /**
//     * @param infoType the info type
//     */
//    private static void validateInfoType(String infoType) {
//        if (StringUtils.isBlank(infoType) || InfoType.get(infoType.toUpperCase()) == null) {
//            throw new InfoTypeNotFound(infoType);
//        }
//    }
//}
