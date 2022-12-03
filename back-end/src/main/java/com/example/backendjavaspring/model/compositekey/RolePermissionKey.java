package com.example.backendjavaspring.model.compositekey;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class RolePermissionKey implements Serializable {
    @Column
    private long roleId;
    @Column
    private long permissionId;

    public RolePermissionKey() {
    }
}
