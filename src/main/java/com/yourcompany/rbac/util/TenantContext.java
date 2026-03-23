package com.yourcompany.rbac.util;

import com.yourcompany.rbac.entity.ClientApp;

public final class TenantContext {

    private static final ThreadLocal<ClientApp> TENANT = new ThreadLocal<>();

    private TenantContext() {
    }

    public static void setTenant(ClientApp clientApp) {
        TENANT.set(clientApp);
    }

    public static ClientApp getTenant() {
        return TENANT.get();
    }

    public static void clear() {
        TENANT.remove();
    }
}
