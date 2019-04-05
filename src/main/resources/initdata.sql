
insert into employee(id, name, username, password, create_date, modification_date)
values (1, 'admin', 'admin', '827ccb0eea8a706c4c34a16891f84e7b', current_date, current_date);


insert into role(id, create_date, description, modification_date, name, superior_role_id)
values (1, current_date, '管理员', current_date, 'admin', null);

insert into employee_role(employee_id, roles_id)
values (1, 1);

replace into menu(id, create_date, modification_date, name, url, sort, remark, status, root_menu)
values (1, current_date, null, '公共功能', null, 2, null, true, true),
       (2, current_date, null, '用户管理', '/user/listUser', 1, '', true, false),
       (3, current_date, null, '菜单管理', '/menu/listMenu', 1, '', true, false);


insert into menu_sub_menus(menu_id, sub_menus_id)
values (1, 2),
       (1, 3);

insert into role_menu(role_id, menus_id)
values (1, 1),
       (1, 2),
       (1, 3);

insert into operation(id, button_id, button_url, create_time, modification_time)
values (1, 'addBtn', '123', current_date, current_date),
       (2, 'editBtn', '123', current_date, current_date);


insert into menu_operation(menu_id, operation_id)
values (2, 1),
       (3, 2);



