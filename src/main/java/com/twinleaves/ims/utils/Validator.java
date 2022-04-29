package com.twinleaves.ims.utils;

import com.twinleaves.ims.model.Inventory;
import org.springframework.stereotype.Service;

@Service
public class Validator {

    public boolean isValidInventory(final Inventory inventory) {
        return true;
    }
}
