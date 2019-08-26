package com.xinguan.workresult.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.workresult.model.PictureFolder;
import com.xinguan.workresult.service.PictureFolderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.criteria.Predicate;
import java.util.Date;

@Service
public class PictureFolderServiceImpl extends BaseService<PictureFolder> implements PictureFolderService {
    @Override
    public Page<PictureFolder> listPictureFolderByPage(int pageSize, int pageNo, String picFolderName) {
        return pictureFolderRepository.findAll((Specification<PictureFolder>) (root, criteriaQuery, criteriaBuilder) -> {
            Predicate namePredicate = null;
            if (StringUtils.isNotBlank(picFolderName)) {
                //项目名称模糊查询
                namePredicate = criteriaBuilder.like(root.get("name").as(String.class), "%" + picFolderName + "%");
                criteriaQuery.where(namePredicate);
            }
            return criteriaQuery.getRestriction();
        }, PageRequest.of(pageNo, pageSize, Sort.Direction.ASC, "createDate"));
    }
    @Transactional
    @Override
    public PictureFolder saveOrUpdate(PictureFolder pictureFolder) {
        Example<PictureFolder> employeeExample = getSimpleExample(pictureFolder);
        if (pictureFolderRepository.exists(employeeExample)) {
            Assert.notNull(pictureFolder.getName(), "文件夹已存在!");
        } else {
            pictureFolder.setCreateDate(new Date());
        }
        return pictureFolderRepository.saveAndFlush(pictureFolder);
    }

    @Override
    public void removePictureFolderById(Long pictureFolderId) {
        pictureFolderRepository.deleteById(pictureFolderId);
    }
}
