package com.tickoff.util;

import java.util.UUID;

public class UniqueId {
    public static String getUUID(){
        return UUID.randomUUID().toString();
    }
}
