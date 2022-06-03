package com.vldby.demochat.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "sec$Role")
@Table(name = "SEC_ROLE")
public class Role extends StandardEntity implements GrantedAuthority {

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "LOC_NAME")
    private String locName;

    @Column(name = "DESCRIPTION", length = 1000)
    private String description;

    @Column(name = "IS_DEFAULT_ROLE")
    private Boolean defaultRole;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDefaultRole() {
        return defaultRole;
    }

    public void setDefaultRole(Boolean defaultRole) {
        this.defaultRole = defaultRole;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
