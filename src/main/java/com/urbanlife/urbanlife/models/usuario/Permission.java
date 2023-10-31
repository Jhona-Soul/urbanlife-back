package com.urbanlife.urbanlife.models.usuario;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    ADMIN_READ("admin:read"),
    ADMIN_DELETE("admin:delete"),
    ADMIN_CREATE("admin:create"),
    ADMIN_UPDATE("admin:update"),
    CLIENTE_READ("client:read"),
    CLIENTE_DELETE("client:delete"),
    CLIENTE_CREATE("client:create"),
    CLIENTE_UPDATE("client:update");

    @Getter
    private final String permission;
}
