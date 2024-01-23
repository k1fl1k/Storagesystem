package com.k1fl1k.storagesystem.persistance.entity.impl;

import com.k1fl1k.storagesystem.persistance.entity.Entity;
import com.k1fl1k.storagesystem.persistance.entity.ErrorTemplates;
import java.util.Objects;
import java.util.UUID;

public class Storage extends Entity {

    private final String address;
    private final String Name;
    private String number;
    private String Admin;

    public Storage(UUID id, String address, String Name, String Admin) {
        super(id);
        this.address = address;
        this.Name = Name;
        this.Admin = Admin;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return Name;
    }

    public String getAdmin() {
        return Admin;
    }

    public String setAdmin(String Admin) {
        String templateName = "вмісту";

        if (Admin.isBlank()) {
            errors.add(ErrorTemplates.REQUIRED.getTemplate().formatted(templateName));
        }

        return Admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Storage storage = (Storage) o;
        return Objects.equals(address, storage.address) && Objects.equals(Name,
            storage.Name) && Objects.equals(Admin, storage.Admin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), address, Name, Admin);
    }

    @Override
    public String toString() {
        return "Storage{" +
            "address='" + address + '\'' +
            ", Name='" + Name + '\'' +
            ", Admin='" + Admin + '\'' +
            ", id=" + id +
            '}';
    }
}
