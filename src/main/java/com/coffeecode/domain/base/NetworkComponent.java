package com.coffeecode.domain.base;

import com.coffeecode.domain.model.NetworkIdentity;

public interface NetworkComponent {
    NetworkIdentity getIdentity();

    boolean isActive();

    void setActive(boolean state);
}
