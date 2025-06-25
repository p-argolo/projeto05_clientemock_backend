package com.simulador.backend.model.transaction;

import lombok.Data;

@Data
public class Wifi {
    private String ssid;
    private String bssid;
    private SecurityType security;
}
