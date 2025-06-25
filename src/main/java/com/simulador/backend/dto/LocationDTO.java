package com.simulador.backend.dto;

import com.simulador.backend.model.transaction.Location;
import com.simulador.backend.model.transaction.Wifi;

public record LocationDTO(
        Wifi wifi,
        Location location
) {
}
