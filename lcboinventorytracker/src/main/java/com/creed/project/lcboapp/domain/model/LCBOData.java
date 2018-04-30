package com.creed.project.lcboapp.domain.model;

import com.creed.project.lcboapp.domain.LCBOInventory;
import com.creed.project.lcboapp.domain.LCBOProduct;
import com.creed.project.lcboapp.domain.LCBOStore;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class LCBOData {
    private String name;

    private Set<LCBOInventory> inventoryObject = Collections.synchronizedSet(new HashSet<>());
    private Set<LCBOProduct> productObject = Collections.synchronizedSet(new HashSet<>());
    private Set<LCBOStore> storeObject = Collections.synchronizedSet(new HashSet<>());

    /**
     * Default Constructor
     */
    public LCBOData() {
        super();
    }

    /**
     * @param name      the cost feed file name
     * @param modelCode the cost model code
     */
    public LCBOData(String name, String modelCode) {
        this.name = name;
//        this.model.setModelCode(modelCode);
//        this.model.setModelCodeType(ModelCodeType.COST);
    }

    /**
     * @return the feed file name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the feed file name to set
     */
    public void setName(final String name) {
        this.name = name;
    }
//
//    /**
//     * @return the model
//     */
//    public Model getModel() {
//        return model;
//    }
//
//    /**
//     * @param model the model to set
//     */
//    public void setModel(Model model) {
//        this.model = model;
//    }
//
    /**
     * @return the LCBOInventory set
     */
    public Set<LCBOInventory> getInventory() {
        Set<LCBOInventory> clone = new HashSet<>();
        clone.addAll(inventoryObject);

        return clone;
    }

    /**
     * @return the LCBOProduct set
     */
    public Set<LCBOProduct> getProduct() {
        Set<LCBOProduct> clone = new HashSet<>();
        clone.addAll(productObject);

        return clone;
    }

    /**
     * @return the LCBOStore set
     */
    public Set<LCBOStore> getStore() {
        Set<LCBOStore> clone = new HashSet<>();
        clone.addAll(storeObject);

        return clone;
    }

    /**
     * @param inventoryObject the LCBOInventory set to set
     */
    public void setInventory(final Set<LCBOInventory> inventoryObject) {
        this.inventoryObject.clear();
        this.inventoryObject.addAll(inventoryObject);
    }

    /**
     * @param productObject the LCBOProduct set to set
     */
    public void setProduct(final Set<LCBOProduct> productObject) {
        this.productObject.clear();
        this.productObject.addAll(productObject);
    }

    /**
     * @param storeObject the LCBOStore set to set
     */
    public void setStore(final Set<LCBOStore> storeObject) {
        this.storeObject.clear();
        this.storeObject.addAll(storeObject);
    }

    /**
     * @param inventoryObject the LCBOInventory object to add
     */
    public void addInventory(final LCBOInventory inventoryObject) {
        this.inventoryObject.add(inventoryObject);
    }

    /**
     * @param productObject the LCBOProduct object to add
     */
    public void addProduct(final LCBOProduct productObject) {
        this.productObject.add(productObject);
    }

    /**
     * @param storeObject the LCBOStore object to add
     */
    public void addStore(final LCBOStore storeObject) {
        this.storeObject.add(storeObject);
    }

    @Override
    public String toString() {
        return "LCBOData{" +
                "name='" + name + '\'' +
                '}';
    }


}
