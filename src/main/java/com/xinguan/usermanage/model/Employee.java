package com.xinguan.usermanage.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * 雇员
 *
 * @author zhangzhan
 * @date 2019-01-02 18:28
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Employee {

    public Employee() {
    }

    public Employee(Long id,String name, String username, Set<Role> roles) {
        this.name = name;
        this.username = username;
        this.roles = roles;
        this.id=id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 雇员姓名
     */
    @Column
    private String name;
    /**
     * 雇员用户名
     */
    @Column(nullable = false,unique = true)
    private String username;
    /**
     * 邮箱
     */
    @Column
    private String email;
    /**
     * 密码
     */
    @Column
    private String password;
    @Column
    private String activationkey;
    @Column
    private String resetpasswordkey;
    /**
     * 手机号
     */
    @Column
    private String mobile;
    /**
     * 上级主管
     */
    @OneToOne
    private Employee chief;
    /**
     * 性别
     */
    @Column
    private String sex;
    /**
     * 入职日期
     */
    @Column
    private Date entryDate;
    /**
     * 离职日期
     */
    @Column
    private Date termDate;
    /**
     * 员工状态
     */
    @ManyToOne(targetEntity = EmployeeStatus.class, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private EmployeeStatus employeeStatus;
    /**
     * 员工职位
     */
    @ManyToOne(targetEntity = DepartmentPosition.class, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private DepartmentPosition departmentPosition;
    /**
     * 创建日期
     */
    @Column
    private Date createDate;
    /**
     * 修改日期
     */
    @Column
    private Date modificationDate;
    /**
     * 所属部门
     */
    @ManyToOne(targetEntity = Department.class, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private Department department;
    /**
     * 员工角色
     */
    @ManyToMany(targetEntity = Role.class, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "employee_role")
    private Set<Role> roles;
    /**
     * 登录次数
     */
    @Column
    private Integer loginTimes;
    /**
     * 最后登录IP
     */
    @Column
    private String lastLoginIP;

    @Version
    @Column
    private int version;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLoginTimes() {
        return loginTimes;
    }

    public void setLoginTimes(Integer loginTimes) {
        this.loginTimes = loginTimes;
    }

    public String getLastLoginIP() {
        return lastLoginIP;
    }

    public void setLastLoginIP(String lastLoginIP) {
        this.lastLoginIP = lastLoginIP;
    }

    public Employee getChief() {
        return chief;
    }

    public void setChief(Employee chief) {
        this.chief = chief;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Date getTermDate() {
        return termDate;
    }

    public void setTermDate(Date termDate) {
        this.termDate = termDate;
    }

    public EmployeeStatus getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(EmployeeStatus employeeStatus) {
        this.employeeStatus = employeeStatus;
    }


    public DepartmentPosition getDepartmentPosition() {
        return departmentPosition;
    }

    public void setDepartmentPosition(DepartmentPosition departmentPosition) {
        this.departmentPosition = departmentPosition;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getActivationkey() {
        return activationkey;
    }

    public void setActivationkey(String activationkey) {
        this.activationkey = activationkey;
    }

    public String getResetpasswordkey() {
        return resetpasswordkey;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public void setResetpasswordkey(String resetpasswordkey) {
        this.resetpasswordkey = resetpasswordkey;
    }
}
