insert into employee(id, name, username, password, create_date, modification_date)
values (1, 'admin', 'admin', '827ccb0eea8a706c4c34a16891f84e7b', current_date, current_date)
on duplicate key update id=values(id);



insert into role(id, create_date, description, modification_date, name, superior_role_id)
values (1, current_date, '管理员', current_date, 'admin', null)
on duplicate key update id=values(id);


insert into employee_role(employee_id, roles_id)
values (1, 1)
on duplicate key update employee_id=values(employee_id);


insert into menu(id, create_date, modification_date, name, url, sort, remark, status, root_menu)
values (1, current_date, null, '公共功能', null, 2, null, true, true),
       (2, current_date, null, '用户管理', '#', 1, '', true, false),
       (3, current_date, null, '菜单管理', '##', 1, '', true, false)
on duplicate key update id=values(id);


insert into menu_sub_menus(menu_id, sub_menus_id)
values (1, 2),
       (1, 3)
on duplicate key update menu_id=values(menu_id) and sub_menus_id = values(sub_menus_id);


insert into role_menu(role_id, menus_id)
values (1, 1),
       (1, 2),
       (1, 3)
on duplicate key update role_id=values(role_id) and menus_id = values(menus_id);


insert into operation(id, button_id, button_url, create_time, modification_time)
values (1, 'addBtn', '####', current_date, current_date),
       (2, 'editBtn', '###', current_date, current_date)
on duplicate key update id=values(id);


replace into menu_operation(menu_id, operation_id)
values (2, 1),
       (3, 2);



