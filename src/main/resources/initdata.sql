insert into employee(id, name, username, password, create_date, modification_date, version)
values (1, 'admin', 'admin', '827ccb0eea8a706c4c34a16891f84e7b', current_date, current_date, 1),
       (2, '监理测试', 'test','827ccb0eea8a706c4c34a16891f84e7b', current_date, current_date, 1);

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
values (1, 1),
       (2, 1);

replace into menu(id, create_date, modification_date, name, url, sort, remark, status, root_menu, version,
                  parent_menu_id)
values (1, current_date, null, '公共功能', null, 2, null, true, true, 1, null),
       (2, current_date, null, '用户管理', '/employee/listEmployeePage/pageSize/{pageSize}/pageNo/{pageNo}', 1, '', true, false, 1, 1),
       (4, current_date, null, '菜单管理', '/menu/listMenuPage/pageNo/{pageNo}/pageSize/{pageSize}', 1, '', true, false, 1, 1),
       (5, current_date, null, '部门管理', '/department/listDepartmentPage/pageSize/{pageSize}/pageNo/{pageNo}', 1, null, true, false, 1, 1),
       (6, current_date, null, '公告管理', '/posting/listPostingPage/pageNo/{pageNo}/pageSize/{pageSize}', 1, null, true, false, 1, 1),
       (7, current_date, null, '工作成果', null, 2, null, true, true,1, null),
       (8, current_date, null, '角色管理', '/role/listRolePage/pageNo/{pageNo}/pageSize/{pageSize}', 1, null, true, false, 1, 1),
       (10, current_date, null, '工作管理', null, 2, null, true, true,1, null),
       (11, current_date, null, '项目管理', '/project/listProjectPage/pageNo/{pageNo}/pageSize/{pageSize}', 1, '', true, false, 1, 10),
       (12, current_date, null, '监理部管理', '/projectSupervisionDepartment/listProjectSupervisionDepartmentPage/pageNo/{pageNo}/pageSize/{pageSize}', 1, '', true, false, 1, 10),
       (13, current_date, null, '知识库管理', '/fileFolder/listAllFileFolder', 1, '', true, false, 1, 10),
       (14, current_date, null, '数据备份', '/dataBackup', 1, '', true, false, 1, 10),
       (15, current_date, null, '文档库管理', '/documentFolder/listDocumentFolderPage/pageNo/{pageNo}/pageSize/{pageSize}', 1, '', true, false, 1, 7),
       (16, current_date, null, '影像资料管理', '/pictureFolder/listPicFolderPage/pageNo/{pageNo}/pageSize/{pageSize}', 1, '', true, false, 1, 7),
       (17, current_date, null, '监理日志', '/superLogJob/listSupervisionLogByPage', 1, '', true, false, 1, 7);




insert into role_menu(roles_id, menus_id)
values (1, 1),
       (1, 2),
       (1, 4),
       (1, 5),
       (1, 6),
       (1, 7),
       (1, 8),
       (1, 10),
       (1, 11),
       (1, 12),
       (1, 13),
       (1, 14),
       (1, 15),
       (1, 16),
       (1, 17);


insert into operation(id, button_id, button_url, create_time, modification_time, version)
values (1, 'primary', '/menu/listMenu', current_date, current_date, 1),
       (2, 'primary', '/employee/getCurrentUser', current_date, current_date, 1),
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
       (24, 'addBtn', '/role/saveRoleMenus', current_date, current_date, 1),
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
       (41, 'getEmployeeList', '/employee/listAllEmployees', current_date, current_date, 1),
       (42, 'getProjectList', '/project/listAllProjects', current_date, current_date, 1),
       (43, 'page', '/projectStatus/listAllProjectStatus', current_date, current_date, 1),
       (44, 'primary', '/posting/listExpPostingPage', current_date, current_date, 1),
       (45, 'primary', '/posting/announcementDetail/postingId/{postingId}', current_date, current_date, 1),
       (46, 'editBtn', '/fileFolder/addOrEditFileFolder', current_date, current_date, 1),
       (47, 'addBtnOk', '/fileFolder/saveFileFolder', current_date, current_date, 1),
       (48, 'delBtn', '/fileFolder/delete/fileFolder/{fileFolderId}', current_date, current_date, 1),
       (49, 'batchDelBtn', '/fileFolder/batch/delete', current_date, current_date, 1),
       (50, 'primary', '/knowledge/findKnowledgeByFileFolder', current_date, current_date, 1),
       (51, 'findProjectsByProjectSupervisionDepartmentIsNull', '/project/listProjectsSupervisionDpIsNull', current_date, current_date, 1),
       (52, 'primary', '/attachment/download', current_date, current_date, 1),
       (53, 'page', '/getDataBackupList', current_date, current_date, 1),
       (54, 'editBtn', '/documentFolder/saveDocumentFolder', current_date, current_date, 1),
       (55, 'delBtn', '/documentFolder/delete/documentFolderId/{documentFolderId}', current_date, current_date, 1),
       (56, 'primary', '/document/listDocumentByFolderPage/documentFolderId/{documentFolderId}/pageNo/{pageNo}/pageSize/{pageSize}', current_date, current_date, 1),
       (57, 'primary', '/document/download', current_date, current_date, 1),
       (58, 'primary', '/pictureFolder/savePictureFolder', current_date, current_date, 1),
       (59, 'primary', '/pictureFolder/delete/pictureFolder/{pictureFolderId}', current_date, current_date, 1),
       (60, 'test', '/superLogJob/addSupLogForTest', current_date, current_date, 1),
       (61, 'addBtn', '/knowledge/uploadKnowledge', current_date, current_date, 1),
       (62, 'editBtn', '/knowledge/saveKnowledge', current_date, current_date, 1),
       (63, 'delBtn', '/knowledge/delete/knowledge/{knowledgeId}', current_date, current_date, 1),
       (64, 'batchDelBtn', '/knowledge/batch/delete', current_date, current_date, 1),
       (65, 'primary', '/knowledge/findKnowledgeByFileNameAndFolder', current_date, current_date, 1),
       (66, 'downloadBtn', '/knowledge/downloadKnowledge', current_date, current_date, 1),
       (67, 'primary', '/knowledge/findKnowledgeByFileName', current_date, current_date, 1),
       (80, 'test', '/superLogJob/addSupLogForTest', current_date, current_date, 1),
       (81, 'page', '/superLogJob/listAllSupEmployees',current_date,current_date,1),
       (82, 'delbtn','/superLogJob/deleteSupLogById', current_date, current_date,1),
       (83, 'page','/superLogJob/listSuperLogRecordByPage', current_date, current_date,1),
       (84, 'delbtn','/superLogJob/deleteSupLogRecordById', current_date, current_date,1);

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
       (1, 41),
       (1, 42),
       (1 ,43),
       (1, 44),
       (1, 45),
       (1, 46),
       (1, 47),
       (1, 48),
       (1, 49),
       (1, 50),
       (1, 51),
       (1, 52),
       (1, 53),
       (1, 54),
       (1, 55),
       (1, 56),
       (1, 57),
       (1, 58),
       (1, 59),
       (1, 60),
       (1, 61),
       (1, 62),
       (1, 63),
       (1, 64),
       (1, 65),
       (1, 66),
       (1, 67),
       (1, 80),
       (1, 81),
       (1, 82),
       (1, 83),
       (1, 84);

insert into menu_operation(menu_id, operation_id)
values (2, 1),
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
       (11, 37),
       (11, 41),
       (11, 43),
       (13, 46),
       (13, 47),
       (13, 48),
       (13, 49),
       (13, 50),
       (13, 61),
       (13, 62),
       (13, 63),
       (13, 64),
       (13, 65),
       (13, 67),
       (14, 53),
       (15, 54),
       (15, 55),
       (17, 60),
       (17, 80),
       (17, 81),
       (17, 82),
       (17, 83),
       (17, 84);

insert into project_status(id, name, remark, version)
values (1, '状态1', 'remark1', 1),
        (2, '状态2', 'remark1', 1);

insert into project(id, construction, create_date, development, modification_date, name, no, section, version, manager_id, project_status_id)
values (1, '施工单位1',current_date, '建设单位1', current_date , '项目1','01','标段1',1,1, 1),
      (2, '施工单位2',current_date, '建设单位2', current_date , '项目2','02','标段2',1,1, 2);

insert into project_supervision_department(id, create_date, description, modification_date, name, remark, version, major_id, project_id)
values (1,current_date, '项目监理部门111', current_date , '项目监理部门1','remark1',1,1, 1),
       (2,current_date, '项目监理部门2', current_date , '项目监理部门2','remark1',1,2, 2);

insert into project_supervision_department_membership(project_supervision_department_id,membership_id)
values (1, 1);

insert into document_category(id, create_date, name, remark, version)
values (1, current_date, 'txt', '文本文档', 1),
        (2, current_date, 'xls', '表格', 1);

insert into document_folder(id,create_date, description, name ,version)
values (1, current_date,'这是文档库1','文档库1',1);

insert into document(id, create_date, document_name, document_url, version, document_category_id, document_folder_id, uploader_id)
values (1, current_date, 'test11557751731659.txt','f:/uploadTest',1,1,1,1),
      (2, current_date, 'test11557670867748.txt','f:/uploadTest',1,1,1,1);

update hibernate_sequence
set next_val=100
where 1 = 1;

