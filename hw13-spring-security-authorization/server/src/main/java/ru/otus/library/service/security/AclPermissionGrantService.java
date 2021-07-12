package ru.otus.library.service.security;

import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;

public interface AclPermissionGrantService {
    void grantAclPermission(Object object, Permission permission, Sid sid);

    void grantAclPermission(Object object);

}
