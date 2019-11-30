package com.example.demo.domain;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_sys_user")
public class TbSysUser {
    /**
     * 用户编码
     */
    @Id
    @Column(name = "user_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
    private String userCode;

    /**
     * 登录账号
     */
    @Column(name = "login_code")
    private String loginCode;

    /**
     * 用户昵称
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 办公电话
     */
    private String phone;

    /**
     * 用户性别
     */
    private String sex;

    /**
     * 头像路径
     */
    private String avatar;

    /**
     * 个性签名
     */
    private String sign;

    /**
     * 绑定的微信号
     */
    @Column(name = "wx_openid")
    private String wxOpenid;

    /**
     * 绑定的手机串号
     */
    @Column(name = "mobile_imei")
    private String mobileImei;

    /**
     * 用户类型
     */
    @Column(name = "user_type")
    private String userType;

    /**
     * 用户类型引用编号
     */
    @Column(name = "ref_code")
    private String refCode;

    /**
     * 用户类型引用姓名
     */
    @Column(name = "ref_name")
    private String refName;

    /**
     * 管理员类型（0非管理员 1系统管理员  2二级管理员）
     */
    @Column(name = "mgr_type")
    private String mgrType;

    /**
     * 密码安全级别（0初始 1很弱 2弱 3安全 4很安全）
     */
    @Column(name = "pwd_security_level")
    private Short pwdSecurityLevel;

    /**
     * 密码最后更新时间
     */
    @Column(name = "pwd_update_date")
    private Date pwdUpdateDate;

    /**
     * 密码修改记录
     */
    @Column(name = "pwd_update_record")
    private String pwdUpdateRecord;

    /**
     * 密保问题
     */
    @Column(name = "pwd_question")
    private String pwdQuestion;

    /**
     * 密保问题答案
     */
    @Column(name = "pwd_question_answer")
    private String pwdQuestionAnswer;

    /**
     * 密保问题2
     */
    @Column(name = "pwd_question_2")
    private String pwdQuestion2;

    /**
     * 密保问题答案2
     */
    @Column(name = "pwd_question_answer_2")
    private String pwdQuestionAnswer2;

    /**
     * 密保问题3
     */
    @Column(name = "pwd_question_3")
    private String pwdQuestion3;

    /**
     * 密保问题答案3
     */
    @Column(name = "pwd_question_answer_3")
    private String pwdQuestionAnswer3;

    /**
     * 密码问题修改时间
     */
    @Column(name = "pwd_quest_update_date")
    private Date pwdQuestUpdateDate;

    /**
     * 最后登陆IP
     */
    @Column(name = "last_login_ip")
    private String lastLoginIp;

    /**
     * 最后登陆时间
     */
    @Column(name = "last_login_date")
    private Date lastLoginDate;

    /**
     * 冻结时间
     */
    @Column(name = "freeze_date")
    private Date freezeDate;

    /**
     * 冻结原因
     */
    @Column(name = "freeze_cause")
    private String freezeCause;

    /**
     * 用户权重（降序）
     */
    @Column(name = "user_weight")
    private Integer userWeight;

    /**
     * 状态（0正常 1删除 2停用 3冻结）
     */
    private String status;

    /**
     * 创建者
     */
    @Column(name = "create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @Column(name = "create_date")
    private Date createDate;

    /**
     * 更新者
     */
    @Column(name = "update_by")
    private String updateBy;

    /**
     * 更新时间
     */
    @Column(name = "update_date")
    private Date updateDate;

    /**
     * 备注信息
     */
    private String remarks;

    /**
     * 归属集团Code
     */
    @Column(name = "corp_code")
    private String corpCode;

    /**
     * 归属集团Name
     */
    @Column(name = "corp_name")
    private String corpName;

    /**
     * 扩展 String 1
     */
    @Column(name = "extend_s1")
    private String extendS1;

    /**
     * 扩展 String 2
     */
    @Column(name = "extend_s2")
    private String extendS2;

    /**
     * 扩展 String 3
     */
    @Column(name = "extend_s3")
    private String extendS3;

    /**
     * 扩展 String 4
     */
    @Column(name = "extend_s4")
    private String extendS4;

    /**
     * 扩展 String 5
     */
    @Column(name = "extend_s5")
    private String extendS5;

    /**
     * 扩展 String 6
     */
    @Column(name = "extend_s6")
    private String extendS6;

    /**
     * 扩展 String 7
     */
    @Column(name = "extend_s7")
    private String extendS7;

    /**
     * 扩展 String 8
     */
    @Column(name = "extend_s8")
    private String extendS8;

    /**
     * 扩展 Integer 1
     */
    @Column(name = "extend_i1")
    private BigDecimal extendI1;

    /**
     * 扩展 Integer 2
     */
    @Column(name = "extend_i2")
    private BigDecimal extendI2;

    /**
     * 扩展 Integer 3
     */
    @Column(name = "extend_i3")
    private BigDecimal extendI3;

    /**
     * 扩展 Integer 4
     */
    @Column(name = "extend_i4")
    private BigDecimal extendI4;

    /**
     * 扩展 Float 1
     */
    @Column(name = "extend_f1")
    private BigDecimal extendF1;

    /**
     * 扩展 Float 2
     */
    @Column(name = "extend_f2")
    private BigDecimal extendF2;

    /**
     * 扩展 Float 3
     */
    @Column(name = "extend_f3")
    private BigDecimal extendF3;

    /**
     * 扩展 Float 4
     */
    @Column(name = "extend_f4")
    private BigDecimal extendF4;

    /**
     * 扩展 Date 1
     */
    @Column(name = "extend_d1")
    private Date extendD1;

    /**
     * 扩展 Date 2
     */
    @Column(name = "extend_d2")
    private Date extendD2;

    /**
     * 扩展 Date 3
     */
    @Column(name = "extend_d3")
    private Date extendD3;

    /**
     * 扩展 Date 4
     */
    @Column(name = "extend_d4")
    private Date extendD4;

    /**
     * 获取用户编码
     *
     * @return user_code - 用户编码
     */
    public String getUserCode() {
        return userCode;
    }

    /**
     * 设置用户编码
     *
     * @param userCode 用户编码
     */
    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    /**
     * 获取登录账号
     *
     * @return login_code - 登录账号
     */
    public String getLoginCode() {
        return loginCode;
    }

    /**
     * 设置登录账号
     *
     * @param loginCode 登录账号
     */
    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    /**
     * 获取用户昵称
     *
     * @return user_name - 用户昵称
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置用户昵称
     *
     * @param userName 用户昵称
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取登录密码
     *
     * @return password - 登录密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置登录密码
     *
     * @param password 登录密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取电子邮箱
     *
     * @return email - 电子邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置电子邮箱
     *
     * @param email 电子邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取手机号码
     *
     * @return mobile - 手机号码
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置手机号码
     *
     * @param mobile 手机号码
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取办公电话
     *
     * @return phone - 办公电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置办公电话
     *
     * @param phone 办公电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取用户性别
     *
     * @return sex - 用户性别
     */
    public String getSex() {
        return sex;
    }

    /**
     * 设置用户性别
     *
     * @param sex 用户性别
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * 获取头像路径
     *
     * @return avatar - 头像路径
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * 设置头像路径
     *
     * @param avatar 头像路径
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * 获取个性签名
     *
     * @return sign - 个性签名
     */
    public String getSign() {
        return sign;
    }

    /**
     * 设置个性签名
     *
     * @param sign 个性签名
     */
    public void setSign(String sign) {
        this.sign = sign;
    }

    /**
     * 获取绑定的微信号
     *
     * @return wx_openid - 绑定的微信号
     */
    public String getWxOpenid() {
        return wxOpenid;
    }

    /**
     * 设置绑定的微信号
     *
     * @param wxOpenid 绑定的微信号
     */
    public void setWxOpenid(String wxOpenid) {
        this.wxOpenid = wxOpenid;
    }

    /**
     * 获取绑定的手机串号
     *
     * @return mobile_imei - 绑定的手机串号
     */
    public String getMobileImei() {
        return mobileImei;
    }

    /**
     * 设置绑定的手机串号
     *
     * @param mobileImei 绑定的手机串号
     */
    public void setMobileImei(String mobileImei) {
        this.mobileImei = mobileImei;
    }

    /**
     * 获取用户类型
     *
     * @return user_type - 用户类型
     */
    public String getUserType() {
        return userType;
    }

    /**
     * 设置用户类型
     *
     * @param userType 用户类型
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * 获取用户类型引用编号
     *
     * @return ref_code - 用户类型引用编号
     */
    public String getRefCode() {
        return refCode;
    }

    /**
     * 设置用户类型引用编号
     *
     * @param refCode 用户类型引用编号
     */
    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }

    /**
     * 获取用户类型引用姓名
     *
     * @return ref_name - 用户类型引用姓名
     */
    public String getRefName() {
        return refName;
    }

    /**
     * 设置用户类型引用姓名
     *
     * @param refName 用户类型引用姓名
     */
    public void setRefName(String refName) {
        this.refName = refName;
    }

    /**
     * 获取管理员类型（0非管理员 1系统管理员  2二级管理员）
     *
     * @return mgr_type - 管理员类型（0非管理员 1系统管理员  2二级管理员）
     */
    public String getMgrType() {
        return mgrType;
    }

    /**
     * 设置管理员类型（0非管理员 1系统管理员  2二级管理员）
     *
     * @param mgrType 管理员类型（0非管理员 1系统管理员  2二级管理员）
     */
    public void setMgrType(String mgrType) {
        this.mgrType = mgrType;
    }

    /**
     * 获取密码安全级别（0初始 1很弱 2弱 3安全 4很安全）
     *
     * @return pwd_security_level - 密码安全级别（0初始 1很弱 2弱 3安全 4很安全）
     */
    public Short getPwdSecurityLevel() {
        return pwdSecurityLevel;
    }

    /**
     * 设置密码安全级别（0初始 1很弱 2弱 3安全 4很安全）
     *
     * @param pwdSecurityLevel 密码安全级别（0初始 1很弱 2弱 3安全 4很安全）
     */
    public void setPwdSecurityLevel(Short pwdSecurityLevel) {
        this.pwdSecurityLevel = pwdSecurityLevel;
    }

    /**
     * 获取密码最后更新时间
     *
     * @return pwd_update_date - 密码最后更新时间
     */
    public Date getPwdUpdateDate() {
        return pwdUpdateDate;
    }

    /**
     * 设置密码最后更新时间
     *
     * @param pwdUpdateDate 密码最后更新时间
     */
    public void setPwdUpdateDate(Date pwdUpdateDate) {
        this.pwdUpdateDate = pwdUpdateDate;
    }

    /**
     * 获取密码修改记录
     *
     * @return pwd_update_record - 密码修改记录
     */
    public String getPwdUpdateRecord() {
        return pwdUpdateRecord;
    }

    /**
     * 设置密码修改记录
     *
     * @param pwdUpdateRecord 密码修改记录
     */
    public void setPwdUpdateRecord(String pwdUpdateRecord) {
        this.pwdUpdateRecord = pwdUpdateRecord;
    }

    /**
     * 获取密保问题
     *
     * @return pwd_question - 密保问题
     */
    public String getPwdQuestion() {
        return pwdQuestion;
    }

    /**
     * 设置密保问题
     *
     * @param pwdQuestion 密保问题
     */
    public void setPwdQuestion(String pwdQuestion) {
        this.pwdQuestion = pwdQuestion;
    }

    /**
     * 获取密保问题答案
     *
     * @return pwd_question_answer - 密保问题答案
     */
    public String getPwdQuestionAnswer() {
        return pwdQuestionAnswer;
    }

    /**
     * 设置密保问题答案
     *
     * @param pwdQuestionAnswer 密保问题答案
     */
    public void setPwdQuestionAnswer(String pwdQuestionAnswer) {
        this.pwdQuestionAnswer = pwdQuestionAnswer;
    }

    /**
     * 获取密保问题2
     *
     * @return pwd_question_2 - 密保问题2
     */
    public String getPwdQuestion2() {
        return pwdQuestion2;
    }

    /**
     * 设置密保问题2
     *
     * @param pwdQuestion2 密保问题2
     */
    public void setPwdQuestion2(String pwdQuestion2) {
        this.pwdQuestion2 = pwdQuestion2;
    }

    /**
     * 获取密保问题答案2
     *
     * @return pwd_question_answer_2 - 密保问题答案2
     */
    public String getPwdQuestionAnswer2() {
        return pwdQuestionAnswer2;
    }

    /**
     * 设置密保问题答案2
     *
     * @param pwdQuestionAnswer2 密保问题答案2
     */
    public void setPwdQuestionAnswer2(String pwdQuestionAnswer2) {
        this.pwdQuestionAnswer2 = pwdQuestionAnswer2;
    }

    /**
     * 获取密保问题3
     *
     * @return pwd_question_3 - 密保问题3
     */
    public String getPwdQuestion3() {
        return pwdQuestion3;
    }

    /**
     * 设置密保问题3
     *
     * @param pwdQuestion3 密保问题3
     */
    public void setPwdQuestion3(String pwdQuestion3) {
        this.pwdQuestion3 = pwdQuestion3;
    }

    /**
     * 获取密保问题答案3
     *
     * @return pwd_question_answer_3 - 密保问题答案3
     */
    public String getPwdQuestionAnswer3() {
        return pwdQuestionAnswer3;
    }

    /**
     * 设置密保问题答案3
     *
     * @param pwdQuestionAnswer3 密保问题答案3
     */
    public void setPwdQuestionAnswer3(String pwdQuestionAnswer3) {
        this.pwdQuestionAnswer3 = pwdQuestionAnswer3;
    }

    /**
     * 获取密码问题修改时间
     *
     * @return pwd_quest_update_date - 密码问题修改时间
     */
    public Date getPwdQuestUpdateDate() {
        return pwdQuestUpdateDate;
    }

    /**
     * 设置密码问题修改时间
     *
     * @param pwdQuestUpdateDate 密码问题修改时间
     */
    public void setPwdQuestUpdateDate(Date pwdQuestUpdateDate) {
        this.pwdQuestUpdateDate = pwdQuestUpdateDate;
    }

    /**
     * 获取最后登陆IP
     *
     * @return last_login_ip - 最后登陆IP
     */
    public String getLastLoginIp() {
        return lastLoginIp;
    }

    /**
     * 设置最后登陆IP
     *
     * @param lastLoginIp 最后登陆IP
     */
    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    /**
     * 获取最后登陆时间
     *
     * @return last_login_date - 最后登陆时间
     */
    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    /**
     * 设置最后登陆时间
     *
     * @param lastLoginDate 最后登陆时间
     */
    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    /**
     * 获取冻结时间
     *
     * @return freeze_date - 冻结时间
     */
    public Date getFreezeDate() {
        return freezeDate;
    }

    /**
     * 设置冻结时间
     *
     * @param freezeDate 冻结时间
     */
    public void setFreezeDate(Date freezeDate) {
        this.freezeDate = freezeDate;
    }

    /**
     * 获取冻结原因
     *
     * @return freeze_cause - 冻结原因
     */
    public String getFreezeCause() {
        return freezeCause;
    }

    /**
     * 设置冻结原因
     *
     * @param freezeCause 冻结原因
     */
    public void setFreezeCause(String freezeCause) {
        this.freezeCause = freezeCause;
    }

    /**
     * 获取用户权重（降序）
     *
     * @return user_weight - 用户权重（降序）
     */
    public Integer getUserWeight() {
        return userWeight;
    }

    /**
     * 设置用户权重（降序）
     *
     * @param userWeight 用户权重（降序）
     */
    public void setUserWeight(Integer userWeight) {
        this.userWeight = userWeight;
    }

    /**
     * 获取状态（0正常 1删除 2停用 3冻结）
     *
     * @return status - 状态（0正常 1删除 2停用 3冻结）
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态（0正常 1删除 2停用 3冻结）
     *
     * @param status 状态（0正常 1删除 2停用 3冻结）
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取创建者
     *
     * @return create_by - 创建者
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * 设置创建者
     *
     * @param createBy 创建者
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * 获取创建时间
     *
     * @return create_date - 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置创建时间
     *
     * @param createDate 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取更新者
     *
     * @return update_by - 更新者
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * 设置更新者
     *
     * @param updateBy 更新者
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 获取更新时间
     *
     * @return update_date - 更新时间
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 设置更新时间
     *
     * @param updateDate 更新时间
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 获取备注信息
     *
     * @return remarks - 备注信息
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 设置备注信息
     *
     * @param remarks 备注信息
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * 获取归属集团Code
     *
     * @return corp_code - 归属集团Code
     */
    public String getCorpCode() {
        return corpCode;
    }

    /**
     * 设置归属集团Code
     *
     * @param corpCode 归属集团Code
     */
    public void setCorpCode(String corpCode) {
        this.corpCode = corpCode;
    }

    /**
     * 获取归属集团Name
     *
     * @return corp_name - 归属集团Name
     */
    public String getCorpName() {
        return corpName;
    }

    /**
     * 设置归属集团Name
     *
     * @param corpName 归属集团Name
     */
    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    /**
     * 获取扩展 String 1
     *
     * @return extend_s1 - 扩展 String 1
     */
    public String getExtendS1() {
        return extendS1;
    }

    /**
     * 设置扩展 String 1
     *
     * @param extendS1 扩展 String 1
     */
    public void setExtendS1(String extendS1) {
        this.extendS1 = extendS1;
    }

    /**
     * 获取扩展 String 2
     *
     * @return extend_s2 - 扩展 String 2
     */
    public String getExtendS2() {
        return extendS2;
    }

    /**
     * 设置扩展 String 2
     *
     * @param extendS2 扩展 String 2
     */
    public void setExtendS2(String extendS2) {
        this.extendS2 = extendS2;
    }

    /**
     * 获取扩展 String 3
     *
     * @return extend_s3 - 扩展 String 3
     */
    public String getExtendS3() {
        return extendS3;
    }

    /**
     * 设置扩展 String 3
     *
     * @param extendS3 扩展 String 3
     */
    public void setExtendS3(String extendS3) {
        this.extendS3 = extendS3;
    }

    /**
     * 获取扩展 String 4
     *
     * @return extend_s4 - 扩展 String 4
     */
    public String getExtendS4() {
        return extendS4;
    }

    /**
     * 设置扩展 String 4
     *
     * @param extendS4 扩展 String 4
     */
    public void setExtendS4(String extendS4) {
        this.extendS4 = extendS4;
    }

    /**
     * 获取扩展 String 5
     *
     * @return extend_s5 - 扩展 String 5
     */
    public String getExtendS5() {
        return extendS5;
    }

    /**
     * 设置扩展 String 5
     *
     * @param extendS5 扩展 String 5
     */
    public void setExtendS5(String extendS5) {
        this.extendS5 = extendS5;
    }

    /**
     * 获取扩展 String 6
     *
     * @return extend_s6 - 扩展 String 6
     */
    public String getExtendS6() {
        return extendS6;
    }

    /**
     * 设置扩展 String 6
     *
     * @param extendS6 扩展 String 6
     */
    public void setExtendS6(String extendS6) {
        this.extendS6 = extendS6;
    }

    /**
     * 获取扩展 String 7
     *
     * @return extend_s7 - 扩展 String 7
     */
    public String getExtendS7() {
        return extendS7;
    }

    /**
     * 设置扩展 String 7
     *
     * @param extendS7 扩展 String 7
     */
    public void setExtendS7(String extendS7) {
        this.extendS7 = extendS7;
    }

    /**
     * 获取扩展 String 8
     *
     * @return extend_s8 - 扩展 String 8
     */
    public String getExtendS8() {
        return extendS8;
    }

    /**
     * 设置扩展 String 8
     *
     * @param extendS8 扩展 String 8
     */
    public void setExtendS8(String extendS8) {
        this.extendS8 = extendS8;
    }

    /**
     * 获取扩展 Integer 1
     *
     * @return extend_i1 - 扩展 Integer 1
     */
    public BigDecimal getExtendI1() {
        return extendI1;
    }

    /**
     * 设置扩展 Integer 1
     *
     * @param extendI1 扩展 Integer 1
     */
    public void setExtendI1(BigDecimal extendI1) {
        this.extendI1 = extendI1;
    }

    /**
     * 获取扩展 Integer 2
     *
     * @return extend_i2 - 扩展 Integer 2
     */
    public BigDecimal getExtendI2() {
        return extendI2;
    }

    /**
     * 设置扩展 Integer 2
     *
     * @param extendI2 扩展 Integer 2
     */
    public void setExtendI2(BigDecimal extendI2) {
        this.extendI2 = extendI2;
    }

    /**
     * 获取扩展 Integer 3
     *
     * @return extend_i3 - 扩展 Integer 3
     */
    public BigDecimal getExtendI3() {
        return extendI3;
    }

    /**
     * 设置扩展 Integer 3
     *
     * @param extendI3 扩展 Integer 3
     */
    public void setExtendI3(BigDecimal extendI3) {
        this.extendI3 = extendI3;
    }

    /**
     * 获取扩展 Integer 4
     *
     * @return extend_i4 - 扩展 Integer 4
     */
    public BigDecimal getExtendI4() {
        return extendI4;
    }

    /**
     * 设置扩展 Integer 4
     *
     * @param extendI4 扩展 Integer 4
     */
    public void setExtendI4(BigDecimal extendI4) {
        this.extendI4 = extendI4;
    }

    /**
     * 获取扩展 Float 1
     *
     * @return extend_f1 - 扩展 Float 1
     */
    public BigDecimal getExtendF1() {
        return extendF1;
    }

    /**
     * 设置扩展 Float 1
     *
     * @param extendF1 扩展 Float 1
     */
    public void setExtendF1(BigDecimal extendF1) {
        this.extendF1 = extendF1;
    }

    /**
     * 获取扩展 Float 2
     *
     * @return extend_f2 - 扩展 Float 2
     */
    public BigDecimal getExtendF2() {
        return extendF2;
    }

    /**
     * 设置扩展 Float 2
     *
     * @param extendF2 扩展 Float 2
     */
    public void setExtendF2(BigDecimal extendF2) {
        this.extendF2 = extendF2;
    }

    /**
     * 获取扩展 Float 3
     *
     * @return extend_f3 - 扩展 Float 3
     */
    public BigDecimal getExtendF3() {
        return extendF3;
    }

    /**
     * 设置扩展 Float 3
     *
     * @param extendF3 扩展 Float 3
     */
    public void setExtendF3(BigDecimal extendF3) {
        this.extendF3 = extendF3;
    }

    /**
     * 获取扩展 Float 4
     *
     * @return extend_f4 - 扩展 Float 4
     */
    public BigDecimal getExtendF4() {
        return extendF4;
    }

    /**
     * 设置扩展 Float 4
     *
     * @param extendF4 扩展 Float 4
     */
    public void setExtendF4(BigDecimal extendF4) {
        this.extendF4 = extendF4;
    }

    /**
     * 获取扩展 Date 1
     *
     * @return extend_d1 - 扩展 Date 1
     */
    public Date getExtendD1() {
        return extendD1;
    }

    /**
     * 设置扩展 Date 1
     *
     * @param extendD1 扩展 Date 1
     */
    public void setExtendD1(Date extendD1) {
        this.extendD1 = extendD1;
    }

    /**
     * 获取扩展 Date 2
     *
     * @return extend_d2 - 扩展 Date 2
     */
    public Date getExtendD2() {
        return extendD2;
    }

    /**
     * 设置扩展 Date 2
     *
     * @param extendD2 扩展 Date 2
     */
    public void setExtendD2(Date extendD2) {
        this.extendD2 = extendD2;
    }

    /**
     * 获取扩展 Date 3
     *
     * @return extend_d3 - 扩展 Date 3
     */
    public Date getExtendD3() {
        return extendD3;
    }

    /**
     * 设置扩展 Date 3
     *
     * @param extendD3 扩展 Date 3
     */
    public void setExtendD3(Date extendD3) {
        this.extendD3 = extendD3;
    }

    /**
     * 获取扩展 Date 4
     *
     * @return extend_d4 - 扩展 Date 4
     */
    public Date getExtendD4() {
        return extendD4;
    }

    /**
     * 设置扩展 Date 4
     *
     * @param extendD4 扩展 Date 4
     */
    public void setExtendD4(Date extendD4) {
        this.extendD4 = extendD4;
    }
}