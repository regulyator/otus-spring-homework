package ru.otus.library.service.security;

import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AclPermissionGrantServiceImpl implements AclPermissionGrantService {
    private final MutableAclService mutableAclService;

    public AclPermissionGrantServiceImpl(MutableAclService mutableAclService) {
        this.mutableAclService = mutableAclService;
    }

    @Override
    @Transactional
    public void grantAclPermission(Object object, Permission permission, Sid permissionSid) {
        final Sid ownerSid = new PrincipalSid(SecurityContextHolder.getContext().getAuthentication());
        final ObjectIdentity objectIdentity = new ObjectIdentityImpl(object);
        final MutableAcl acl = mutableAclService.createAcl(objectIdentity);
        acl.setOwner(ownerSid);
        acl.insertAce(acl.getEntries().size(), permission, permissionSid, true);
        mutableAclService.updateAcl(acl);
    }

    @Override
    @Transactional
    public void grantAclPermission(Object object) {
        final Sid ownerSid = new PrincipalSid(SecurityContextHolder.getContext().getAuthentication());
        final ObjectIdentity objectIdentity = new ObjectIdentityImpl(object);
        final MutableAcl acl = mutableAclService.createAcl(objectIdentity);
        acl.setOwner(ownerSid);
        acl.insertAce(acl.getEntries().size(), BasePermission.ADMINISTRATION, ownerSid, true);
        mutableAclService.updateAcl(acl);
    }
}
