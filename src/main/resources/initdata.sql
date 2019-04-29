insert into employee(id, name, username, password, create_date, modification_date, version)
values (1, 'admin', 'admin', '827ccb0eea8a706c4c34a16891f84e7b', current_date, current_date, 1);

insert into department(id, create_date, description, modification_date, name, version,
                       superior_department_id)
values (1, current_date, 'department', null, '总裁办', 1, null),
       (2, current_date, 'department1', null, '人力资源', 1, 1),
       (3, current_date, 'department2', null, '质管部', 1, 1);

update employee
set department_id=1
where id = 1;

insert into posting_system(id, content, create_date, effect_date, expire_date, modification_date, name, version,announcer_id)
values (1, '公告内容1',current_date, '2019-04-08 22:15:30', '2019-04-30 22:15:44', current_date, '公告1', 1,1),
       (2, '公告内容2',current_date, '2019-04-01 22:15:49', '2019-04-25 22:15:59', current_date, '公告2', 1,1),
       (3, '公告内容3',current_date, '2019-04-26 22:16:29', '2019-04-29 22:16:40', current_date, '公告3', 1,1);

insert into role(id, create_date, description, modification_date, name, superior_role_id, version)
values (1, current_date, '管理员', current_date, 'admin', null, 1);

insert into employee_role(employees_id, roles_id)
values (1, 1);

replace into menu(id, create_date, modification_date, name, url, sort, remark, status, root_menu, version,
                  parent_menu_id)
values (1, current_date, null, '公共功能', null, 2, null, true, true, 1, null),
       (2, current_date, null, '用户管理', '/employee/listEmployeePage/pageSize/{pageSize}/pageNo/{pageNo}', 1, '', true, false, 1, 1),
       (3, current_date, null, '用户菜单', '/menu/listMenu', 1, '', true, false, 1, 1),
       (4, current_date, null, '菜单管理', '/menu/listMenuPage/pageNo/{pageNo}/pageSize/{pageSize}', 1, '', true, false, 1,
        1),
       (5, current_date, null, '部门管理', '/department/listDepartmentPage/pageSize/{pageSize}/pageNo/{pageNo}', 1, null,
        true, false, 1, 1),
        (6, current_date, null, '公告管理', '/posting/listPostingPage/pageNo/{pageNo}/pageSize/{pageSize}', 1, null,
        true, false, 1, 1),
        (7, current_date, null, '首页公告', '/posting/listExpPostingPage/pageNo/{pageNo}/pageSize/{pageSize}', 1, null,
        true, false, 1, null);




insert into role_menu(roles_id, menus_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (1, 6),
       (1, 7);


insert into operation(id, button_id, button_url, create_time, modification_time, version, name)
values (1, 'addBtn', '123', current_date, current_date, 1, '新增'),
       (2, 'editBtn', '123', current_date, current_date, 1, '编辑'),
       (3, 'addBtn', '/menu/addOrEditMenu', current_date, current_date, 1, '新增'),
       (4, 'commitBtnOk', '/menu/saveMenu', current_date, current_date, 1, '提交'),
       (5, 'delBtn', '/menu/delete/menuId/{menuId}', current_date, current_date, 1, '删除'),
       (6, 'delBatchBtn', '/menu/batch/delete', current_date, current_date, 1, '批量删除'),
       (7, 'addPostingBtn', '/posting/addOrEditPosting', current_date, current_date, 1, '新增'),
       (8, 'commitPostingBtnOk', '/posting/savePosting', current_date, current_date, 1, '提交'),
       (9, 'delPostingBtn', '/posting/delete/postingId/{postingId}', current_date, current_date, 1, '删除'),
       (10, 'delBatchPosting', '/posting/batch/delete', current_date, current_date, 1, '批量删除'),
       (11, 'editBtn', '/department/edit', current_date, current_date, 1, '编辑'),
       (12, 'saveBtn', '/department/saveOrUpdate', current_date, current_date, 1, '保存'),
       (13, 'delBtn', '/department/delDepartmentBatch', current_date, current_date, 1, '删除'),
       (14, 'addEmployeeBtn', '/employee/addOrEditEmployee', current_date, current_date, 1, '新增'),
       (15, 'commitEmployeeBtnOk', '/employee/saveEmployee', current_date, current_date, 1, '提交'),
       (16, 'delEmployeeBtn', '/employee/delete/employeeId/{employeeId}', current_date, current_date, 1, '删除'),
       (17, 'delBatchEmployee', '/employee/batch/delete', current_date, current_date, 1, '批量删除');

insert into role_operation(roles_id, operations_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (1, 6),
       (1, 7),
       (1, 8),
       (1, 9),
       (1, 10),
       (1, 11),
       (1, 12),
       (1, 13),
       (1, 14),
       (1, 15),
       (1, 16),
       (1, 17);


insert into menu_operation(menu_id, operation_id)
values (2, 1),
       (3, 2),
       (4, 3),
       (4, 4),
       (4, 6),
       (6, 7),
       (6, 8),
       (6, 10),
       (5, 11),
       (5, 12),
       (5, 13),
       (2, 14),
       (2, 15),
       (2, 16),
       (2, 17);


update hibernate_sequence
set next_val=100
where 1 = 1;

