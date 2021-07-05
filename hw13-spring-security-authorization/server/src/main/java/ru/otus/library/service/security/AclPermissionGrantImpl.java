package ru.otus.library.service.security;

import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AclPermissionGrantImpl implements AclPermissionGrant {
    private final MutableAclService mutableAclService;

    public AclPermissionGrantImpl(MutableAclService mutableAclService) {
        this.mutableAclService = mutableAclService;
    }

    @Override
    public void grantAclPermission(Object object, Permission permission, Sid permissionSid) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Sid owner = new PrincipalSid(authentication);
        final ObjectIdentity oid = new ObjectIdentityImpl(object);
        final MutableAcl acl = mutableAclService.createAcl(oid);
        acl.setOwner(owner);
        acl.insertAce(acl.getEntries().size(), BasePermission.ADMINISTRATION, permissionSid, true);
        mutableAclService.updateAcl(acl);
    }

    @Override
    public void grantAclPermission(Object object) {
        final Sid permissionSid = new PrincipalSid(SecurityContextHolder.getContext().getAuthentication());
        this.grantAclPermission(object, BasePermission.ADMINISTRATION, permissionSid);
    }
}
