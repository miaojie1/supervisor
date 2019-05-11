insert into employee(id, name, username, password, create_date, modification_date, version)
values (1, 'admin', 'admin', '827ccb0eea8a706c4c34a16891f84e7b', current_date, current_date, 1);

insert into department(id, create_date, description, modification_date, name, version,
                       superior_department_id)
values (1, current_date, 'department', null, '总裁办', 1, null),
       (2, current_date, 'department1', null, '人力资源', 1, 1),
       (3, current_date, 'department2', null, '质管部', 1, 1);

insert into department_position(id, create_date, description, modification_date, name, status, version,department_id)
values (1, current_date, '总经理', null, '总经理', true, 1, 1),
        (2, current_date, '副总经理', null, '副总经理', true, 1, 1),
        (3, current_date, '挂名总经理', null, '挂名总经理', true, 1, 1),
        (4, current_date, '人力资源总监', null, '人力总监', true, 1, 2),
        (5, current_date, '人力资源', null, '人力干部', true, 1, 2),
        (6, current_date, '质管部', null, '质管总监', true, 1, 3),
        (7, current_date, '质管部', null, '质管干部', true, 1, 3);

insert into employee_status(id, create_date, description, modification_date, name, status, version)
values (1, current_date, '在职', null, '在职', true, 1),
        (2, current_date, '离职', null, '离职', true, 1);

update employee
set department_id=1
where id = 1;

insert into posting_system(id, content, create_date, effect_date, expire_date, modification_date, name, version,announcer_id)
values (1, '公告内容1',current_date, '2019-04-08 22:15:30', '2019-05-30 22:15:44', current_date, '公告1', 1,1),
       (2, '公告内容2',current_date, '2019-04-01 22:15:49', '2019-05-25 22:15:59', current_date, '公告2', 1,1),
       (3, '公告内容3',current_date, '2019-04-26 22:16:29', '2019-05-29 22:16:40', current_date, '公告3', 1,1);

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
        (7, current_date, null, '首页公告', '/posting/listExpPostingPage', 1, null,
        true, false, 1, null),
        (8, current_date, null, '角色管理', '/role/listRolePage/pageNo/{pageNo}/pageSize/{pageSize}', 1, null,
          true, false, 1, 1),
        (9, current_date, null, '公告详情', '/posting/announcementDetail/postingId/{postingId}', 1, null,true, false, 1, null),
       (10, current_date, null, '工作管理', null, 2, null, true, true,1, null),
       (11, current_date, null, '项目管理', '/project/listProjectPage/pageNo/{pageNo}/pageSize/{pageSize}', 1, '', true, false, 1, 10),
       (12, current_date, null, '监理部管理', '/projectSupervisionDepartment/listProjectSupervisionDepartmentPage/pageNo/{pageNo}/pageSize/{pageSize}', 1, '', true, false, 1, 10);




insert into role_menu(roles_id, menus_id)
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
       (1, 12);


insert into operation(id, button_id, button_url, create_time, modification_time, version)
values (1, 'addBtn', '123', current_date, current_date, 1),
       (2, 'editBtn', '123', current_date, current_date, 1),
       (3, 'addBtn', '/menu/addOrEditMenu', current_date, current_date, 1),
       (4, 'addBtnOk', '/menu/saveMenu', current_date, current_date, 1),
       (5, 'delBtn', '/menu/delete/menuId/{menuId}', current_date, current_date, 1),
       (6, 'batchDel', '/menu/batch/delete', current_date, current_date, 1),
       (7, 'editBtn', '/project/saveProject', current_date, current_date, 1),
       (8, 'editBtn', '/posting/savePosting', current_date, current_date, 1),
       (9, 'delBtn', '/posting/delete/postingId/{postingId}', current_date, current_date, 1),
       (10, 'batchDel', '/posting/delPostingBatch', current_date, current_date, 1),
       (11, 'editBtn', '/department/edit', current_date, current_date, 1),
       (12, 'saveBtn', '/department/saveOrUpdate', current_date, current_date, 1),
       (13, 'delBtn', '/department/delDepartmentBatch', current_date, current_date, 1),
       (14, 'addBtn', '/employee/addOrEditEmployee', current_date, current_date, 1),
       (15, 'addBtnOk', '/employee/saveEmployee', current_date, current_date, 1),
       (16, 'editBtn', '/employee/addOrEditEmployee', current_date, current_date, 1),
       (17, 'delBtn', '/employee/delete/employeeId/{employeeId}', current_date, current_date, 1),
       (18, 'batchDel', '/employee/batch/delete', current_date, current_date, 1),
       (19, 'addBtn', '/resource/addOrEditEmployee', current_date, current_date, 1),
       (20, 'editBtn', '/resource/delete/employeeId/{employeeId}', current_date, current_date, 1),
       (21, 'delBtn', '/resource/batch/delete', current_date, current_date, 1),
       (22, 'page', '/departmentPosition/listAllDepartmentPositions', current_date, current_date, 1),
       (23, 'page', '/employeeStatus/listAllEmployeeStatus', current_date, current_date, 1),
       (24, 'addBtn', '/role/addRole', current_date, current_date, 1),
       (25, 'editBtn', '/role/editRole', current_date, current_date, 1),
       (26, 'delBtn', '/role/delete/roleId/{roleId}', current_date, current_date, 1),
       (27, 'addBtnOk', '/role/saveRole', current_date, current_date, 1),
       (28, 'page', '/department/listAllDepartment', current_date, current_date, 1),
       (29, 'showMenus', '/menu/listMenuTree', current_date, current_date, 1),
       (30, 'showRoles', '/role/listAllRoles', current_date, current_date, 1),
       (31, 'page', '/department/listAllDepartment', current_date, current_date, 1),
       (32, 'page', '/attachment/upload', current_date, current_date, 1),
       (33, 'page', '/attachment/deleteAttachment', current_date, current_date, 1),
       (34, 'page', '/menu/listAllMenus', current_date, current_date, 1),
       (35, 'getCurrentUser', '/employee/getCurrentUser', current_date, current_date, 1),
       (36, 'delBtn', '/project/delete/projectId/{projectId}', current_date, current_date, 1),
       (37, 'batchDel', '/project/delProjectBatch', current_date, current_date, 1),
       (38, 'saveOrAddSupervisionDepartment', '/projectSupervisionDepartment/saveProjectSupervisionDepartment', current_date, current_date, 1),
       (39, 'delSupervisionDepartment', '/projectSupervisionDepartment/delete/projectSupervisionDpId/{projectSupervisionDpId}', current_date, current_date, 1),
       (40, 'delSupervisionDepartments', '/projectSupervisionDepartment/delProjectSupervisionDpBatch', current_date, current_date, 1),
       (41, 'getEmployeeList', '/employee/listAllEmployees', current_date, current_date, 1);

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
       (1, 17),
       (1, 18),
       (1, 19),
       (1, 20),
       (1, 21),
       (1, 22),
       (1, 23),
       (1, 24),
       (1, 25),
       (1, 26),
       (1, 27),
       (1, 28),
       (1, 29),
       (1, 30),
       (1, 31),
       (1, 32),
       (1, 33),
       (1, 34),
       (1, 35),
       (1, 36),
       (1, 37),
       (1, 38),
       (1, 39),
       (1, 40),
       (1, 41);


insert into menu_operation(menu_id, operation_id)
values (2, 1),
       (3, 2),
       (4, 3),
       (4, 4),
       (4, 6),
       (11, 7),
       (6, 8),
       (6, 32),
       (6, 33),
       (6, 9),
       (6, 10),
       (5, 11),
       (5, 12),
       (5, 13),
       (5, 31),
       (2, 14),
       (2, 15),
       (2, 16),
       (2, 17),
       (2, 18),
       (2, 22),
       (2, 23),
       (2, 28),
       (2, 30),
       (2, 35),
       (8, 24),
       (8, 25),
       (8, 26),
       (8, 27),
       (8, 29),
       (11, 36),
       (11, 37);



update hibernate_sequence
set next_val=100
where 1 = 1;

