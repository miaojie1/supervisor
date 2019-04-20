insert into employee(id, name, username, password, create_date, modification_date, version)
values (1, 'admin', 'admin', '827ccb0eea8a706c4c34a16891f84e7b', current_date, current_date, 1);


insert into role(id, create_date, description, modification_date, name, superior_role_id, version)
values (1, current_date, '管理员', current_date, 'admin', null, 1);

insert into employee_role(employees_id, roles_id)
values (1, 1);

replace into menu(id, create_date, modification_date, name, url, sort, remark, status, root_menu, version,
                  parent_menu_id)
values (1, current_date, null, '公共功能', null, 2, null, true, true, 1, null),
       (2, current_date, null, '用户管理', '/user/listUser', 1, '', true, false, 1, 1),
       (3, current_date, null, '用户菜单', '/menu/listMenu', 1, '', true, false, 1, 1),
       (4, current_date, null, '菜单管理', '/menu/listMenuPage/pageNo/{pageNo}/pageSize/{pageSize}', 1, '', true, false, 1,
        1);



insert into role_menu(roles_id, menus_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (1, 4);

insert into operation(id, button_id, button_url, create_time, modification_time, version)
values (1, 'addBtn', '123', current_date, current_date, 1),
       (2, 'editBtn', '123', current_date, current_date, 1),
       (3, 'addBtn', '/menu/addOrEditMenu', current_date, current_date, 1),
       (4, 'addBtnOk', '/menu/saveMenu', current_date, current_date, 1),
       (5, 'delBtn', '/menu/delete/menuId/{menuId}', current_date, current_date, 1),
       (6, 'batchDel', '/menu/batch/delete', current_date, current_date, 1);

insert into role_operation(roles_id, operations_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (1, 6);


insert into menu_operation(menu_id, operation_id)
values (2, 1),
       (3, 2),
       (4, 3),
       (4, 4),
       (4, 6);

update hibernate_sequence
set next_val=10
where 1 = 1;

