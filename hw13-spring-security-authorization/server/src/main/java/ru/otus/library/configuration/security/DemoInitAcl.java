package ru.otus.library.configuration.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.security.acls.domain.AclImpl;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Genre;

import javax.annotation.PostConstruct;

@Service
public class DemoInitAcl {
    private final MongoOperations mongoOperations;
    private final MutableAclService mutableAclService;

    public DemoInitAcl(MongoOperations mongoOperations, MutableAclService mutableAclService) {
        this.mongoOperations = mongoOperations;
        this.mutableAclService = mutableAclService;
    }

    @Transactional
    public void initAcl() {
        mongoOperations.findAll(Genre.class).stream().map(ObjectIdentityImpl::new).forEach(genreObjectIdentity -> {
            MutableAcl acl = mutableAclService.createAcl(genreObjectIdentity);
            acl.insertAce(acl.getEntries().size(), BasePermission.CREATE, new PrincipalSid("admin"), true);
            acl.insertAce(acl.getEntries().size(), BasePermission.READ, new PrincipalSid("user"), false);
        });
    }
}
