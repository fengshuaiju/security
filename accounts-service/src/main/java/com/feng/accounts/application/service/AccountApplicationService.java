package com.feng.accounts.application.service;

import com.feng.accounts.application.command.BindWechatCommand;
import com.feng.accounts.application.representation.MemberRepresentation;
import com.feng.accounts.application.representation.MemberRpcRepresentation;
import com.feng.accounts.application.representation.TenantRepresentation;
import com.feng.accounts.application.representation.TenantRpcRepresentation;
import com.feng.accounts.model.*;
import com.feng.accounts.support.utils.ResourceNotFoundException;
import com.feng.accounts.support.utils.Validate;

import com.feng.accounts.support.utils.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Stream;


import static java.util.stream.Collectors.toList;

@Service
@Slf4j
public class AccountApplicationService {

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private MembershipRepository membershipRepository;

    @Autowired
    private RoleGroupRepository roleGroupRepository;

    @Autowired
    private SmsSender smsSender;


    @Transactional(readOnly = true)
    public String friendlyName(String username) {
//        Record3<String, String, String> record = jooq.select(LOGIN.USERNAME, USERS.NICKNAME, USERS.NAME)
//                .from(LOGIN)
//                .leftOuterJoin(USERS).on(LOGIN.USER_ID.eq(USERS.ID))
//                .where(LOGIN.USERNAME.eq(username)).fetchOne();
//        if (record == null) {
//            return null;
//        }
//
//        String _username = record.component1();
//        String nickname = record.component2();
//        String name = record.component3();
//
//        if (StringUtils.isNotBlank(nickname)) {
//            return nickname;
//        } else {
//            if (StringUtils.isNotBlank(name)) {
//                return name;
//            } else {
//                return _username;
//            }
//        }

        return "";
    }

    @Transactional
    public Login createUser(Username username, Cellphone cellphone, EmailAddress email, String password, String name) {
//        if (username != null) {
//            Validate.isTrue(jooq.selectFrom(LOGIN).where(LOGIN.USERNAME.eq(username.toString())).fetch().isEmpty(), "error.username.existed");
//        }
//        if (cellphone != null) {
//            Validate.isTrue(jooq.selectFrom(LOGIN).where(LOGIN.CELLPHONE.eq(cellphone.toString())).fetch().isEmpty(), "error.cellphone.existed");
//        }
//        if (email != null) {
//            Validate.isTrue(jooq.selectFrom(LOGIN).where(LOGIN.EMAIL_ADDRESS.eq(email.toString())).fetch().isEmpty(), "error.email.existed");
//        }
//
//        if (username == null) {
//            username = generateUniqueUsername();
//        }
//
//        Login login = new Login(username, cellphone, email, passwordEncoder.encode(password), new User(username, name));
//        loginRepository.add(login);
//        return login;

        return null;
    }

    @Transactional
    public Login registerUser(String username, String password, String name) {
//        Validate.notBlank(username, "Username can not be blank");
//
//        Login login;
//        Username uniqueUsername;
//        if (Cellphone.isValid(username)) {
//            Validate.isTrue(jooq.selectFrom(LOGIN).where(LOGIN.CELLPHONE.eq(username)).fetch().isEmpty(), "error.cellphone.existed");
//
//            uniqueUsername = generateUniqueUsername();
//            login = new Login(uniqueUsername, new Cellphone(username), passwordEncoder.encode(password), new User(uniqueUsername, name));
//        } else if (EmailAddress.isValid(username)) {
//            Validate.isTrue(jooq.selectFrom(LOGIN).where(LOGIN.EMAIL_ADDRESS.eq(username)).fetch().isEmpty(), "error.email.existed");
//
//            uniqueUsername = generateUniqueUsername();
//            login = new Login(uniqueUsername, new EmailAddress(username), passwordEncoder.encode(password), new User(uniqueUsername, name));
//        } else {
//            String regex = "^[a-zA-Z][a-zA-Z0-9_-]*$";
//            if (username.matches(regex)) {
//                Validate.isTrue(jooq.selectFrom(LOGIN).where(LOGIN.USERNAME.eq(username)).fetch().isEmpty(), "error.username.existed");
//                uniqueUsername = new Username(username);
//                login = new Login(uniqueUsername, passwordEncoder.encode(password), new User(uniqueUsername, name));
//            } else {
//                throw new ValidationException("error.register.username.invalid");
//            }
//        }
//
//        loginRepository.add(login);
//
//        return login;

        return null;
    }

    private Username generateUniqueUsername() {
//        while (true) {
//            String random = "u_" + RandomStringUtils.randomAlphabetic(16);
//            if (jooq.selectFrom(LOGIN).where(LOGIN.USERNAME.eq(random)).fetch().isEmpty()) {
//                return new Username(random);
//            }
//        }
        return null;

    }

    @Transactional
    public void bindWechat(BindWechatCommand bindWechatCommand, Username username) {
//        loginRepository.findByUserName(username)
//                .ifPresent(login -> {
//                    login.bindWechat(bindWechatCommand.getWechatOpenId());
//
//                    login.editUserInfo(bindWechatCommand.getNickname(), bindWechatCommand.getSex(),
//                            bindWechatCommand.getHeadImageUrl(), bindWechatCommand.getCountry(), bindWechatCommand.getProvince(),
//                            bindWechatCommand.getCity());
//
//                    log.info("User {} binding Wechat success", username);
//                });
    }

    @Transactional
    public Tenant provisionTenant(Login admin, String tenantChineseName, boolean isPlatform) {
//        Validate.isTrue(
//                jooq.selectFrom(TENANT).where(TENANT.CHINESE_NAME.equalIgnoreCase(tenantChineseName)).fetch().isEmpty(),
//                "error.tenant.chineseName.existed"
//        );
//
//        TenantId tenantId = tenantRepository.nextId();
//        Tenant tenant = new Tenant(tenantId, admin.username(), tenantChineseName);
//        if (isPlatform) {
//            tenant.approve(admin.username(), Tenant.Type.PLATFORM, "platform", "Platform Init");
//        }
//        tenant = tenantRepository.add(tenant);
//
//        String roleGroupName = isPlatform ? "平台管理员" : "管理员";
//        RoleGroupId roleGroupId = provisionRoleGroup(tenantId, roleGroupName,
//                Stream.of(Role.values())
//                        .filter(role -> role.name().startsWith(isPlatform ? "PLATFORM_" : "TENANT_"))
//                        .collect(toList()));
//
//        Membership membership = new Membership(admin, tenantId, Collections.singletonList(roleGroupId));
//        membershipRepository.add(membership);
//
//        return tenant;

        return null;
    }

    @Transactional
    public RoleGroupId provisionRoleGroup(TenantId tenantId, String name, List<Role> roles) {
        Tenant tenant = tenantRepository.findByTenantId(tenantId).orElseThrow(() -> new ResourceNotFoundException("error.tenant.not.found"));
        RoleGroup roleGroup = tenant.newRoleGroup(name, roles);
        roleGroupRepository.add(roleGroup);
        return roleGroup.id();
    }

    @Transactional
    public void createAndApproveTenant(String adminName, String adminPassword, String tenantCode, String tenantChineseName, String approveRemarks, Username supporter) {
        Login user = registerUser(adminName, adminPassword, null);
        Tenant tenant = provisionTenant(user, tenantChineseName, false);
        tenant.approve(supporter, Tenant.Type.PREMIER_CUSTOMER, tenantCode, approveRemarks);
    }

    @Transactional
    public TenantId createTenant(String adminName, String adminPassword, String tenantName, String captcha) {
//        VerificationCodeRecord record = jooq.selectFrom(VERIFICATION_CODE).where(VERIFICATION_CODE.ID.equalIgnoreCase(adminName)).fetchOne();
//        Validate.notNull(record, "error.verification.code.not.generated");
//        Validate.isTrue(StringUtils.equalsIgnoreCase(captcha, record.getCaptcha()), "error.verification.code.invalid");
//        Validate.isTrue(Instant.now().isBefore(record.getExpireAt()), "error.verification.code.expired");
//
//        Login user = registerUser(adminName, adminPassword, null);
//        Tenant tenant = provisionTenant(user, tenantName, false);
//
//        provisionRoleGroup(tenant.tenantId(), "操作", Arrays.asList(Role.TENANT_ORDER_READ, Role.TENANT_ORDER_WRITE));
//
//        return tenant.tenantId();

        return null;
    }

    @Transactional(readOnly = true)
    public Page<TenantRepresentation> searchTenants(Username supporter, String name, Tenant.Status status, Pageable pageable) {
//        String likeExpression = StringUtils.isBlank(name) ? "%" : "%" + name + "%";
//        Condition whereCondition = TENANT.CHINESE_NAME.likeIgnoreCase(likeExpression);
//        if (status != null) {
//            whereCondition = whereCondition.and(TENANT.STATUS.eq(status.name()));
//        }
//        List<TenantRepresentation> queryResults =
//                jooq.select(LOGIN.CELLPHONE, TENANT.TENANT_ID, TENANT.CHINESE_NAME, TENANT.CODE, TENANT.CREATED_AT, TENANT.STATUS, TENANT.REVIEW_REMARKS)
//                        .from(TENANT)
//                        .leftJoin(LOGIN).on(TENANT.OWNER_ID.eq(LOGIN.USERNAME))
//                        .where(whereCondition)
//                        .orderBy(getSortFields(TENANT, pageable.getSort()))
//                        .limit(pageable.getPageSize()).offset((int) pageable.getOffset())
//                        .fetchInto(TenantRepresentation.class);
//
//        int totalCount = jooq.fetchCount(jooq.select().from(TENANT).where(whereCondition));
//
//        return new PageImpl<>(queryResults, pageable, totalCount);

        return null;
    }

//    private Collection<SortField<?>> getSortFields(Table<? extends Record> table, Sort sortSpec) {
//        if (sortSpec == null) {
//            return Collections.emptyList();
//        }
//
//        return Streams.stream(sortSpec.iterator())
//                .map(field -> {
//                    try {
//                        Field _field = table.getClass().getField(field.getProperty().toUpperCase());
//                        TableField tableField = (TableField) _field.get(table);
//                        return (SortField<?>) (field.getDirection() == Sort.Direction.ASC ? tableField.asc() : tableField.desc());
//                    } catch (NoSuchFieldException | IllegalAccessException e) {
//                        throw new InvalidDataAccessApiUsageException("Could not find table field: " + field.getProperty(), e);
//                    }
//                })
//                .collect(toList());
//    }

    @Transactional
    public void enableTenant(String tenantId, boolean enabled) {
        Tenant tenant = tenantRepository.findByTenantId(new TenantId(UUID.fromString(tenantId)))
                .orElseThrow(() -> new ResourceNotFoundException("error.tenant.not.found"));
        tenant.enable(enabled);
    }

    @Transactional
    public void updatePassword(Username username, String password) {
        loginRepository.findByUserName(username)
                .ifPresent(login -> login.setPassword(passwordEncoder.encode(password)));
    }

    @Transactional
    public void sendCaptcha(String username) {
//        Validate.isTrue(Cellphone.isValid(username), "error.cellphone.format.invalid");
//        Integer captcha = RandomUtils.nextInt(100000, 1000000);
//        Instant expireAt = Instant.now().plus(Duration.ofMinutes(5));
//        VerificationCodeRecord record = jooq.selectFrom(VERIFICATION_CODE).where(VERIFICATION_CODE.ID.equalIgnoreCase(username)).fetchOne();
//        if (record == null) {
//            jooq.insertInto(VERIFICATION_CODE)
//                    .set(VERIFICATION_CODE.ID, username)
//                    .set(VERIFICATION_CODE.CAPTCHA, captcha.toString())
//                    .set(VERIFICATION_CODE.EXPIRE_AT, expireAt)
//                    .execute();
//        } else {
//            record.setCaptcha(captcha.toString());
//            record.setExpireAt(expireAt);
//            jooq.executeUpdate(record);
//        }
//        smsSender.send(new Cellphone(username), 120552, captcha.toString(), "5");
    }

    @Transactional
    public void approveTenant(Username supporter, TenantId tenantId, boolean approved, Tenant.Type type, String code, String remarks) {
        Tenant tenant = tenantRepository.findByTenantId(tenantId)
                .orElseThrow(() -> new ValidationException("error.tenant.not.found"));
        if (approved) {
            tenant.approve(supporter, type, code, remarks);
        } else {
            tenant.disapprove(remarks);
        }
    }

    @Transactional(readOnly = true)
    public TenantRpcRepresentation getTenant(String tenantId) {
//        return jooq.select(TENANT.CHINESE_NAME, TENANT.TYPE, TENANT.STATUS)
//                .from(TENANT)
//                .where(TENANT.TENANT_ID.equalIgnoreCase(tenantId))
//                .fetchOneInto(TenantRpcRepresentation.class);

        return null;
    }

    @Transactional(readOnly = true)
    public MemberRpcRepresentation getMember(String tenantId, String memberId) {
//        return jooq.select(MEMBERSHIP.NAME, MEMBERSHIP.CELLPHONE, MEMBERSHIP.EMAIL_ADDRESS)
//                .from(MEMBERSHIP)
//                .where(MEMBERSHIP.TENANT_ID.equalIgnoreCase(tenantId).and(MEMBERSHIP.USERNAME.equalIgnoreCase(memberId)))
//                .fetchOneInto(MemberRpcRepresentation.class);

        return null;
    }

    @Transactional
    public Username addMember(TenantId tenantId, String name, Cellphone cellphone, EmailAddress email, String password, List<String> roleGroupNames) {
//        Validate.notNull(email, "error.member.email.empty");
//        Login login = loginRepository.findByEmailAddress(email);
//        if (login != null) {
//            Validate.isTrue(!jooq.fetchExists(MEMBERSHIP, MEMBERSHIP.TENANT_ID.eq(tenantId.tenantId()).and(MEMBERSHIP.USERNAME.eq(login.username().toString()))),
//                    "error.member.existed");
//            Validate.isTrue(login.cellphone() == null || (cellphone == null || StringUtils.equalsIgnoreCase(login.cellphone().toString(), cellphone.toString())),
//                    "error.member.cellphone.conflict");
//        } else {
//            login = createUser(null, cellphone, email, password, name);
//        }
//
//        Tenant tenant = tenantRepository.findByTenantId(tenantId).orElseThrow(() -> new ValidationException("error.tenant.not.found"));
//        Validate.isTrue(tenant.status() == Tenant.Status.APPROVED, "error.tenant.status.not.approved");
//
//        Membership membership = tenant.newMembership(login, roleGroupRepository.get(tenantId, roleGroupNames).stream().map(RoleGroup::id).collect(toList()));
//        membershipRepository.add(membership);
//
//        return login.username();

        return null;
    }

    @Transactional
    public void deleteMember(TenantId tenantId, String memberId) {
        tenantRepository.findByTenantId(tenantId)
                .ifPresent(tenant -> {
                    Username username = new Username(memberId);
                    if (!username.equals(tenant.ownerId())) {
                        membershipRepository.get(tenantId, username)
                                .ifPresent(membershipRepository::delete);
                    } else {
                        throw new ValidationException("error.cannot.delete.tenant.owner");
                    }
                });

    }

    public Page<MemberRepresentation> getMembers(TenantId tenantId, Pageable pageable) {
//        return tenantRepository.findByTenantId(tenantId).map(tenant -> {
//            Condition where = MEMBERSHIP.TENANT_ID.eq(tenantId.tenantId())
//                    .and(MEMBERSHIP.USERNAME.notEqual(tenant.ownerId().toString()));
//            List<MemberRepresentation> result = jooq.selectFrom(MEMBERSHIP)
//                    .where(where)
//                    .orderBy(getSortFields(MEMBERSHIP, pageable.getSort()))
//                    .limit(pageable.getPageSize()).offset((int) pageable.getOffset())
//                    .fetchInto(MemberRepresentation.class);
//
//            int totalCount = jooq.fetchCount(jooq.selectFrom(MEMBERSHIP).where(where));
//            return new PageImpl<>(result, pageable, totalCount);
//        }).orElse(new PageImpl<>(Collections.emptyList(), pageable, 0));

        return null;
    }
}
