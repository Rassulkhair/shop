package kz.shokanov.rassulkhair.shop.entity.enumiration;

import lombok.Getter;
import lombok.Setter;


public enum Role {
    USER("user", "Пользователь"),
    MODERATOR("moderator", "Модератор"),
    ADMIN("admin", "Администратор");
    private final String serviceName;

    private final String displayName;

    Role(String serviceName, String displayName) {
        this.serviceName = serviceName;
        this.displayName = displayName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getDisplayName(){
        return displayName;
    }
}